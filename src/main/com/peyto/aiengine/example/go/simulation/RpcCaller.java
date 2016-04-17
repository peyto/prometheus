package com.peyto.aiengine.example.go.simulation;

import com.peyto.aiengine.example.go.GoStrategy;
import com.peyto.aiengine.example.go.model.Board;
import com.peyto.aiengine.example.go.model.GameConstants;
import com.peyto.aiengine.example.go.model.Move;

import java.net.Socket;

public class RpcCaller implements GoStrategy{
    private final Socket socket;

    public RpcCaller(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void move(Board board, int myPlayerNumber, GameConstants gameConstants, Move move) {

    }
}
