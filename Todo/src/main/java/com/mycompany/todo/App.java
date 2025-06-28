package com.mycompany.todo;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Aplikasi Todo");
        
        // Mulai dengan tampilan login
        LoginView loginView = new LoginView(primaryStage);
        Scene scene = new Scene(loginView.getView(), 800, 600);
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() {
        // Tutup koneksi database saat aplikasi ditutup
        DatabaseConnection.closeConnection();
    }
}
