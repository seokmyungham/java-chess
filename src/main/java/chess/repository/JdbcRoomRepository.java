package chess.repository;

import chess.domain.game.Room;
import chess.domain.game.RoomName;
import chess.domain.piece.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcRoomRepository implements RoomRepository{

    private final DBConnection dbConnection = new DBConnection();

    @Override
    public List<String> findAllRoomNames() {
        List<String> allRoomNames = new ArrayList<>();
        String query = "SELECT name FROM room";
        processQuery(query, preparedStatement -> {
            ResultSet rs = preparedStatement.executeQuery();
            addRoomNames(allRoomNames, rs);
        });
        return allRoomNames;
    }

    private void addRoomNames(List<String> allRoomNames, ResultSet rs) throws SQLException {
        while (rs.next()) {
            allRoomNames.add(rs.getString("name"));
        }
    }

    @Override
    public boolean isExistsRoomName(RoomName roomName) {
        List<Boolean> existsRoom = new ArrayList<>();
        String query = "SELECT EXISTS (SELECT 1 FROM room WHERE name = ?) AS exists_room";
        processQuery(query, preparedStatement -> {
            preparedStatement.setString(1, roomName.getValue());
            ResultSet rs = preparedStatement.executeQuery();
            addExistsRoomResult(existsRoom, rs);
        });
        return existsRoom.get(0);
    }

    private void addExistsRoomResult(List<Boolean> existsRoom, ResultSet rs) throws SQLException {
        if (rs.next()) {
            existsRoom.add(rs.getBoolean("exists_room"));
        }
    }

    @Override
    public void saveRoom(RoomName roomName) {
        String query = "INSERT INTO room(name, turn) VALUES(?, ?)";
        processQuery(query, preparedStatement -> {
            preparedStatement.setString(1, roomName.getValue());
            preparedStatement.setString(2, Color.WHITE.name());
            preparedStatement.executeUpdate();
        });
    }

    @Override
    public void updateRoomTurn(Color color, Long roomId) {
        String query = "UPDATE room SET turn = ? WHERE id = ?";
        processQuery(query, preparedStatement -> {
            preparedStatement.setString(1, color.name());
            preparedStatement.setLong(2, roomId);
            preparedStatement.executeUpdate();
        });
    }

    @Override
    public Room findRoomByName(RoomName roomName) {
        List<Room> roomId = new ArrayList<>();
        String query = "SELECT * FROM room WHERE name = ? LIMIT 1";
        processQuery(query, preparedStatement -> {
            preparedStatement.setString(1, roomName.getValue());
            ResultSet rs = preparedStatement.executeQuery();
            addRoom(roomId, rs);
        });
        return roomId.get(0);
    }

    private void addRoom(List<Room> roomId, ResultSet rs) throws SQLException {
        if (rs.next()) {
            roomId.add(new Room(rs.getLong("id"), rs.getString("name")));
        }
    }

    @Override
    public Color findTurnById(Long roomId) {
        List<Color> color = new ArrayList<>();
        String query = "SELECT turn FROM room WHERE id = ?";
        processQuery(query, preparedStatement -> {
            preparedStatement.setLong(1, roomId);
            ResultSet rs = preparedStatement.executeQuery();
            addColor(color, rs);
        });
        return color.get(0);
    }

    private void addColor(List<Color> color, ResultSet rs) throws SQLException {
        if (rs.next()) {
            color.add(Color.valueOf(rs.getString("turn")));
        }
    }

    private void processQuery(String query, QueryProcessor queryProcessor) {
        try (Connection connection = dbConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            queryProcessor.process(preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
