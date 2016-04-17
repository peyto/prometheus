package com.peyto.aiengine.example.go.model;

import java.util.*;
import java.util.stream.Collectors;

public class Board {

    public static final int DEFAULT_BOARD_SIZE = 19;

    private int turn;
    private final int boardSize;
    private int currentPlayer;
    private final List<Player> players;
    private final List<PlayerPosition> playerPositions;
    private Move lastMove;

    public Board(int turn, int currentPlayer, int boardSize, List<Player> players, List<PlayerPosition> playerPositions, Move lastMove) {
        this.turn = turn;
        this.currentPlayer = currentPlayer;
        this.boardSize = boardSize;
        this.players = players;
        this.playerPositions = playerPositions;
        this.lastMove = lastMove;
    }

    private static Player createPlayer(int index, String name) {
        return new Player(index, Color.getColorByIndex(index), name, false);
    }

    public static Board createNewBoard(int playersNumber, int boardSize, String[] playerNames) {
        List<Player> players =  new ArrayList<Player>();
        List<PlayerPosition> playerPositions = new ArrayList<PlayerPosition>();
        for (int i=0; i<playersNumber; i++) {
            players.add(createPlayer(i, playerNames[i]));
            playerPositions.add(new PlayerPosition(players.get(i)));
        }
        return new Board(0, 0, boardSize, players, playerPositions, null);
    }

    public int getTurn() {
        return turn;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }


    public List<PlayerPosition> getPlayerPositions() {
        return Collections.unmodifiableList(playerPositions);
    }

    public List<Stone> getAllStones() {
        return playerPositions.stream().flatMap(p -> p.getStones().stream()).collect(Collectors.toList());
    }

    public boolean isGameOver() {
        return playerPositions.stream().allMatch(position -> position.isLastTurnPassed());
    }
    /**
     * Returns last move, made by any Player
     * If the move was illegal, it is ignored and played as "pass"
     * @return
     */
    public Move getLastMove() {
        return lastMove;
    }

    public Board copy() {
        List<Player> playersNew = Collections.unmodifiableList(players);
        List<PlayerPosition> playerPositionsNew = Collections.unmodifiableList(playerPositions.stream().
                        map(p -> p.copy()).
                        collect(Collectors.toList())
                );

        return new Board(turn, currentPlayer, boardSize, playersNew, playerPositionsNew, lastMove!=null ? lastMove.copy() : null);
    }

    /**
     * Method which apply actual move to the Board.
     * The only method (except Constructor) which actually change the state of the board
     * It should be applied on the server side only
     * User can also apply it, as he recieves a copy, but it is not recommended

     * @param nextPlayer
     * @param move
     */
    public void applyTurn(int nextPlayer, Move move) {
        if (!move.isPass()) {
            playerPositions.get(currentPlayer).putStone(move.getNewStone());
            lastMove = move;
            currentPlayer = nextPlayer;
        }
    }
}
