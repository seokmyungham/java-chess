package chess.domain.strategy;

import chess.domain.board.position.Direction;
import chess.domain.board.position.Row;
import java.util.List;

public class WhitePawnMoveStrategy extends PawnMoveStrategy{

    private static final List<Direction> DIRECTIONS = List.of(Direction.N, Direction.NE, Direction.NW);

    public WhitePawnMoveStrategy() {
        super(DIRECTIONS, Row.TWO);
    }
}
