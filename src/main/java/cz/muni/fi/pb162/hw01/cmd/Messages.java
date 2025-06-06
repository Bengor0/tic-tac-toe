package cz.muni.fi.pb162.hw01.cmd;

/**
 * Messages and outputs
 */
public final class Messages {
    /**
     * This message is printed to stdin when invalid command is executed
     */
    public static final String ERROR_INVALID_COMMAND = "Invalid command";
    /**
     * This message is printed to stdin when play command is invalid
     */
    public static final String ERROR_ILLEGAL_PLAY = "Illegal play";
    /**
     * This message is printed to stdin when rewind command is invalid
     */
    public static final String ERROR_REWIND = "Unable to rewind";


    /**
     * This message is printed to stdin to inform about current turn number
     */
    public static final String TURN_COUNTER = "Turn: %d" + System.lineSeparator();
    /**
     * This message is printed to stdin following current board
     */
    public static final String TURN_PROMPT = "Enter your turn (%s): ";
    /**
     * This message is printed to stdin at the end of the turn (after play command was entered)
     */
    public static final String TURN_DELIMITER = "===" + System.lineSeparator();
    /**
     * This message is printed to stdin to inform about the game end
     */
    public static final String GAME_OVER = "Game over after %d turns" + System.lineSeparator();
    /**
     * This message is printed to stdin to inform about the winner
     */
    public static final String GAME_WINNER = "Winner is '%s'";

    private Messages() {
        // Intentionally made private to prevent instantiation
    }
}
