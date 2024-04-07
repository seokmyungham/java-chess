package chess.repository.fake;

import chess.domain.board.Board;
import chess.domain.board.BoardFactory;
import chess.domain.board.position.Position;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.repository.BoardRepository;
import java.util.Map;

public class FakeBoardRepository implements BoardRepository {

    @Override
    public void saveBoard(Map<Position, Piece> board, Long roomId) {
    }

    @Override
    public void savePiece(Piece piece, Position position, Long roomId) {
    }

    @Override
    public void deletePieceByPosition(Position position, Long roomId) {
    }

    @Override
    public Piece findPieceByPosition(Position position, Long roomId) {
        return new Piece(PieceType.KING, Color.WHITE);
    }

    @Override
    public Map<Position, Piece> findAllPieceByRoomId(Long roomId) {
        Board board = BoardFactory.createBoard();
        return board.getBoard();
    }

    @Override
    public boolean isExistsPiece(Long roomId) {
        return true;
    }
}
