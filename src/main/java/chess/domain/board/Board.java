package chess.domain.board;

import chess.domain.board.position.Position;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private static final int GAME_OVER_KING_COUNT = 1;

    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public boolean isGameOver() {
        return findKing().size() == GAME_OVER_KING_COUNT;
    }

    public Color findWinnerColor() {
        return findKing().get(0).getColor();
    }

    private List<Piece> findKing() {
        return board.values().stream()
                .filter(Piece::isKing)
                .toList();
    }

    public void movePiece(Position from, Position to) {
        Piece fromPiece = board.get(from);
        board.put(to, fromPiece);
        board.remove(from);
    }

    public Piece findPieceByPosition(Position position) {
        if (existPiece(position)) {
            return board.get(position);
        }
        throw new IllegalArgumentException("해당 위치에 기물이 없습니다.");
    }

    public boolean isEmptySpace(Position position) {
        return !existPiece(position);
    }

    public boolean existPiece(Position position) {
        return board.containsKey(position);
    }

    public void put(Position position, Piece piece) {
        board.put(position, piece);
    }

    public Map<Position, Piece> getBoard() {
        return new HashMap<>(board);
    }
}
