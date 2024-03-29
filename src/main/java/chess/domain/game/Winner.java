package chess.domain.game;

import chess.domain.piece.Color;
import java.util.Arrays;
import java.util.Map;
import java.util.function.BiPredicate;

public enum Winner {

    WHITE_WIN(Score::isGreaterThan),
    BLACK_WIN(Score::isLowerThan),
    DRAW(Score::equals);

    private final BiPredicate<Score, Score> selectWinner;

    Winner(BiPredicate<Score, Score> selectWinner) {
        this.selectWinner = selectWinner;
    }

    public static Winner selectWinnerByScore(Map<Color, Score> teamScore) {
        return Arrays.stream(values())
                .filter(winner -> winner.selectWinner.test(teamScore.get(Color.WHITE), teamScore.get(Color.BLACK)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    public static Winner selectWinnerByCheckmate(Color kingColor) {
        if (kingColor == Color.WHITE) {
            return BLACK_WIN;
        }
        return WHITE_WIN;
    }
}
