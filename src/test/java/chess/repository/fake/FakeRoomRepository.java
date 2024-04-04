package chess.repository.fake;

import chess.domain.game.Room;
import chess.domain.game.RoomName;
import chess.domain.piece.Color;
import chess.repository.RoomRepository;
import java.util.Collections;
import java.util.List;

public class FakeRoomRepository implements RoomRepository {

    @Override
    public List<String> findAllRoomNames() {
        return Collections.emptyList();
    }

    @Override
    public boolean isExistsRoomName(RoomName roomName) {
        return true;
    }

    @Override
    public void saveRoom(RoomName roomName) {
    }

    @Override
    public void updateRoomTurn(Color color, Long roomId) {
    }

    @Override
    public Room findRoomByName(RoomName roomName) {
        return new Room(0L, roomName.getValue());
    }

    @Override
    public Color findTurnById(Long roomId) {
        return Color.WHITE;
    }
}
