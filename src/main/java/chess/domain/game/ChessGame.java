package chess.domain.game;

import chess.domain.board.Board;
import chess.domain.board.position.Direction;
import chess.domain.board.position.Position;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.service.dto.ChessGameResult;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class ChessGame {

    private final Board board;
    private Color currentTurn;

    public ChessGame(Board board, Color currentTurn) {
        this.board = board;
        this.currentTurn = currentTurn;
    }

    public void processTurn(Position source, Position destination) {
        Piece sourcePiece = board.findPieceByPosition(source);
        validateUserTurn(sourcePiece);
        List<Position> movablePositions = generateMovablePositions(sourcePiece, source);
        if (movablePositions.contains(destination)) {
            board.movePiece(source, destination);
            currentTurn = currentTurn.change();
            return;
        }
        throw new IllegalArgumentException("기물을 해당 위치로 이동시킬 수 없습니다.");
    }

    private void validateUserTurn(Piece piece) {
        if (!piece.isSameColor(currentTurn)) {
            throw new IllegalArgumentException("상대방의 기물을 움직일 수 없습니다. 현재 턴 : " + currentTurn);
        }
    }

    private List<Position> generateMovablePositions(Piece piece, Position source) {
        Map<Direction, Queue<Position>> candidatePositions = piece.generateAllDirectionPositions(source);
        return PositionsFilter.generateValidPositions(candidatePositions, piece, board);
    }

    public ChessGameResult generateGameResult() {
        Map<Color, Score> totalScore = ScoreCalculator.calculateTotalScore(board.getBoard());
        if (board.isGameOver()) {
            return new ChessGameResult(Winner.selectWinnerByCheckmate(board.findWinnerColor()), totalScore);
        }
        return new ChessGameResult(Winner.selectWinnerByScore(totalScore), totalScore);
    }

    public boolean isGameOver() {
        return board.isGameOver();
    }

    public Map<Position, Piece> getBoard() {
        return board.getBoard();
    }
}
