package chess.domain.board;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import chess.domain.board.position.Row;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RowTest {

    @Test
    @DisplayName("거리 만큼 이동한 Row를 반환한다.")
    void calculateNextRowSuccessTest() {
        Row row = Row.FOUR;

        assertAll(
            () -> assertEquals(Row.FIVE, row.calculateNextRow(1)),
            () -> assertEquals(Row.THREE, row.calculateNextRow(-1)),
            () -> assertEquals(Row.FOUR, row.calculateNextRow(0))
        );
    }

    @Test
    @DisplayName("거리 만큼 이동한 값이 보드판을 벗어난 경우 에러를 반환한다.")
    void calculateNextRowFailTest() {
        Row row = Row.FOUR;

        assertThatThrownBy(() -> row.calculateNextRow(-5))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("거리 만큼 이동한 값이 보드판을 벗어난 경우 false를 반환한다.")
    void isNextInRange() {
        assertAll(
            () -> assertFalse(Row.EIGHT.isNextInRange(1)),
            () -> assertFalse(Row.ONE.isNextInRange(-1))
        );
    }
}
