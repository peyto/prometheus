package com.peyto.aiengine.example.go.model;

public class Player {
    private final long id;
    private final Color color;
    private final String name;
    private final boolean strategyCrashed;

    public Player(long id, Color color, String name, boolean strategyCrashed) {
        this.id = id;
        this.color = color;
        this.name = name;
        this.strategyCrashed = strategyCrashed;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isStrategyCrashed() {
        return strategyCrashed;
    }

    public Color getColor() {
        return color;
    }

    public Player copy() {
        return new Player(id, color, name, strategyCrashed);
    }
}
