package chess.controller.command;

import chess.controller.State;
import chess.domain.game.ChessGame;
import chess.service.GameService;

public interface Command {

    State execute(GameService gameService, ChessGame chessGame, Long roomId);
}
