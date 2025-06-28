
package com.mycompany.todo;



import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SecondaryView {
    private Stage stage;
    private BorderPane root;

    public SecondaryView(Stage stage) {
        this.stage = stage;
        this.root = new BorderPane();
        // konstruksi UI di sini (atau load FXML)
    }

    public BorderPane getView() {
        return root;
    }
}
