package com.peyto.aiengine.example.go.model;

public final class PlayerContext {
    private final int playerIndex;
    private final Board board;

    public PlayerContext(int playerIndex, Board board) {
        this.playerIndex = playerIndex;
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }
}
