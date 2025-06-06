package cz.muni.fi.pb162.hw01.impl;

import com.beust.jcommander.Parameter;
import cz.muni.fi.pb162.hw01.cmd.CommandLine;
import cz.muni.fi.pb162.hw01.Utils;
import cz.muni.fi.pb162.hw01.cmd.Messages;

import java.util.ArrayList;
import java.util.List;

/**
 * Application class represents the command line interface of this application.
 * <p>
 * You are expected to implement the {@link Application#run()} method
 *
 * @author jcechace
 */
public class Application {
    @Parameter(names = {"--size", "-s"})
    private int size = 3;

    @Parameter(names = {"--win", "-w"})
    private int win = 3;

    @Parameter(names = {"--history", "-h"})
    private int history = 2;

    @Parameter(names = {"--Players", "-p"})
    private String players = "xo";

    @Parameter(names = "--help", help = true)
    private boolean showUsage = false;

    /**
     * Application entry point
     *
     * @param args command line arguments of the application
     */
    public static void main(String[] args) {
        Application app = new Application();

        CommandLine cli = new CommandLine(app);
        cli.parseArguments(args);

        if (app.showUsage) {
            cli.showUsage();
        } else {
            app.run();
        }
    }

    /**
     * Application runtime logic
     */
    private void run() {
        boolean end = false;                                        //variable to determine whether the game os over

        int round = 1;                                              //variable that represents rounds of the game
        int turn = 1;                                               //an index of the array of playing boards
        int rewind;                                                 //a rewind number
        int turnTwo = 0;                                            //an amount of player turns
        int x = 0;                                                  //x coordinate of player's mark
        int y = 0;                                                  //y coordinate of player's mark
        int[][] grid = new int[size][size];                         //An array consisting positions of players' marks

        String input;
        String quit;
        String[] playerMarks = players.split("");

        List<Boards> formattedBoards = new ArrayList<>();           //A list of playing boards
        List<Coordinates> coordinates = new ArrayList<>();          //A list of coordinates

        System.out.printf(Messages.TURN_COUNTER, round);
        formattedBoards.add(new Boards(size, new StringBuilder()));
        formattedBoards.get(0).format(formattedBoards.get(0));
        System.out.println(formattedBoards.get(0).getFormattedBoard().toString());

        while (!end) {
            for (int i = 0; i < playerMarks.length; i++) {
                while (true) {

                    System.out.printf(Messages.TURN_PROMPT, playerMarks[i]);
                    input = Utils.readLineFromStdIn();

                    try {
                        rewind = Integer.parseInt(input.split("<", 3)[2]);
                        if (rewind <= history && rewind < formattedBoards.size()) {
                            for (int j = 0; j < rewind; j++) {
                                grid[coordinates.get(coordinates.size() - 1).getX()]
                                        [coordinates.get(coordinates.size() - 1).getY()] = 0;
                                formattedBoards.remove(formattedBoards.size() - 1);
                                coordinates.remove(coordinates.size() - 1);
                            }
                            turnTwo++;
                            System.out.println(Messages.TURN_DELIMITER);
                            turn -= rewind;
                            break;
                        } else {
                            System.out.println(Messages.TURN_DELIMITER);
                            System.out.println(Messages.ERROR_REWIND);
                        }

                    } catch (Throwable o) {
                        try {
                            x = Integer.parseInt(input.split(" ", 2)[0]);
                            y = Integer.parseInt(input.split(" ", 2)[1]);
                            if (x < size && y < size) {
                                if (grid[x][y] == 0) {
                                    coordinates.add(new Coordinates(x, y));
                                    grid[x][y] = i + 1;
                                    formattedBoards.add(new Boards(size, new StringBuilder()));
                                    formattedBoards.get(turn).getFormattedBoard().
                                            append(formattedBoards.get(turn - 1).getFormattedBoard());
                                    formattedBoards.get(turn).getFormattedBoard().
                                            replace(x * (2 * size + 2) + y * 2 + 1,
                                                    x * (2 * size + 2) + y * 2 + 2, playerMarks[i]); //places players mark
                                    System.out.println(Messages.TURN_DELIMITER);
                                    turn++;
                                    turnTwo++;
                                    break;
                                }
                            }
                            System.out.println(Messages.TURN_DELIMITER);
                            System.out.println(Messages.ERROR_ILLEGAL_PLAY);

                        } catch (Throwable p) {
                            try {
                                quit = input.split(":", 2)[1];
                                if (!quit.equals("q")) {
                                    System.out.println(Messages.TURN_DELIMITER);
                                    System.out.println(Messages.ERROR_INVALID_COMMAND);
                                } else {
                                    System.out.println(Messages.TURN_DELIMITER);
                                    end = true;
                                    break;
                                }
                            } catch (Throwable k) {
                                System.out.println(Messages.TURN_DELIMITER);
                                System.out.println(Messages.ERROR_INVALID_COMMAND);
                            }
                        }
                    }
                }
                if (checkWin(x, y, size, grid, (i + 1), win)) {
                    System.out.printf(Messages.GAME_OVER, turnTwo);
                    System.out.printf(Messages.GAME_WINNER + "\n", playerMarks[i]);
                    System.out.println(formattedBoards.get(formattedBoards.size() - 1).getFormattedBoard().toString());
                    end = true;
                    break;
                }
                if (end || isFull(grid, size)) {
                    System.out.printf(Messages.GAME_OVER, turnTwo);
                    System.out.println(formattedBoards.get(formattedBoards.size() - 1).getFormattedBoard().toString());
                    end = true;
                    break;
                }
                round++;
                System.out.printf(Messages.TURN_COUNTER, round);
                System.out.println(formattedBoards.get(turn - 1).getFormattedBoard().toString());

            }
        }
    }

