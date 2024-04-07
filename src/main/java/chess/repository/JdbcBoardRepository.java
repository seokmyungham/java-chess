package chess.repository;

import chess.domain.board.position.Position;
import chess.domain.piece.Piece;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcBoardRepository implements BoardRepository {

    private final DBConnection dbConnection = new DBConnection();

    @Override
    public void saveBoard(Map<Position, Piece> board, Long roomId) {
        String query = "INSERT INTO board(`row`, `column`, piece_type, piece_color, room_id) VALUES(?, ?, ?, ?, ?)";
        processQuery(query, preparedStatement -> {
            insertPiecesBatch(board, roomId, preparedStatement);
            preparedStatement.executeBatch();
            preparedStatement.clearBatch();
        });
    }

    private void insertPiecesBatch(Map<Position, Piece> board, Long roomId,
                                   PreparedStatement preparedStatement) throws SQLException {
        for (Map.Entry<Position, Piece> entry : board.entrySet()) {
            Position position = entry.getKey();
            Piece piece = entry.getValue();
            preparedStatement.setInt(1, position.getRowIndex());
            preparedStatement.setString(2, position.getColumn().name());
            preparedStatement.setString(3, piece.getPieceType().name());
            preparedStatement.setString(4, piece.getColor().name());
            preparedStatement.setLong(5, roomId);
            preparedStatement.addBatch();
            preparedStatement.clearParameters();
        }
    }

    @Override
    public void savePiece(Piece piece, Position position, Long roomId) {
        String query = "INSERT INTO board(`row`, `column`, piece_type, piece_color, room_id) VALUES(?, ?, ?, ?, ?)";
        processQuery(query, preparedStatement -> {
            preparedStatement.setInt(1, position.getRowIndex());
            preparedStatement.setString(2, position.getColumn().name());
            preparedStatement.setString(3, piece.getPieceType().name());
            preparedStatement.setString(4, piece.getColor().name());
            preparedStatement.setLong(5, roomId);
            preparedStatement.executeUpdate();
        });
    }

    @Override
    public void deletePieceByPosition(Position position, Long roomId) {
        String query = "DELETE FROM board WHERE `row` = ? AND `column` = ? AND room_id = ?";
        processQuery(query, preparedStatement -> {
            preparedStatement.setInt(1, position.getRowIndex());
            preparedStatement.setString(2, position.getColumn().name());
            preparedStatement.setLong(3, roomId);
            preparedStatement.executeUpdate();
        });
    }

    @Override
    public Piece findPieceByPosition(Position position, Long roomId) {
        List<Piece> pieces = new ArrayList<>();
        String query = "SELECT piece_type, piece_color FROM board WHERE `row` = ? AND `column` = ? AND room_id = ?";
        processQuery(query, preparedStatement -> {
            preparedStatement.setInt(1, position.getRowIndex());
            preparedStatement.setString(2, position.getColumn().name());
            preparedStatement.setLong(3, roomId);
            ResultSet rs = preparedStatement.executeQuery();
            addPiece(pieces, rs);
        });
        return pieces.get(0);
    }

    private void addPiece(List<Piece> pieces, ResultSet rs) throws SQLException {
        if (rs.next()) {
            pieces.add(new Piece(rs.getString("piece_type"), rs.getString("piece_color")));
        }
    }

    @Override
    public Map<Position, Piece> findAllPieceByRoomId(Long roomId) {
        Map<Position, Piece> allPieces = new HashMap<>();
        String query = "SELECT * FROM board JOIN room ON board.room_id = room.id WHERE board.room_id = ?";
        processQuery(query, preparedStatement -> {
            preparedStatement.setLong(1, roomId);
            ResultSet rs = preparedStatement.executeQuery();
            addPositionPieces(allPieces, rs);
        });
        return allPieces;
    }

    private void addPositionPieces(Map<Position, Piece> allPieces, ResultSet rs) throws SQLException {
        while (rs.next()) {
            Position position = new Position(rs.getInt("row"), rs.getString("column"));
            Piece piece = new Piece(rs.getString("piece_type"), rs.getString("piece_color"));
            allPieces.put(position, piece);
        }
    }

    @Override
    public boolean isExistsPiece(Long roomId) {
        List<Boolean> existsPiece = new ArrayList<>();
        String query = "SELECT EXISTS (SELECT 1 FROM board WHERE room_id = ?) AS exists_piece";
        processQuery(query, preparedStatement -> {
            preparedStatement.setLong(1, roomId);
            ResultSet rs = preparedStatement.executeQuery();
            addExistsPieceResult(existsPiece, rs);
        });
        return existsPiece.get(0);
    }

    private void addExistsPieceResult(List<Boolean> existsPiece, ResultSet rs) throws SQLException {
        if (rs.next()) {
            existsPiece.add(rs.getBoolean("exists_piece"));
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
