package chess.domain.board.position;

import java.util.Arrays;

public enum Column {
    A(0),
    B(1),
    C(2),
    D(3),
    E(4),
    F(5),
    G(6),
    H(7);

    private final int index;

    Column(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public Column calculateNextColumn(int distance) {
        return Arrays.stream(values())
                .filter(column -> column.index == this.index + distance)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("다음 위치로 이동할 수 있는 Column이 존재하지 않습니다."));
    }

    public boolean isNextInRange(int distance) {
        int nextIndex = index + distance;
        return A.index <= nextIndex && nextIndex <= H.index;
    }
}
