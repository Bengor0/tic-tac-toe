package cz.muni.fi.pb162.hw01.impl;

/**
 * A class representing a playing board for the Tic-Tac-Toe game.
 *
 * @author Viktor Sulla <483026@mail.muni.cz>
 */
public class Boards implements BoardFormatter {
    private final int size;
    private final StringBuilder formattedBoard;

    /**
     * A constructor.
     *
     * @param size           size of the board (size * size)
     * @param formattedBoard An empty formatted board converted into string.
     */
    public Boards(int size, StringBuilder formattedBoard) {
        this.size = size;
        this.formattedBoard = formattedBoard;
    }

    public StringBuilder getFormattedBoard() {
        return formattedBoard;
    }

    @Override
    public String format(Boards board) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size * 2 + 1; j++) {
                if (j % 2 == 0) {
                    formattedBoard.append("|");
                } else {
                    formattedBoard.append(" ");
                }
            }
            formattedBoard.append("\n");
        }
        return formattedBoard.toString();
    }
}
