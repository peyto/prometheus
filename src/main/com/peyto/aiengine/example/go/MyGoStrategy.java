package com.peyto.aiengine.example.go;

import com.peyto.aiengine.example.go.model.*;

import java.util.Random;


public final class MyGoStrategy implements GoStrategy {

    Random random = new Random();
    @Override
    public void move(Board board, int myPlayerNumber, GameConstants gameConstants, Move move) {
        System.out.println("MyStrategy entered");
        Color color = board.getPlayers().get(myPlayerNumber).getColor();
        Stone newStone = new Stone(random.nextInt(19), random.nextInt(19), color);

        move.setNewStone(newStone);
    }
}
