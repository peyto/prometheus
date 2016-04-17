package com.peyto.aiengine.example.go.simulation.human;

import com.peyto.aiengine.example.go.GoStrategy;
import com.peyto.aiengine.example.go.model.*;
import com.peyto.aiengine.example.go.simulation.human.swing.Goban;
import com.peyto.aiengine.example.go.simulation.human.swing.MoveMaker;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class HumanStrategy implements GoStrategy {

    private final Goban goban;
    private final MoveMaker moveMaker;
    private final JFrame window;

    public HumanStrategy(Color playerColor) {
        window = new JFrame("Go Goban");
        window.setSize(600, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        goban = new Goban();
        window.add(goban);

        moveMaker = new MoveMaker(goban, playerColor);
        goban.addMouseListener(moveMaker);
    }


    @Override
    public void move(Board board, int myPlayerIndex, GameConstants gameConstants, Move move) {
        // drawPosition();
        goban.setCurrentPosition(board);

        goban.forceDrawGoban();

        moveMaker.boundAndWaitMove();
        // wait for human response
        while (!moveMaker.moveReady()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Move newMove = moveMaker.getMoveResultAndUnbound();
        if (!newMove.isPass() && newMove.getNewStone()!=null) {
            // draw new stone
            goban.addNewStoneLocally(newMove);
            goban.forceDrawGoban();
        }

        // set move
        move.setNewStone(newMove.getNewStone());
        move.setPass(newMove.isPass());
    }

    public static void main(String[] agrs) {
        HumanStrategy humanStrategy = new HumanStrategy(Color.BLACK);
        Player whiteP = new Player(0, Color.WHITE, "a1", false);
        Player blackP = new Player(1, Color.BLACK, "a2", false);
        PlayerPosition white = new PlayerPosition(whiteP);
        PlayerPosition black = new PlayerPosition(blackP);
        List<Player> listP = new ArrayList<>();
        listP.add(blackP);
        listP.add(whiteP);
        List<PlayerPosition> list = new ArrayList<>();
        list.add(black);
        list.add(white);

        white.putStone(new Stone(2, 15, Color.WHITE));
        white.putStone(new Stone(4, 16, Color.WHITE));
        white.putStone(new Stone(15, 5, Color.WHITE));
        white.putStone(new Stone(14, 7, Color.WHITE));

        black.putStone(new Stone(4, 4, Color.BLACK));
        black.putStone(new Stone(3, 6, Color.BLACK));

        Move lastMove = new Move(new Stone(3, 6, Color.BLACK), false);
        Board board = new Board(3, 0, 19, listP, list, lastMove);
        humanStrategy.move(board, 0, new GameConstants(123, 19), new Move());
    }

}
