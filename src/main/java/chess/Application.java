package chess;

import chess.controller.ChessGameController;
import chess.repository.BoardRepository;
import chess.repository.JdbcBoardRepository;
import chess.repository.JdbcRoomRepository;
import chess.repository.RoomRepository;
import chess.service.GameService;

public class Application {
    public static void main(String[] args) {
        RoomRepository roomRepository = new JdbcRoomRepository();
        BoardRepository boardRepository = new JdbcBoardRepository();

        GameService gameService = new GameService(roomRepository, boardRepository);
        ChessGameController chessGameController = new ChessGameController(gameService);
        chessGameController.run();
    }
}
