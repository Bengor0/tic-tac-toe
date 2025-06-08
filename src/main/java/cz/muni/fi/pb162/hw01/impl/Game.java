package cz.muni.fi.pb162.hw01.impl;

import java.util.Stack;

public class Game {
    private final int size;
    private final int win;
    private final String[] players;
    private final Board playingBoard;
    private final Stack<PlayerTurn> turnHistory;
    private boolean gameOver;

    public Game(int size, int win, String players) {
        this.size = size;
        this.win = win;
        this.players = players.split("");
        this.playingBoard = new Board(size);
        this.turnHistory = new Stack<>();
        this.gameOver = false;
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public int getSize() {
        return size;
    }

    public int getWin() {
        return win;
    }

    public String[] getPlayers() {
        return players;
    }

    public Board getPlayingBoard() {
        return playingBoard;
    }

    public Stack<PlayerTurn> getTurnHistory() {
        return turnHistory;
    }

    public void checkWin() {
        Character[][] playingGrid = playingBoard.getPlayingGrid();
        PlayerTurn playerTurn = turnHistory.peek();
        Character playerSymbol = playerTurn.getPlayerSymbol();
        Coordinates coordinates = playerTurn.getCoordinates();

        int horizontalNum = findHorizontalOccurances(playerSymbol, coordinates, playingGrid);
        int verticalNum = findVerticalOccurances(playerSymbol, coordinates, playingGrid);
        int[] diagonalNums = findDiagonalOccurances(playerSymbol, coordinates, playingGrid);


        if (horizontalNum == win || verticalNum == win || diagonalNums[0] == win || diagonalNums[1] == win){
            gameOver = true;
        }
    }

    public int findHorizontalOccurances(Character playerSymbol, Coordinates coordinates, Character[][] playingGrid){
        int num = 0;
        int x = coordinates.getX() - 1;
        int y = coordinates.getY() - 1;
        for (int i = x; i < size; i++){
            if (playingGrid[y][i] == playerSymbol){
                num++;
            } else break;
        }
        for (int i = x; i >= 0; i--){
            if (playingGrid[y][i] == playerSymbol){
                num++;
            } else break;
        }
        return num - 1;
    }

    public int findVerticalOccurances(Character playerSymbol, Coordinates coordinates, Character[][] playingGrid){
        int num = 0;
        int x = coordinates.getX() - 1;
        int y = coordinates.getY() - 1;
        for (int i = y; i < size; i++){
            if (playingGrid[i][x] == playerSymbol){
                num++;
            } else break;
        }
        for (int i = y; i >= 0; i--){
            if (playingGrid[i][x] == playerSymbol){
                num++;
            } else break;
        }
        return num - 1;
    }

    public int[] findDiagonalOccurances(Character playerSymbol, Coordinates coordinates, Character[][] playingGrid){
        int index = 0;
        int leftCount = 0;
        int rightCount = 0;
        int x = coordinates.getX() - 1;
        int y = coordinates.getY() - 1;

        while (x + index < size && y + index < size){
            if (playingGrid[y + index][x + index] == playerSymbol){
                leftCount++;
                index++;
            } else {
                break;
            }
        }
        index = 0;
        while ((x - index) >= 0 && (y - index) >= 0){
            if (playingGrid[y - index][x - index] == playerSymbol) {
                leftCount++;
                index++;
            } else {
                break;
            }
        }
        index = 0;
        while ((x + index) < size && (y - index) >= 0) {
            if (playingGrid[y - index][x + index] == playerSymbol) {
                rightCount++;
                index++;
            } else {
                break;
            }
        }
        index = 0;
        while ((x - index) >= 0 && (y + index) < size) {
            if (playingGrid[y + index][x - index] == playerSymbol) {
                rightCount++;
                index++;
            } else {
                break;
            }
        }
        return new int[]{leftCount - 1, rightCount - 1};
    }
}
