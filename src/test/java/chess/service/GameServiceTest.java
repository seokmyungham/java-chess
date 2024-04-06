package chess.service;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.game.Room;
import chess.repository.BoardRepository;
import chess.repository.RoomRepository;
import chess.repository.fake.FakeBoardRepository;
import chess.repository.fake.FakeRoomRepository;
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
}
