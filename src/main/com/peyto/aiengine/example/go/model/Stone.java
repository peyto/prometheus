package com.peyto.aiengine.example.go.model;

public class Stone {
    private int x;
    private int y;

    private Color color;

    public Stone(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    private String getCoordinates() {
        return "Stone ["+getXAsString() + getYAsString()+"]["+color+"]";
    }

    private char getXAsString() {
        return (char) ((int)'A' + x);
    }

    private int getYAsString() {
        return y+1;
    }

    public Stone copy() {
        return new Stone(x, y, color);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stone stone = (Stone) o;

        if (x != stone.x) return false;
        if (y != stone.y) return false;
        return color == stone.color;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + color.hashCode();
        return result;
    }
}
