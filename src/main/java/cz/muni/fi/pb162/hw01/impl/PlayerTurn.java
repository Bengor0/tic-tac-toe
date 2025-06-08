package cz.muni.fi.pb162.hw01.impl;

public class PlayerTurn {
    private final Character playerSymbol;
    private final Coordinates coordinates;

    public PlayerTurn(Character playerSymbol, Coordinates coordinates) {
        this.playerSymbol = playerSymbol;
        this.coordinates = coordinates;
    }

    public Character getPlayerSymbol() {
        return playerSymbol;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
}
