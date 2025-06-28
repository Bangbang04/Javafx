/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.todo;



import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.chart.*;
import java.sql.SQLException;
import com.mycompany.todo.TodoStats;

public class DashboardView {
    private Stage primaryStage;
    private User currentUser;
    private TodoOperations todoOperations;

    public DashboardView(Stage primaryStage, User user) {
        this.primaryStage = primaryStage;
        this.currentUser = user;
        try {
            todoOperations = new TodoOperations();
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Gagal terhubung ke database");
        }
    }

    public BorderPane getView() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // Menu Sidebar
        VBox sidebar = createSidebar();
        root.setLeft(sidebar);

        // Konten Utama
        VBox mainContent = createMainContent();
        root.setCenter(mainContent);

        return root;
    }

    private VBox createSidebar() {
        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(10));
        sidebar.setStyle("-fx-background-color: #f8f9fa; -fx-min-width: 200px;");

        Label welcomeLabel = new Label("Selamat datang,\n" + currentUser.getUsername());
        welcomeLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Button dashboardButton = new Button("Dashboard");
        dashboardButton.setMaxWidth(Double.MAX_VALUE);
        
        Button todoButton = new Button("Daftar Todo");
        todoButton.setMaxWidth(Double.MAX_VALUE);
        
        Button logoutButton = new Button("Keluar");
        logoutButton.setMaxWidth(Double.MAX_VALUE);

        todoButton.setOnAction(e -> {
            TodoView todoView = new TodoView(primaryStage, currentUser);
            primaryStage.setScene(new Scene(todoView.getView(), 800, 600));
        });

        logoutButton.setOnAction(e -> {
            LoginView loginView = new LoginView(primaryStage);
            primaryStage.setScene(new Scene(loginView.getView(), 800, 600));
        });

        sidebar.getChildren().addAll(welcomeLabel, dashboardButton, todoButton, logoutButton);
        return sidebar;
    }

    private VBox createMainContent() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(10));

        // Statistik
        TodoStats stats = todoOperations.getStatistik(currentUser.getId());
        
        GridPane statsGrid = new GridPane();
        statsGrid.setHgap(20);
        statsGrid.setVgap(10);
        statsGrid.setPadding(new Insets(10));
        statsGrid.setStyle("-fx-background-color: white; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");

        addStatCard(statsGrid, "Total Todo", String.valueOf(stats.getTotal()), 0, 0);
        addStatCard(statsGrid, "Selesai", String.valueOf(stats.getSelesai()), 1, 0);
        addStatCard(statsGrid, "Belum Selesai", String.valueOf(stats.getBelumSelesai()), 2, 0);

        // Grafik
        PieChart pieChart = createPieChart(stats);
        
        content.getChildren().addAll(
            new Label("Statistik Todo"),
            statsGrid,
            new Label("Grafik Status Todo"),
            pieChart
        );

        return content;
    }

    private void addStatCard(GridPane grid, String label, String value, int col, int row) {
        VBox card = new VBox(5);
        card.setPadding(new Insets(10));
        card.setStyle("-fx-background-color: #ffffff; -fx-border-color: #e0e0e0; -fx-border-radius: 5;");
        
        Label valueLabel = new Label(value);
        valueLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        Label titleLabel = new Label(label);
        titleLabel.setStyle("-fx-font-size: 14px;");
        
        card.getChildren().addAll(valueLabel, titleLabel);
        grid.add(card, col, row);
    }

    private PieChart createPieChart(TodoStats stats) {
        PieChart.Data selesaiData = new PieChart.Data("Selesai", stats.getSelesai());
        PieChart.Data belumSelesaiData = new PieChart.Data("Belum Selesai", stats.getBelumSelesai());
        
        PieChart chart = new PieChart();
        chart.getData().addAll(selesaiData, belumSelesaiData);
        chart.setTitle("Status Todo");
        
        return chart;
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}