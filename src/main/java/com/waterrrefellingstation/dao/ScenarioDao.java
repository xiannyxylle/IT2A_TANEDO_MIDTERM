package com.waterrrfellingstation.dao;

import com.waterrrfellingstation.model.ScenarioEntity;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScenarioDao {
    private static final String DB_URL = "jdbc:sqlite:scenario.db";

    public ScenarioDao() {
        createTableIfNotExists();
    }

    public void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS orders (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "customer_name TEXT NOT NULL, " +
                "qty_slim INTEGER NOT NULL, " +
                "qty_round INTEGER NOT NULL, " +
                "status TEXT NOT NULL" +
                ");";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    public boolean addOrder(String customerName, int qtySlim, int qtyRound, String status) {
        String sql = "INSERT INTO orders (customer_name, qty_slim, qty_round, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customerName);
            pstmt.setInt(2, qtySlim);
            pstmt.setInt(3, qtyRound);
            pstmt.setString(4, status);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error inserting record: " + e.getMessage());
            return false;
        }
    }

    public List<ScenarioEntity> getAllOrders() {
        List<ScenarioEntity> orders = new ArrayList<>();
        String sql = "SELECT id, customer_name, qty_slim, qty_round, status FROM orders";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                orders.add(new ScenarioEntity(
                        rs.getInt("id"),
                        rs.getString("customer_name"),
                        rs.getInt("qty_slim"),
                        rs.getInt("qty_round"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching orders: " + e.getMessage());
        }

        return orders;
    }
}