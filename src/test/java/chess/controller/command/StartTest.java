package chess.controller.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.controller.State;
import chess.domain.board.Board;
import chess.domain.board.BoardFactory;
import chess.domain.game.ChessGame;
import chess.domain.piece.Color;
import chess.repository.BoardRepository;
import chess.repository.RoomRepository;
import chess.repository.fake.FakeBoardRepository;
import chess.repository.fake.FakeRoomRepository;
import chess.service.GameService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StartTest {

    private RoomRepository roomRepository;
    private BoardRepository boardRepository;

    @BeforeEach
    void setUp() {
        roomRepository = new FakeRoomRepository();
        boardRepository = new FakeBoardRepository();
    }

    @DisplayName("명령어 입력이 start로만 이루어져 있으면 정상적으로 생성된다.")
    @Test
    void validateCommandInputSize() {
        assertThatNoException()
                .isThrownBy(() -> new Start(List.of("start")));
    }

    @DisplayName("명령어 입력이 start로만 이루어져 있지 않으면 에러를 발생시킨다..")
    @Test
    void validateCommandInputSizeFailTest() {
        assertThatThrownBy(() -> new Start(List.of("start", "2", " ")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("게임 시작 명령어 입력 형식이 올바르지 않습니다.");
    }

    @DisplayName("기능을 수행한 후 RUNNING 상태를 반환한다.")
    @Test
    void executeTest() {
        Start start = new Start(List.of("start"));
        GameService gameService = new GameService(roomRepository, boardRepository);
        Board board = BoardFactory.createBoard();
        State gameState = start.execute(gameService, new ChessGame(board, Color.WHITE), 0L);

        assertThat(gameState).isEqualTo(State.RUNNING);
    }

}
