/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.todo;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.SQLException;

public class LoginView {
    private Stage primaryStage;
    private UserOperations userOperations;

    public LoginView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        try {
            userOperations = new UserOperations();
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Gagal terhubung ke database");
        }
    }

    public VBox getView() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: white;");

        Label titleLabel = new Label("Masuk");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Nama Pengguna");
        usernameField.setMaxWidth(300);
        usernameField.setStyle("-fx-padding: 10px;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Kata Sandi");
        passwordField.setMaxWidth(300);
        passwordField.setStyle("-fx-padding: 10px;");

        Button loginButton = new Button("Masuk");
        loginButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-padding: 10px 20px;");
        loginButton.setMaxWidth(300);

        Hyperlink registerLink = new Hyperlink("Belum punya akun? Daftar di sini");
        
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            User user = userOperations.loginUser(username, password);
            if (user != null) {
                DashboardView dashboardView = new DashboardView(primaryStage, user);
                Scene dashboardScene = new Scene(dashboardView.getView(), 800, 600);
                primaryStage.setScene(dashboardScene);
            } else {
                showError("Nama pengguna atau kata sandi salah");
            }
        });

        registerLink.setOnAction(e -> {
            RegisterView registerView = new RegisterView(primaryStage);
            Scene registerScene = new Scene(registerView.getView(), 800, 600);
            primaryStage.setScene(registerScene);
        });

        root.getChildren().addAll(
            titleLabel,
            usernameField,
            passwordField,
            loginButton,
            registerLink
        );

        return root;
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}