package cz.muni.fi.pb162.hw01.impl;

/**
 * A class representing coordinates in 2D space.
 *
 * @author Viktor Sulla <483026@mail.muni.cz>
 */
public class Coordinates {
    private final int x;
    private final int y;

    /**
     * A constructor.
     *
     * @param x Coordinate on x axis.
     * @param y Coordinate on y axis.
     */
    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

