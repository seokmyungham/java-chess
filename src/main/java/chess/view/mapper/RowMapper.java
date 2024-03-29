package chess.view.mapper;

import chess.domain.board.position.Row;
import java.util.Arrays;

public enum RowMapper {
    RANK1(Row.ONE, "1"),
    RANK2(Row.TWO, "2"),
    RANK3(Row.THREE, "3"),
    RANK4(Row.FOUR, "4"),
    RANK5(Row.FIVE, "5"),
    RANK6(Row.SIX, "6"),
    RANK7(Row.SEVEN, "7"),
    RANK8(Row.EIGHT, "8");

    private final Row row;
    private final String value;

    RowMapper(Row row, String value) {
        this.row = row;
        this.value = value;
    }

    public static Row findByInputValue(String value) {
        return Arrays.stream(values())
                .filter(row -> row.value.equals(value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("입력과 일치하는 열이 존재하지 않습니다."))
                .row;
    }

    public static String findByRow(Row row) {
        return Arrays.stream(values())
                .filter(rowMapper -> rowMapper.row == row)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("열과 일치하는 문자열이 존재하지 않습니다."))
                .value;
    }
}