    /**
     * A method that determines whether the playing board is full.
     *
     * @param grid The playing board aka playing grid.
     * @param size Size of the playing board.
     * @return True when the playing board is full, otherwise false.
     */
    private static boolean isFull(int[][] grid, int size) {
        boolean full = true;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                full = grid[i][j] != 0 && full;
            }
        }
        return full;
    }

    /**
     * A method that determines whether a player won.
     *
     * @param x      X coordinate of the mark placed by a player.
     * @param y      Y coordinate of the mark placed by a player.
     * @param size   Size of the board.
     * @param board  An array consisting positions of players' marks.
     * @param player Numeric representation of a player.
     * @param win    An amount of marks in a row to win.
     * @return True when enough marks in a row, otherwise false.
     */
    private static boolean checkWin(int x, int y, int size, int[][] board, int player, int win) {
        int horizontalCount = 0;
        int verticalCount = 0;
        int diagonalCount = 0;
        int inverseDiagonalCount = -1;

        for (int i = 0; i < size; i++) {
            if (board[x][i] == player) {
                horizontalCount++;
            }
            if (board[i][y] == player) {
                verticalCount++;
            }
        }

        int k = 1;
        while (x - k >= 0 && y - k >= 0) {
            if (board[x - k][y - k] == player) {
                diagonalCount++;
            }
            k++;
        }

        k = 0;
        while (x + k < size && y + k < size) {
            if (board[x + k][y + k] == player) {
                diagonalCount++;
            }
            k++;
        }

        k = 0;
        while (x + k < size && y - k >= 0) {
            if (board[x + k][y - k] == player) {
                inverseDiagonalCount++;
            }
            k++;
        }

        k = 0;
        while (x - k >= 0 && y + k < size) {
            if (board[x - k][y + k] == player) {
                inverseDiagonalCount++;
            }
            k++;
        }

        return diagonalCount == win || inverseDiagonalCount == win || horizontalCount == win || verticalCount == win;
    }
}
