package com.peyto.aiengine.example.go.simulation;

import com.peyto.aiengine.example.go.model.Move;

public class MoveResult extends Move {
    private boolean failed = false;

    public void setFailed(boolean failed) {
        this.failed = failed;
    }

    public boolean isFailed() {
        return failed;
    }
}

