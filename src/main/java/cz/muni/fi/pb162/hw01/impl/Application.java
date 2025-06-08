package cz.muni.fi.pb162.hw01.impl;

import com.beust.jcommander.Parameter;
import cz.muni.fi.pb162.hw01.cmd.CommandLine;
import cz.muni.fi.pb162.hw01.Utils;
import cz.muni.fi.pb162.hw01.cmd.Messages;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        int turnCounter = 1;
        Game game = new Game(size, win, players);

        System.out.printf(Messages.TURN_COUNTER, turnCounter);
        System.out.println(game.getPlayingBoard().format(game.getPlayingBoard()));

        while (turnCounter <= (size * size) && !game.getGameOver()) {

            for (Character player : players.toCharArray()) {

                System.out.println("\n");
                boolean isValidInput = false;
                while (!isValidInput) {
                    System.out.printf(Messages.TURN_PROMPT, player);
                    String playerInput = Utils.readLineFromStdIn();
                    System.out.println(Messages.TURN_DELIMITER);
                    isValidInput = handleInput(player, playerInput, game, history);
                }

                game.checkWin();
                if (game.getGameOver()){
                    break;
                }
                turnCounter++;
                System.out.printf(Messages.TURN_COUNTER, turnCounter);
                System.out.println(game.getPlayingBoard().format(game.getPlayingBoard()));
            }
        }
        System.out.printf(Messages.GAME_OVER, turnCounter);
        System.out.println(game.getPlayingBoard().format(game.getPlayingBoard()));
    }

    private static boolean handleInput(Character player, String playerInput, Game game, int history) {
        String playCommandRegex =  "^\\d+ \\d+$";
        String rewindCommandRegex = "^<<\\d+$";
        Pattern playCommandPattern = Pattern.compile(playCommandRegex);
        Pattern rewindCommandPattern = Pattern.compile(rewindCommandRegex);
        Matcher playCommandMatcher = playCommandPattern.matcher(playerInput);
        Matcher rewindCommandMatcher = rewindCommandPattern.matcher(playerInput);

        if (playCommandMatcher.matches()){
            return handlePlayCommand(player, playerInput, game);
        } else if (rewindCommandMatcher.matches()) {
            return handleRewindCommand(playerInput, history, game);
        } else if (playerInput.equals(":q")){
            game.setGameOver(true);
            return true;
        }else {
            System.out.println(Messages.ERROR_INVALID_COMMAND);
            return false;
        }
    }

    /**
     * A method to handle play command
     * @param player player's symbol.
     * @param playerInput player's command prompt (in this case coordinates).
     * @param game an instance of a game.
     * @return true if player's prompt is alright according to the game's rules.
     */
    private static boolean handlePlayCommand(Character player, String playerInput, Game game) {
        Coordinates coordinates = new Coordinates(playerInput);
        if (isWithinBounds(coordinates, game.getSize()) && isEmptyCell(coordinates, game)){
            game.getPlayingBoard().setPlayingGrid(coordinates, player);
            game.getTurnHistory().push(new PlayerTurn(player, coordinates));
            return true;
        } else {
            System.out.println(Messages.ERROR_ILLEGAL_PLAY);
            return false;
        }
    }

    /**
     * a method to check whether coordinates are withing the playing board area.
     * @param coordinates
     * @param size
     * @return
     */
    private static boolean isWithinBounds(Coordinates coordinates, int size) {
        return coordinates.getX() <= size && coordinates.getY() <= size &&
                coordinates.getX() > 0 && coordinates.getY() > 0;
    }

    private static boolean isEmptyCell(Coordinates coordinates, Game game){
        return game.getPlayingBoard().getPlayingGrid()[coordinates.getY() - 1][coordinates.getX() - 1] == null;
    }

    private static boolean handleRewindCommand(String playerInput, int history, Game game) {
        int rewindNumber = Integer.parseInt(playerInput.replaceAll("<", ""));
        if (rewindNumber > 0 && rewindNumber <= history && rewindNumber <= game.getTurnHistory().size()) {
            for (int i = 0; i < rewindNumber; i++){
                PlayerTurn playerTurn = game.getTurnHistory().pop();
                game.getPlayingBoard().setPlayingGrid(playerTurn.getCoordinates(), null);
            }
            return true;
        } else {
            System.out.println(Messages.ERROR_REWIND);
            return false;
        }
    }
}
