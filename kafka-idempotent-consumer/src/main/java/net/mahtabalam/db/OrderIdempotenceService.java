package net.mahtabalam.db;

import java.sql.*;

public class OrderIdempotenceService {

    private final String DB_URL = "jdbc:postgresql://localhost:5432/orders";
    private final String DB_USER = "postgres";
    private final String DB_PASSWORD = "postgres";

    public boolean isOrderProcessed(String orderId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM processed_order_messages WHERE order_id = ?")) {
            preparedStatement.setString(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        }
        return false;
    }

    public void markOrderProcessed(String orderId) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO processed_order_messages (order_id) VALUES (?)")) {
            preparedStatement.setString(1, orderId);
            preparedStatement.executeUpdate();
        }
    }
}