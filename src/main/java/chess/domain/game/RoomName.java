package chess.domain.game;

public class RoomName {

    private static final int ROOM_NAME_MAX_LENGTH = 16;

    private final String name;

    public RoomName(String name) {
        if (name.length() > ROOM_NAME_MAX_LENGTH) {
            throw new IllegalArgumentException("게임 방 이름 길이는 최대 16글자까지 가능합니다.");
        }
        this.name = name;
    }

    public String getValue() {
        return name;
    }
}
