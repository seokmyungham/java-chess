package chess.controller;

import chess.controller.command.Command;
import chess.controller.command.CommandRouter;
import chess.domain.board.Board;
import chess.domain.game.ChessGame;
import chess.domain.game.Room;
import chess.domain.piece.Color;
import chess.service.GameService;
import chess.view.InputView;
import chess.view.OutputView;

public class ChessGameController {

    private final GameService gameService;

    public ChessGameController(GameService gameService) {
        this.gameService = gameService;
    }

    public void run() {
        OutputView.printSavedRoomNames(gameService.findAllRoomNames());
        Room room = loadRoom();
        OutputView.printEnterRoomMessage(room.getName());
        ChessGame chessGame = createChessGame(room.getId());
        process(room.getId(), chessGame);
    }

    private Room loadRoom() {
        try {
            String input = InputView.readRoomName();
            return gameService.loadRoom(input);
        } catch (RuntimeException error) {
            OutputView.printError(error);
            return loadRoom();
        }
    }

    private ChessGame createChessGame(Long roomId) {
        Color turn = gameService.findTurnByRoomId(roomId);
        Board board = gameService.loadBoard(roomId);
        return new ChessGame(board, turn);
    }

    private void process(Long roomId, ChessGame chessGame) {
        State state = State.RUNNING;
        do {
            state = executeCommand(state, chessGame, roomId);
        } while (state != State.END);
    }

    private State executeCommand(State state, ChessGame chessGame, Long roomId) {
        try {
            Command command = CommandRouter.findCommendByInput(InputView.readCommend());
            return command.execute(gameService, chessGame, roomId);
        } catch (RuntimeException error) {
            OutputView.printError(error);
            return state;
        }
    }
}
