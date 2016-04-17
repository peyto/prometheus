package com.peyto.aiengine.example.go.model;

public enum Color {
    BLACK(0),
    WHITE(1);

    private final int colorIndex;

    private Color(int colorIndex) {
        this.colorIndex = colorIndex;
    }

    public static Color getColorByIndex(int i) {
        for (Color c : Color.values()) {
            if (c.colorIndex==i) {
                return c;
            }
        }
        // Better undef, but we are ok here
        return BLACK;
    }
}
