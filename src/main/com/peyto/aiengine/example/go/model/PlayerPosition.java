package com.peyto.aiengine.example.go.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerPosition {
    private final long playerId;

    private final Color color;
    private final List<Stone> stones;

    private final boolean lastTurnPassed;

    private PlayerPosition(long playerId, Color color, List<Stone> stones, boolean lastTurnPassed) {
        this.playerId = playerId;
        this.color = color;
        this.stones = stones;
        this.lastTurnPassed = lastTurnPassed;
    }

    public PlayerPosition(Player player) {
        this(player.getId(), player.getColor(), new ArrayList<Stone>(), false);
    }

    public long getPlayerId() {
        return playerId;
    }

    public List<Stone> getStones() {
        return stones;
    }

    public boolean isLastTurnPassed() {
        return lastTurnPassed;
    }

    public Color getColor() {
        return color;
    }

    public PlayerPosition copy() {
        return new PlayerPosition(playerId, color, Collections.unmodifiableList(stones), lastTurnPassed);
    }

    /**
     * Put the stone on the board. The only method, which change the state of Player Position
     * @param stone
     */
    public void putStone(Stone stone) {
        stones.add(stone);
    }
}
