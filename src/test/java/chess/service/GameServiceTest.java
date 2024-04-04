package chess.service;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.board.Board;
import chess.domain.board.BoardFactory;
import chess.domain.game.Room;
import chess.repository.BoardRepository;
import chess.repository.RoomRepository;
import chess.repository.fake.FakeBoardRepository;
import chess.repository.fake.FakeRoomRepository;
import java.util.HashMap;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GameServiceTest {

    private GameService gameService;

    @BeforeEach
    void setUp() {
        RoomRepository roomRepository = new FakeRoomRepository();
        BoardRepository boardRepository = new FakeBoardRepository();
        gameService = new GameService(roomRepository, boardRepository);
    }

    @Test
    @DisplayName("입력으로 주어진 이름을 갖는 게임 방을 반환한다.")
    void loadRoomTest() {
        String roomName = "jazz";

        Room room = gameService.loadRoom(roomName);

        assertThat(room.getName()).isEqualTo(roomName);
    }

    @Test
    @DisplayName("게임 보드를 생성한다.")
    void loadBoardTest() {
        Board board = gameService.loadBoard(0L);

        Board expectedBoard = new Board(new HashMap<>());
        BoardFactory.initialize(expectedBoard);

        Assertions.assertThat(expectedBoard.getBoard()).isEqualTo(board.getBoard());
    }
}
