package chess.domain.game;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import chess.domain.board.position.Column;
import chess.domain.board.position.Position;
import chess.domain.board.position.Row;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ScoreCalculatorTest {

    @Test
    @DisplayName("기물 점수 계산 테스트")
    void calculateTeamScoreTest() {
        Map<Position, Piece> map = new HashMap<>();
        map.put(new Position(Row.ONE, Column.B), new Piece(PieceType.WHITE_PAWN, Color.WHITE));
        map.put(new Position(Row.TWO, Column.B), new Piece(PieceType.WHITE_PAWN, Color.WHITE));
        map.put(new Position(Row.THREE, Column.B), new Piece(PieceType.WHITE_PAWN, Color.WHITE));
        map.put(new Position(Row.ONE, Column.D), new Piece(PieceType.QUEEN, Color.WHITE));
        map.put(new Position(Row.ONE, Column.E), new Piece(PieceType.KING, Color.WHITE));
        map.put(new Position(Row.TWO, Column.F), new Piece(PieceType.ROOK, Color.WHITE));

        map.put(new Position(Row.EIGHT, Column.G), new Piece(PieceType.KNIGHT, Color.BLACK));
        map.put(new Position(Row.EIGHT, Column.F), new Piece(PieceType.BISHOP, Color.BLACK));
        map.put(new Position(Row.EIGHT, Column.E), new Piece(PieceType.KING, Color.BLACK));
        map.put(new Position(Row.SEVEN, Column.F), new Piece(PieceType.BLACK_PAWN, Color.BLACK));
        map.put(new Position(Row.SEVEN, Column.D), new Piece(PieceType.BLACK_PAWN, Color.BLACK));
        map.put(new Position(Row.SEVEN, Column.E), new Piece(PieceType.BLACK_PAWN, Color.BLACK));

        Map<Color, Score> scoreOfTeam = ScoreCalculator.calculateTotalScore(map);
        Score whiteScore = scoreOfTeam.get(Color.WHITE);
        Score blackScore = scoreOfTeam.get(Color.BLACK);

        assertAll(
                () -> assertEquals(whiteScore, new Score(15.5)),
                () -> assertEquals(blackScore, new Score(8.5))
        );
    }
}
