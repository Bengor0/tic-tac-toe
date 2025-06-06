AHomework assignment no. 1, Tic-Tac-Toe
====================================

**Publication date:**  March 15, 2022

**Submission deadline:** April 1, 2022

General information
-------------------
The goal of this homework is to implement the game of [tic-tac-toe](https://en.wikipedia.org/wiki/Tic-tac-toe)  
The players will alternate turns, each playing one action command. 
See the "Running the application" section of this README to understand the desired behavior and the user interface. 

Then game ends when either of the following happens:

- There is a required number of the same symbols in a line
- The board runs out of free cells
- The Quit command is executed

### Command Line Option
Application should support the following

| Name         | Short | Long      | Default | Description                                       | Conditions        |
|--------------|-------|-----------|---------|---------------------------------------------------|-------------------|
| Size         | -s    | --size    | 3       | Size of the board (NxN)                           | >= 3              |
| Win Size     | -w    | --win     | 3       | Number of symbols in line required  to win        | 3 <= w <= size    |
| History Size | -h    | --history | 1       | Number of turns the player is able to rewind back | 0 > h < size*size |
| Players      | -p    | --players | xo      | Symbols and order of players                      | len(players) > 1  |
| Help         |       | --help    | false   | Prints usage                                      |                   |

### User interface
The example bellow should give a good description how a single turn of the game should be presented.

```
Turn: 1
-------
| | | |
-------
| | | |
-------
| | | |
-------
Enter your turn (x): 1 1
===

Turn: 2
-------
| | | |
-------
| |x| |
-------
| | | |
-------
Enter your turn (o):
```

Please pay special attention to the game board formatting. 

- Each row of the board is bordered by a line of ``-`` characters from the top and the bottom
- Cells are bordered by single ``|`` from the left and the right
- Adjacent rows and cells share a separator 
- Each cell is exactly 3 characters wide (including the borders). 
  - The content of a cell is either a played symbol, or a single space for an empty cell.

More complex examples of the gameplay can be seen in further section of this README. 

For convenience and consistency across student solutions a set of messages is provided as a part of ``cmd.Messages`` class.
These messages can be templated and printed to stdout via ``System.printf()`` method call.  
The exact sequence of the output printed to stdout in each turn should thus be as follows
```
Message.TURN_COUNTER
// Formatted board output
Message.TURN_PROMPT
Message.TURN_DELIMITER
```

After the last turn (at the game end) the following should be appended 

```
Messages.GAME_OVER
// Formatted board output
Messages.GAME_WINNER if the game has a winner
```

Command Implementation Details
----------------------
The game will respond to the following commands during a gameplay

| Name   | Command | Description               |
|--------|---------|---------------------------|
| Play   | X Y     | Put a symbol on the board |  
| Rewind | <<N     | Rewind N turns back       |
| Quit   | :q      | Quit the game             |

If any other command is entered (or the command is not valid), the current turn is repeated.  

### Play command
Puts the symbol associated with the current player at the cell with the entered coordinates.
The following conditions apply for this command

- Two space-delimited numbers
  - otherwise ``Messages.ERROR_INVALID_COMMAND`` is printed to stdout
- Both numbers must be in ``<0, n)`` where ``n`` is the size of the board
  - otherwise ``Messages.ERROR_ILLEGAL_PLAY`` is printed to stdout
- Coordinates are referencing an empty cell
  - otherwise ``Messages.ERROR_ILLEGAL_PLAY`` is printed to stdout
- After the command is executed successfully, next turns starts with the next player

### Rewind command
Allows the current user to rewind back last ``N`` successful play commands (essentially removes last ``N`` placed symbol).
The following conditions apply for this command

- Format is ``<<N`` where N is a number
  - otherwise ``Messages.ERROR_INVALID_COMMAND`` is printed to stdout
- N must be in ``<0, h)`` where ``h`` is the size of history
  - otherwise ``Messages.ERROR_REWIND`` is printed to stdout
