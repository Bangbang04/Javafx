package com.mycompany.todo;





import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SecondaryController {

    private Stage primaryStage;

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    private void switchToPrimary() throws IOException {
        PrimaryView primaryView = new PrimaryView(primaryStage);
        primaryStage.setScene(new Scene(primaryView.getView(), 800, 600));
    }
}