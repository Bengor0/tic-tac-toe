package cz.muni.fi.pb162.hw01.impl;

/**
 * String formatter for {@link ArrayBoard}
 */
public interface BoardFormatter {

    /**
     * Formats given board as {@link String}
     * @param board board to be formatted
     * @return string representation of given board
     */
    String format(Board board);
}
