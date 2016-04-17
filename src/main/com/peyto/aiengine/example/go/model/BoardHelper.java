package com.peyto.aiengine.example.go.model;

/**
 * This class is used for static utility methods, used for easy access to board data
 */
public class BoardHelper {

    public static boolean validateMove(Board board, Move move) {
        if (move.isPass()) {
            return true;
        } else {
            // Validate if the place is free
            // Validate it have free counts/can kill enemy
            // Validate ko
            return true;
        }
    }
}
