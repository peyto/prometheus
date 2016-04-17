package com.peyto.aiengine.example.go;

import com.peyto.aiengine.example.go.model.GameConstants;
import com.peyto.aiengine.example.go.model.Move;
import com.peyto.aiengine.example.go.model.PlayerContext;

import java.io.IOException;

public final class Runner {
    private final RemoteProcessClient remoteProcessClient;
    private final String token;
    private final GoStrategy strategy;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        if (args.length == 3) {
            new Runner(args).run();
        } else {
            new Runner(new String[]{"127.0.0.1", "31001", "0000000000000000"}).run();
        }
    }

    private Runner(String[] args) throws IOException {
        this(new MyGoStrategy(), args);
    }

    private Runner(GoStrategy strategy, String[] args) throws IOException {
        remoteProcessClient = new RemoteProcessClient(args[0], Integer.parseInt(args[1]));
        token = args[2];
        this.strategy = strategy;
    }

    public void run() throws IOException, ClassNotFoundException {
        try {
            remoteProcessClient.writeToken(token);
            remoteProcessClient.writeProtocolVersion();
            GameConstants gameConstants = remoteProcessClient.readGameConstants();

            PlayerContext playerContext;

            Move move = new Move();
            while ((playerContext = remoteProcessClient.readPlayerContext()) != null) {
                strategy.move(playerContext.getBoard(), playerContext.getPlayerIndex(), gameConstants, move);
                remoteProcessClient.writeMove(move);
            }

        }
        finally
        {
        remoteProcessClient.close();
        }
    }
}
