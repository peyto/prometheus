package com.peyto.aiengine.example.go;

import com.peyto.aiengine.example.go.model.*;

public interface GoStrategy {

    void move(Board board, int myPlayerIndex, GameConstants gameConstants, Move move);
}
