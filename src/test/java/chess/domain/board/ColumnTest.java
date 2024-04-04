package chess.domain.board;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import chess.domain.board.position.Column;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ColumnTest {

    @Test
    @DisplayName("거리 만큼 이동한 Column를 반환한다.")
    void calculateNextColumnSuccessTest() {
        Column column = Column.E;

        assertAll(
                () -> assertEquals(Column.D, column.calculateNextColumn(-1)),
                () -> assertEquals(Column.F, column.calculateNextColumn(1)),
                () -> assertEquals(Column.E, column.calculateNextColumn(0))
        );
    }

    @Test
    @DisplayName("거리 만큼 이동한 값이 보드판을 벗어난 경우 에러를 반환한다.")
    void calculateNextColumnFailTest() {
        Column column = Column.D;

        assertThatThrownBy(() -> column.calculateNextColumn(-5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("다음 위치로 이동할 수 있는 Column이 존재하지 않습니다.");
    }



    @Test
    @DisplayName("거리 만큼 이동한 값이 보드판을 벗어난 경우 false를 반환한다.")
    void isNextInRange() {
        assertAll(
                () -> assertFalse(Column.A.isNextInRange(-1)),
                () -> assertFalse(Column.H.isNextInRange(1)),
                () -> assertTrue(Column.A.isNextInRange(1)),
                () -> assertTrue(Column.H.isNextInRange(-1))
        );
    }
}