- After the command is executed successfully, next turns starts with the next player

Be aware that this command is tight to the history size specified when starting the game.

- For games with a history size of 3 it is possible to rewind at most 2 turns back
- For games with a history size of 1 it is not possible to rewind at all

### Quit command
This commands stops and exits the game
The following conditions apply fort this command

- Format is ``:q``
- Execution of this command does not count as a turn 
  - See the example at the end of this README 
- After the command is executed successfully the program exits

### Evaluation
Beside functional correctness this assignment is focused on the object-oriented design.
This means that the way you structure your program will be an important part of its evaluation.
On the other hand, the given set of tests is not trying to provide an elaborate test coverage and incorrect behaviour in corner-cases should not have a large impact on the evaluation.

Note that all this is at your seminar teacher's discretion.

The maximum number of points for this assignment is **10**.

- **5 points** for passing the tests (attached tests do not guarantee a 100% correctness).
- **5 points** for correct and clean implementation (evaluated by your class teacher).

### Preconditions
To successfully implement this assignment you need to know the following

1. What is the difference between _class_ and _object_ and how to work with both.
2. How programs are structured in _object-oriented_ languages.
3. How to use basic control structures such as `if`, `for`/`while` and `switch`.
4. How to work with `String`s and `enum`s in Java.
5. How to work with _arrays_ (or _lists_).
6. Being able to understand and navigate a code provided by third-party.

### Project structure
The structure of the project provided as a base for your implementation should meet the following criteria.

1. Package ```cz.muni.fi.pb162.hw01``` contains classes and interfaces provided as a part of the assignment.
  - **Do not modify or add any classes or subpackages into this package.**
2. Package  ```cz.muni.fi.pb162.hw01.impl``` should contain your implementation.
  - **Anything outside this package will be ignored during evaluation.**

### Names in this document
Unless fully classified name is provided, all class names are relative to the package ```cz.muni.fi.pb162.hw01``` or ```cz.muni.fi.pb162.hw01.impl``` for classes implemented as a part of your solution.


### Compiling the project
The project can be compiled and packaged in the same way you already know

```bash
$ mvn clean install
```

The only difference is that unlike with seminar project, this time the checks for missing documentation and style violation will produce an error.
You can temporarily disable this behavior when running this command.

```bash
$ mvn clean install -Dcheckstyle.fail=false
```

You can consult your seminar teacher to help you set the ```checkstyle.fail``` property in your IDE (or just google it).

### Running the application
The build descriptor is configured to produce a single runnable jar file located at `target/application.jar`. The application can be run using the following command

```
# Start a game on 3x3 board, requiring 3 symbols in a line to win and the ability to rewind 2 turns back
$ java -jar application.jar -s 3 -w 3 --players xo -h 3                                      
Turn: 1
-------
| | | |
-------
| | | |
-------
| | | |
-------
Enter your turn (x): 0 0
===

Turn: 2
-------
|x| | |
-------
| | | |
-------
| | | |
-------
Enter your turn (o): 1 2
===

Turn: 3
-------
|x| | |
-------
| | |o|
-------
| | | |
-------
Enter your turn (x): 2 0
===

Turn: 4
-------
|x| | |
-------
| | |o|
-------
|x| | |
-------
Enter your turn (o): <<2
===

Turn: 5
-------
|x| | |
-------
| | | |
-------
| | | |
-------
Enter your turn (x): :q
===

Game over after 4 turns
-------
|x| | |
-------
| | | |
-------
| | | |
-------
```

### Submitting the assignment
The procedure to submit your solution may differ based on your seminar group. However, it should be generally OK to submit ```target/homework01-2022-1.0-SNAPSHOT-sources.jar``` to the homework vault.

## Implementation
Generally speaking, there are no mandatory requirements on the structure of your code as long as the command line interface of the ```Appplication``` class works correctly.
The use of classes, enums, and interfaces provided as a part of the project skeleton is up to your decision.
