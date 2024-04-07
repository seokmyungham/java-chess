package chess.domain.board;

import chess.domain.board.position.Column;
import chess.domain.board.position.Position;
import chess.domain.board.position.Row;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import java.util.HashMap;
import java.util.Map;

public class BoardFactory {

    public static Board createBoard() {
        Map<Position, Piece> board = new HashMap<>();
        initializeBlackTeam(board);
        initializeWhiteTeam(board);
        return new Board(board);
    }

    private static void initializeBlackTeam(Map<Position, Piece> board) {
        initializePawn(board, Row.SEVEN, Color.BLACK);
        initializeSpecialPiece(board, Row.EIGHT, Color.BLACK);
    }

    private static void initializeWhiteTeam(Map<Position, Piece> board) {
        initializePawn(board, Row.TWO, Color.WHITE);
        initializeSpecialPiece(board, Row.ONE, Color.WHITE);
    }

    private static void initializePawn(Map<Position, Piece> board, Row row, Color color) {
        for (Column column : Column.values()) {
            Position position = new Position(row, column);
            if (color == Color.WHITE) {
                board.put(position, new Piece(PieceType.WHITE_PAWN, color));
            }
            if (color == Color.BLACK) {
                board.put(position, new Piece(PieceType.BLACK_PAWN, color));
            }
        }
    }

    private static void initializeSpecialPiece(Map<Position, Piece> board, Row row, Color color) {
        board.put(new Position(row, Column.A), new Piece(PieceType.ROOK, color));
        board.put(new Position(row, Column.H), new Piece(PieceType.ROOK, color));

        board.put(new Position(row, Column.B), new Piece(PieceType.KNIGHT, color));
        board.put(new Position(row, Column.G), new Piece(PieceType.KNIGHT, color));

        board.put(new Position(row, Column.C), new Piece(PieceType.BISHOP, color));
        board.put(new Position(row, Column.F), new Piece(PieceType.BISHOP, color));

        board.put(new Position(row, Column.D), new Piece(PieceType.QUEEN, color));
        board.put(new Position(row, Column.E), new Piece(PieceType.KING, color));
    }
}
