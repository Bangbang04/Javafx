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
import javafx.collections.FXCollections;

public class RegisterView {
    private Stage primaryStage;
    private UserOperations userOperations;

    public RegisterView(Stage primaryStage) {
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

        Label titleLabel = new Label("Daftar");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Nama Pengguna");
        usernameField.setMaxWidth(300);
        usernameField.setStyle("-fx-padding: 10px;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Kata Sandi");
        passwordField.setMaxWidth(300);
        passwordField.setStyle("-fx-padding: 10px;");

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Konfirmasi Kata Sandi");
        confirmPasswordField.setMaxWidth(300);
        confirmPasswordField.setStyle("-fx-padding: 10px;");

        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.setItems(FXCollections.observableArrayList("Pengguna", "Admin"));
        roleComboBox.setValue("Pengguna");
        roleComboBox.setMaxWidth(300);
        roleComboBox.setStyle("-fx-padding: 10px;");

        Button registerButton = new Button("Daftar");
        registerButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-padding: 10px 20px;");
        registerButton.setMaxWidth(300);

        Hyperlink loginLink = new Hyperlink("Sudah punya akun? Masuk di sini");

        registerButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            String role = roleComboBox.getValue();

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                showError("Semua field harus diisi");
                return;
            }

            if (!password.equals(confirmPassword)) {
                showError("Kata sandi tidak cocok");
                return;
            }

            if (userOperations.registerUser(username, password, role)) {
                showSuccess("Pendaftaran berhasil! Silakan masuk");
                LoginView loginView = new LoginView(primaryStage);
                primaryStage.setScene(new Scene(loginView.getView(), 800, 600));
            } else {
                showError("Nama pengguna sudah digunakan");
            }
        });

        loginLink.setOnAction(e -> {
            LoginView loginView = new LoginView(primaryStage);
            primaryStage.setScene(new Scene(loginView.getView(), 800, 600));
        });

        root.getChildren().addAll(
            titleLabel,
            usernameField,
            passwordField,
            confirmPasswordField,
            roleComboBox,
            registerButton,
            loginLink
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

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sukses");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}