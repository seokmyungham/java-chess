package chess.repository;

import chess.domain.board.position.Position;
import chess.domain.piece.Piece;
import java.util.Map;

public interface BoardRepository {

    void savePiece(Piece piece, Position position, Long roomId);

    void deletePieceByPosition(Position position, Long roomId);

    Piece findPieceByPosition(Position position, Long roomId);

    Map<Position, Piece> findAllPieceByRoomId(Long roomId);

    boolean isExistsPiece(Long roomId);
}
