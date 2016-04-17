package com.peyto.aiengine.example.go.model;

public class Move {
    private Stone newStone;
    private boolean pass;

    public Move(Stone newStone, boolean pass) {
        this.newStone = newStone;
        this.pass = pass;
    }

    public Move() {}

    public Stone getNewStone() {
        return newStone;
    }

    public void setNewStone(Stone newStone) {
        this.newStone = newStone;
    }

    public boolean isPass() {
        return pass;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public Move copy() {
        return new Move(newStone.copy(), pass);
    }

}
