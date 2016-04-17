package com.peyto.aiengine.example.go.simulation.human.swing;

import com.peyto.aiengine.example.go.model.Color;
import com.peyto.aiengine.example.go.model.Move;
import com.peyto.aiengine.example.go.model.Stone;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MoveMaker implements MouseListener {

    private final Goban goban;

    private volatile Stone currentStone = null;
    private final Color playerColor;

    public MoveMaker(Goban goban, Color playerColor) {
        this.goban = goban;
        this.playerColor = playerColor;
    }

    public boolean moveReady() {
        return currentStone!=null;
    }

    public synchronized void boundAndWaitMove() {
        currentStone = null;
        goban.forgetManualStone();
        goban.addMouseListener(this);
    }

    public synchronized Move getMoveResultAndUnbound() {
        goban.removeMouseListener(this);
        return new Move(currentStone, false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (currentStone==null) {
            synchronized (this) {
                if (currentStone!=null) return;
                int x = (e.getX()-Goban.OFFSET_X)/Goban.CELL_SIZE;
                int y = (e.getY()-Goban.OFFSET_Y)/Goban.CELL_SIZE;
                if (x>=0 && x<19 && y>=0 && y<19) {
                    System.out.println("Mouse clicked on: "+x+", "+y);
                    boolean isOccupied = goban.getAllStones().stream().anyMatch(stone -> stone.getX()==x && stone.getY()==y);
                    if (!isOccupied) {
                        currentStone = new Stone(x, y, playerColor);
                    } else {
                        System.out.println("Cell already occupied");
                    }
                }
            }
        } else {
            System.out.println("Stone already placed, click on: " + e.getX() + ", " + e.getY());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
