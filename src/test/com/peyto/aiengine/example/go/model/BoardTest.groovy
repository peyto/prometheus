package com.peyto.aiengine.example.go.model

class BoardTest extends spock.lang.Specification {

    Board board;
    
    def setup() {
        board = Board.createNewBoard(2, Board.DEFAULT_BOARD_SIZE, ["t1","t2"] as String[]);
    }

    def "CreateNewBoard: board properties after creation"() {
        given:
        Board board = Board.createNewBoard(2, Board.DEFAULT_BOARD_SIZE, ["t1","t2","t3"] as String[]);

        expect:
        board.gameOver == false
        board.currentPlayer == 0
        board.turn == 0
        board.lastMove == null
        board.allStones.isEmpty()
        board.boardSize == 19
        board.players.size() == 2
        board.players.get(0).name.equals("t1")
        board.players.get(1).name.equals("t2")
        board.playerPositions.size() == 2
        for (int i = 0; i < 2; i++) {
            board.playerPositions.get(i).playerId == board.players.get(i).id
            board.playerPositions.get(i).color == board.players.get(i).color
            board.playerPositions.get(i).lastTurnPassed == false
            board.playerPositions.get(i).stones.isEmpty()
        }

    }

    def "CreateNewBoard: unusual board properties"() {
        given:
        Board board = Board.createNewBoard(4, 17, ["t1","t2","t3", "t4"] as String[]);

        expect:
        board.allStones.isEmpty()
        board.boardSize == 17
        board.players.size() == 4

        board.playerPositions.size() == 4
        for (int i = 0; i < 4; i++) {
            board.players.get(i).name.equals("t"+(i+1))
            board.playerPositions.get(i).playerId == board.players.get(i).id
            board.playerPositions.get(i).color == board.players.get(i).color
        }
        // All colors are unique
        //Set<Color> uniqueColors = board.players.stream().map(Player::getColor()).collect(Collectors.toSet());
        //uniqueColors.size() == board.players.size()
    }
    
    def "GetAllStones"() {

    }

    def "IsGameOver"() {

    }

    def "ApplyTurn"() {
        given:
        def move = new Move();

        when: "add only one item"
        board.applyTurn(1, new Move())

        then: "expect value of the item"
        board.lastMove == move

    }
}
