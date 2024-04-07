package chess.service;

import chess.domain.board.Board;
import chess.domain.board.BoardFactory;
import chess.domain.board.position.Position;
import chess.domain.game.ChessGame;
import chess.domain.game.Room;
import chess.domain.game.RoomName;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.repository.BoardRepository;
import chess.repository.RoomRepository;
import java.util.List;
import java.util.Map;

public class GameService {

    private final RoomRepository roomRepository;
    private final BoardRepository boardRepository;

    public GameService(RoomRepository roomRepository, BoardRepository boardRepository) {
        this.roomRepository = roomRepository;
        this.boardRepository = boardRepository;
    }

    public Room loadRoom(String input) {
        RoomName roomName = new RoomName(input);
        if (roomRepository.isExistsRoomName(roomName)) {
            return roomRepository.findRoomByName(roomName);
        }
        return createNewRoom(roomName);
    }

    private Room createNewRoom(RoomName roomName) {
        roomRepository.saveRoom(roomName);
        return roomRepository.findRoomByName(roomName);
    }

    public ChessGame createChessGame(Long roomId) {
        Color turn = roomRepository.findTurnById(roomId);
        Board board = loadBoard(roomId);
        return new ChessGame(board, turn);
    }

    private Board loadBoard(Long roomId) {
        if (boardRepository.isExistsPiece(roomId)) {
            Map<Position, Piece> board = boardRepository.findAllPieceByRoomId(roomId);
            return new Board(board);
        }
        return createNewBoard(roomId);
    }

    private Board createNewBoard(Long roomId) {
        Board board = BoardFactory.createBoard();
        boardRepository.saveBoard(board.getBoard(), roomId);
        return board;
    }

    public void processTurn(Position source, Position destination, Long roomId) {
        movePiece(source, destination, roomId);
        Color currentTurn = roomRepository.findTurnById(roomId);
        roomRepository.updateRoomTurn(currentTurn.change(), roomId);
    }

    private void movePiece(Position source, Position destination, Long roomId) {
        Piece piece = boardRepository.findPieceByPosition(source, roomId);
        boardRepository.deletePieceByPosition(source, roomId);
        boardRepository.deletePieceByPosition(destination, roomId);
        boardRepository.savePiece(piece, destination, roomId);
    }

    public List<String> findAllRoomNames() {
        return roomRepository.findAllRoomNames();
    }
}
