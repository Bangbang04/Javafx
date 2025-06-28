
package com.mycompany.todo;



import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PrimaryView {
    private Stage stage;
    private BorderPane root;

    public PrimaryView(Stage stage) {
        this.stage = stage;
        this.root = new BorderPane();
        // konstruksi UI di sini (atau load FXML)
    }

    public BorderPane getView() {
        return root;
    }
}