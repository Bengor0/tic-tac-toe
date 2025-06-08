package cz.muni.fi.pb162.hw01.impl;


/**
 * A class representing a playing board for the Tic-Tac-Toe game.
 *
 * @author Viktor Sulla <483026@mail.muni.cz>
 */
public class Board implements BoardFormatter {
    private final int size;
    private final Character[][] playingGrid;

    /**
     * A constructor.
     *
     * @param size           size of the board (size * size)
     */
    public Board(int size) {
        this.size = size;
        this.playingGrid = new Character[size][size];
    }

    public int getSize() {
        return size;
    }

    public Character[][] getPlayingGrid() {
        return playingGrid;
    }

    public void setPlayingGrid(Coordinates coordinates, Character playerSymbol) {
        this.playingGrid[coordinates.getY() - 1][coordinates.getX() - 1] = playerSymbol;
    }

    @Override
    public String format(Board board) {
        StringBuilder formattedBoard = new StringBuilder();
        StringBuilder rowDelimiter = new StringBuilder("-".repeat(2 * this.size + 1));

        for (int i = 0; i < size; i++) {
            formattedBoard.append(rowDelimiter);
            formattedBoard.append("\n");
            formattedBoard.append("|");
            for (int j = 0; j < size; j++) {
                if (this.playingGrid[i][j] == null) {
                    formattedBoard.append(" ");
                } else {
                    formattedBoard.append(this.playingGrid[i][j]);
                }
                formattedBoard.append("|");
            }
            formattedBoard.append("\n");
        }
        formattedBoard.append(rowDelimiter);
        return formattedBoard.toString();
    }
}
