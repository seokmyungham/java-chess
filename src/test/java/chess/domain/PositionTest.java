package chess.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PositionTest {

    @Test
    @DisplayName("포지션 생성 테스트")
    void constructTest() {
        Position position = new Position(Row.THREE, Column.H);

        Assertions.assertThat(position).isNotNull();
    }
}
