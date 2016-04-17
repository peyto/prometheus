package com.peyto.aiengine.example.go.model;

public class GameConstants {
    private final long randomSeed;

    private final int boardSize;

    public GameConstants(long randomSeed, int boardSize) {
        this.randomSeed = randomSeed;
        this.boardSize = boardSize;
    }

    public long getRandomSeed() {
        return randomSeed;
    }

    public int getBoardSize() {
        return boardSize;
    }
}
