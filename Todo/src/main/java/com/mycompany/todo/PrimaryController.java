package com.mycompany.todo;



import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PrimaryController {

    private Stage primaryStage;

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    private void switchToSecondary() throws IOException {
        SecondaryView secondaryView = new SecondaryView(primaryStage);
        primaryStage.setScene(new Scene(secondaryView.getView(), 800, 600));
    }
}
