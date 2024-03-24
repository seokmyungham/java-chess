package chess.domain.strategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.domain.board.position.Column;
import chess.domain.board.position.Direction;
import chess.domain.board.position.Position;
import chess.domain.board.position.Row;
import java.util.Map;
import java.util.Queue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QueenMoveStrategyTest {

    /**
     * ........  8 (rank 8)
     * ........  7
     * ........  6
     * ........  5
     * ........  4
     * ........  3
     * ...q....  2
     * ........  1 (rank 1)
     *
     * abcdefgh
     */
    @Test
    @DisplayName("퀸이 D2 에 있을 때 방향에 따라 움직일 수 있는 후보 포지션을 차례대로 저장한다.")
    void calculateCandidateOnEdgePosition() {
        Position position = new Position(Row.TWO, Column.D);
        MoveStrategy moveStrategy = new QueenMoveStrategy();

        Map<Direction, Queue<Position>> directionListMap = moveStrategy.generateMovablePositions(position);

        assertAll(
                () -> assertThat(directionListMap.get(Direction.N)).containsExactly(
                        new Position(Row.THREE, Column.D),
                        new Position(Row.FOUR, Column.D),
                        new Position(Row.FIVE, Column.D),
                        new Position(Row.SIX, Column.D),
                        new Position(Row.SEVEN, Column.D),
                        new Position(Row.EIGHT, Column.D)
                ),
                () -> assertThat(directionListMap.get(Direction.NE)).containsExactly(
                        new Position(Row.THREE, Column.E),
                        new Position(Row.FOUR, Column.F),
                        new Position(Row.FIVE, Column.G),
                        new Position(Row.SIX, Column.H)
                ),
                () -> assertThat(directionListMap.get(Direction.E)).containsExactly(
                        new Position(Row.TWO, Column.E),
                        new Position(Row.TWO, Column.F),
                        new Position(Row.TWO, Column.G),
                        new Position(Row.TWO, Column.H)
                        ),
                () -> assertThat(directionListMap.get(Direction.SE)).containsExactly(
                        new Position(Row.ONE, Column.E)
                ),
                () -> assertThat(directionListMap.get(Direction.S)).containsExactly(
                        new Position(Row.ONE, Column.D)
                ),
                () -> assertThat(directionListMap.get(Direction.SW)).containsExactly(
                        new Position(Row.ONE, Column.C)
                ),
                () -> assertThat(directionListMap.get(Direction.W)).containsExactly(
                        new Position(Row.TWO, Column.C),
                        new Position(Row.TWO, Column.B),
                        new Position(Row.TWO, Column.A)
                        ),
                () -> assertThat(directionListMap.get(Direction.NW)).containsExactly(
                        new Position(Row.THREE, Column.C),
                        new Position(Row.FOUR, Column.B),
                        new Position(Row.FIVE, Column.A)
                )
        );
    }
}
