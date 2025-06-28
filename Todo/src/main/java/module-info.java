module com.mycompany.todo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.mycompany.todo to javafx.fxml;
    exports com.mycompany.todo;
}
