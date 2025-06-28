/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.todo;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.mycompany.todo.TodoStats;

public class TodoOperations {
    private Connection connection;

    public TodoOperations() throws SQLException {
        connection = DatabaseConnection.getConnection();
    }

    // Menambah todo baru
    public boolean tambahTodo(Todo todo, int userId) {
        String query = "INSERT INTO todos (judul, deskripsi, user_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, todo.getJudul());
            stmt.setString(2, todo.getDeskripsi());
            stmt.setInt(3, userId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Mengambil semua todo untuk user tertentu
    public List<Todo> getTodos(int userId) {
        List<Todo> todos = new ArrayList<>();
        String query = "SELECT * FROM todos WHERE user_id = ? ORDER BY tanggal_dibuat DESC";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                todos.add(new Todo(
                    rs.getInt("id"),
                    rs.getString("judul"),
                    rs.getString("deskripsi"),
                    rs.getBoolean("selesai"),
                    rs.getString("tanggal_dibuat"),
                    rs.getInt("user_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todos;
    }

    // Mengupdate todo
    public boolean updateTodo(Todo todo) {
        String query = "UPDATE todos SET judul = ?, deskripsi = ? WHERE id = ? AND user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, todo.getJudul());
            stmt.setString(2, todo.getDeskripsi());
            stmt.setInt(3, todo.getId());
            stmt.setInt(4, todo.getUserId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Menghapus todo
    public boolean hapusTodo(int todoId, int userId) {
        String query = "DELETE FROM todos WHERE id = ? AND user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, todoId);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Menandai todo sebagai selesai
    public boolean tandaiSelesai(int todoId, int userId) {
        String query = "UPDATE todos SET selesai = TRUE WHERE id = ? AND user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, todoId);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Mendapatkan statistik todo
    public TodoStats getStatistik(int userId) {
        String query = "SELECT " +
                      "COUNT(*) as total, " +
                      "SUM(CASE WHEN selesai = TRUE THEN 1 ELSE 0 END) as selesai, " +
                      "SUM(CASE WHEN selesai = FALSE THEN 1 ELSE 0 END) as belum_selesai " +
                      "FROM todos WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new TodoStats(
                    rs.getInt("total"),
                    rs.getInt("selesai"),
                    rs.getInt("belum_selesai")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new TodoStats(0, 0, 0);
    }
}