package chess.domain.game;

import chess.domain.board.position.Column;
import chess.domain.board.position.Position;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import java.util.Map;
import java.util.stream.Collectors;

public class ScoreCalculator {

    private static final int PAWN_DUPLICATE_THRESHOLD = 2;
    private static final double PAWN_DEDUCTION_SCORE = 0.5;

    public static Map<Color, Score> calculateTotalScore(Map<Position, Piece> board) {
        return Map.of(
                Color.WHITE, calculateScore(board, Color.WHITE),
                Color.BLACK, calculateScore(board, Color.BLACK)
        );
    }

    private static Score calculateScore(Map<Position, Piece> board, Color color) {
        double sum = sumScore(board, color);
        double pawnMinus = calculatePawnScore(board, color);
        return new Score(sum - pawnMinus);
    }

    private static double sumScore(Map<Position, Piece> board, Color color) {
        return board.values().stream()
                .filter(piece -> piece.isSameColor(color))
                .mapToDouble(Piece::getScore)
                .sum();
    }

    private static double calculatePawnScore(Map<Position, Piece> board, Color color) {
        Map<Column, Long> pawnCountByColumn = countPawnByColumn(board, color);
        return pawnCountByColumn.values().stream()
                .filter(pawnCount -> pawnCount >= PAWN_DUPLICATE_THRESHOLD)
                .mapToDouble(pawnCount -> pawnCount * PAWN_DEDUCTION_SCORE)
                .sum();
    }

    private static Map<Column, Long> countPawnByColumn(Map<Position, Piece> board, Color color) {
        return board.keySet().stream()
                .filter(position -> board.get(position).isPawn(color))
                .collect(Collectors.groupingBy(Position::getColumn, Collectors.counting()));
    }
}
