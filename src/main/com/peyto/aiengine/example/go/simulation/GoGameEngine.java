package com.peyto.aiengine.example.go.simulation;

import com.peyto.aiengine.example.go.GoStrategy;
import com.peyto.aiengine.example.go.MyGoStrategy;
import com.peyto.aiengine.example.go.model.*;
import com.peyto.aiengine.example.go.simulation.human.HumanStrategy;

import java.io.IOException;
import java.util.concurrent.*;

public class GoGameEngine {


    public static final int MAX_TIMEOUT = 360_000;
    private final GameConstants gameConstants;
    private final Board board;
    private final int playersNumber = 2;
    private final int THREAD_COUNT = 2;
    ExecutorService execService = Executors.newFixedThreadPool(THREAD_COUNT);

    private final GoStrategy[] strategies = new GoStrategy[playersNumber];


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        if (args.length == 3) {
            new GoGameEngine(args).run();
        } else {
            new GoGameEngine(new String[]{"31001", "0000000000000000", "19"}).run();
        }
    };

    private void getLocalClients() {
        strategies[0] = new HumanStrategy(Color.BLACK);
        strategies[1] = new MyGoStrategy();
    }

    private void getRemoteClients(String port) throws IOException {
        final ServerProcess server = new ServerProcess(Integer.parseInt(port), playersNumber);
        server.waitClientsToConnect();

        for (int i = 0; i<playersNumber; i++) {
            strategies[i] = server.getClient(i);
        }
    }

    private GoGameEngine(String[] args) throws IOException {
        //serverProcess = new ServerProcess(args[0], Integer.parseInt(args[1]));
        //token = args[2];
        long random = Long.parseLong(args[1]);
        int boardSize = Integer.parseInt(args[2]);

        gameConstants = new GameConstants(random, boardSize);
        board = Board.createNewBoard(playersNumber, boardSize, new String[] {"test1", "test2"});

        if ("local".equals(args[0])) {
            getLocalClients();
        } else {
            getRemoteClients(args[0]);
        }
    }

    public int switchPlayer(int currentPlayer) {
        int nextPlayer = currentPlayer+1;
        return nextPlayer>=playersNumber ? 0 : nextPlayer;
    }

    private void validateMove(Board board, Move move) {
        boolean validTurn = BoardHelper.validateMove(board, move);
        if (!validTurn) {
            // If the turn wasn't legal, we just pass on behalf Of player
            move.setPass(true);
        }
    }

    private void makeControlledMove(GoStrategy strategy, Board board, int playerIndex, GameConstants gameConstants, MoveResult move) {
        // make protection - run in a separte controlled thread
        Callable<Boolean> callable = (Callable<Boolean>) () -> {
            strategy.move(board, playerIndex, gameConstants, move);
            return true;
        };
        Future<Boolean> result = execService.submit(callable);

        try {
            Boolean waitForExecution = result.get(MAX_TIMEOUT, TimeUnit.MILLISECONDS);
            move.setFailed(!waitForExecution);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException | TimeoutException e) {
            System.out.println("Task Wasn't Executed on time or had an exception");
            move.setFailed(true);
        }
    }

    public void run() {
        int currentPlayer = 0;
        while (!board.isGameOver()) {
            GoStrategy strategy = strategies[currentPlayer];

            MoveResult move = new MoveResult();
            makeControlledMove(strategy, board.copy(), currentPlayer, gameConstants, move);

            validateMove(board, move);
            board.applyTurn(switchPlayer(currentPlayer), move);

            currentPlayer = board.getCurrentPlayer();
        }
        // count and write game results
    }

}
