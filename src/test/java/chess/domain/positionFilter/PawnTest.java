package chess.domain.positionFilter;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.board.Board;
import chess.domain.board.position.Column;
import chess.domain.board.position.Direction;
import chess.domain.board.position.Position;
import chess.domain.board.position.Row;
import chess.domain.game.PositionsFilter;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PawnTest {

    @Test
    @DisplayName("시작 위치 앞 2칸이 비어있을 시 이동 가능한 포지션에 두 칸 모두 포함되어야 한다.")
    void startWithEmptyFrontTwoPositionsTest() {
        Piece piece = new Piece(PieceType.WHITE_PAWN, Color.WHITE);
        Position position = new Position(Row.TWO, Column.D);
        Map<Direction, Queue<Position>> candidatePositions = piece.generateAllDirectionPositions(position);
        Board board = new Board(
                Map.of(
                        position, piece,
                        new Position(Row.THREE, Column.C), new Piece(PieceType.ROOK, Color.WHITE),
                        new Position(Row.THREE, Column.E),  new Piece(PieceType.KNIGHT, Color.WHITE)
                )
        );

        List<Position> movablePositions = PositionsFilter.generateValidPositions(candidatePositions, piece, board);

        assertThat(movablePositions).containsExactlyInAnyOrder(
                new Position(Row.THREE, Column.D),
                new Position(Row.FOUR, Column.D)
        );
    }

    @Test
    @DisplayName("대각선에 상대 기물이 존재하면 이동할 수 있는 위치에 포함되어야 한다.")
    void diagonalMoveWithOpponentPiecePresentTest() {
        Piece piece = new Piece(PieceType.WHITE_PAWN, Color.WHITE);
        Position position = new Position(Row.TWO, Column.D);
        Map<Direction, Queue<Position>> candidatePositions = piece.generateAllDirectionPositions(position);
        Board board = new Board(
                Map.of(
                        position, piece,
                        new Position(Row.THREE, Column.C), new Piece(PieceType.ROOK, Color.BLACK),
                        new Position(Row.THREE, Column.E),  new Piece(PieceType.KNIGHT, Color.BLACK),
                        new Position(Row.THREE, Column.D),  new Piece(PieceType.BISHOP, Color.BLACK)
                )
        );

        List<Position> movablePositions = PositionsFilter.generateValidPositions(candidatePositions, piece, board);

        assertThat(movablePositions).containsExactlyInAnyOrder(
                new Position(Row.THREE, Column.C),
                new Position(Row.THREE, Column.E)
        );
    }

    @Test
    @DisplayName("앞에 기물이 존재하고 우리팀 기물일 시 이동가능한 위치에 포함되어서는 안된다.")
    void cannotMoveIfFriendlyPieceAhead() {
        Piece piece = new Piece(PieceType.WHITE_PAWN, Color.WHITE);
        Position position = new Position(Row.TWO, Column.D);
        Map<Direction, Queue<Position>> candidatePositions = piece.generateAllDirectionPositions(position);
        Board board = new Board(
                Map.of(
                        position, piece,
                        new Position(Row.THREE, Column.D), new Piece(PieceType.ROOK, Color.WHITE)
                )
        );

        List<Position> movablePositions = PositionsFilter.generateValidPositions(candidatePositions, piece, board);

        assertThat(movablePositions).isEmpty();
    }
}
