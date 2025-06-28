/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.todo;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.util.Callback;
import java.sql.SQLException;

public class TodoView {
    private Stage primaryStage;
    private User currentUser;
    private TodoOperations todoOperations;
    private TableView<Todo> tableView;
    private ObservableList<Todo> todoList;

    public TodoView(Stage primaryStage, User user) {
        this.primaryStage = primaryStage;
        this.currentUser = user;
        try {
            todoOperations = new TodoOperations();
            todoList = FXCollections.observableArrayList(
                todoOperations.getTodos(user.getId())
            );
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Gagal memuat data todo");
        }
    }

    public BorderPane getView() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // Sidebar
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
        todoButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        
        Button logoutButton = new Button("Keluar");
        logoutButton.setMaxWidth(Double.MAX_VALUE);

        dashboardButton.setOnAction(e -> {
            DashboardView dashboardView = new DashboardView(primaryStage, currentUser);
            primaryStage.setScene(new Scene(dashboardView.getView(), 800, 600));
        });

        logoutButton.setOnAction(e -> {
            LoginView loginView = new LoginView(primaryStage);
            primaryStage.setScene(new Scene(loginView.getView(), 800, 600));
        });

        sidebar.getChildren().addAll(welcomeLabel, dashboardButton, todoButton, logoutButton);
        return sidebar;
    }

    private VBox createMainContent() {
        VBox content = new VBox(10);
        content.setPadding(new Insets(10));

        // Header
        HBox header = new HBox(10);
        header.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        Label titleLabel = new Label("Daftar Todo");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button addButton = new Button("Tambah Todo");
        addButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");
        addButton.setOnAction(e -> showTodoModal(null));

        header.getChildren().addAll(titleLabel, addButton);

        // Tabel
        tableView = createTableView();
        
        content.getChildren().addAll(header, tableView);
        return content;
    }

    private TableView<Todo> createTableView() {
        TableView<Todo> table = new TableView<>();
        table.setItems(todoList);

        // Kolom ID
        TableColumn<Todo, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        idColumn.setPrefWidth(50);

        // Kolom Judul
        TableColumn<Todo, String> judulColumn = new TableColumn<>("Judul");
        judulColumn.setCellValueFactory(cellData -> cellData.getValue().judulProperty());
        judulColumn.setPrefWidth(200);

        // Kolom Deskripsi
        TableColumn<Todo, String> deskripsiColumn = new TableColumn<>("Deskripsi");
        deskripsiColumn.setCellValueFactory(cellData -> cellData.getValue().deskripsiProperty());
        deskripsiColumn.setPrefWidth(300);

        // Kolom Status
        TableColumn<Todo, Boolean> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().selesaiProperty());
        statusColumn.setCellFactory(col -> new TableCell<Todo, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item ? "Selesai" : "Belum Selesai");
                }
            }
        });
        statusColumn.setPrefWidth(100);

        // Kolom Aksi
        TableColumn<Todo, Void> aksiColumn = new TableColumn<>("Aksi");
        aksiColumn.setCellFactory(createActionButtonCellFactory());
        aksiColumn.setPrefWidth(200);

        table.getColumns().addAll(idColumn, judulColumn, deskripsiColumn, statusColumn, aksiColumn);
        return table;
    }

    private Callback<TableColumn<Todo, Void>, TableCell<Todo, Void>> createActionButtonCellFactory() {
        return new Callback<>() {
            @Override
            public TableCell<Todo, Void> call(final TableColumn<Todo, Void> param) {
                return new TableCell<>() {
                    private final Button editBtn = new Button("Ubah");
                    private final Button deleteBtn = new Button("Hapus");
                    private final Button completeBtn = new Button("Selesai");
                    private final HBox pane = new HBox(5);

                    {
                        editBtn.setStyle("-fx-background-color: #ffc107;");
                        deleteBtn.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");
                        completeBtn.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");

                        pane.getChildren().addAll(editBtn, deleteBtn, completeBtn);

                        editBtn.setOnAction(event -> {
                            Todo todo = getTableView().getItems().get(getIndex());
                            showTodoModal(todo);
                        });

                        deleteBtn.setOnAction(event -> {
                            Todo todo = getTableView().getItems().get(getIndex());
                            hapusTodo(todo);
                        });

                        completeBtn.setOnAction(event -> {
                            Todo todo = getTableView().getItems().get(getIndex());
                            tandaiSelesai(todo);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(empty ? null : pane);
                    }
                };
            }
        };
    }

    private void showTodoModal(Todo todo) {
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setTitle(todo == null ? "Tambah Todo" : "Ubah Todo");

        VBox modalContent = new VBox(10);
        modalContent.setPadding(new Insets(20));

        TextField judulField = new TextField();
        judulField.setPromptText("Judul");
        if (todo != null) judulField.setText(todo.getJudul());

        TextArea deskripsiField = new TextArea();
        deskripsiField.setPromptText("Deskripsi");
        if (todo != null) deskripsiField.setText(todo.getDeskripsi());

        Button saveButton = new Button(todo == null ? "Tambah" : "Simpan");
        saveButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");

        saveButton.setOnAction(e -> {
            if (todo == null) {
                // Tambah todo baru
                Todo newTodo = new Todo(0, judulField.getText(), deskripsiField.getText(), 
                    false, null, currentUser.getId());
                if (todoOperations.tambahTodo(newTodo, currentUser.getId())) {
                    refreshTable();
                    modalStage.close();
                    showSuccess("Todo berhasil ditambahkan");
                }
            } else {
                // Update todo
                todo.setJudul(judulField.getText());
                todo.setDeskripsi(deskripsiField.getText());
                if (todoOperations.updateTodo(todo)) {
                    refreshTable();
                    modalStage.close();
                    showSuccess("Todo berhasil diperbarui");
                }
            }
        });

        modalContent.getChildren().addAll(
            new Label("Judul:"),
            judulField,
            new Label("Deskripsi:"),
            deskripsiField,
            saveButton
        );

        Scene modalScene = new Scene(modalContent);
        modalStage.setScene(modalScene);
        modalStage.showAndWait();
    }

    private void hapusTodo(Todo todo) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi");
        alert.setHeaderText("Hapus Todo");
        alert.setContentText("Apakah Anda yakin ingin menghapus todo ini?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                if (todoOperations.hapusTodo(todo.getId(), currentUser.getId())) {
                    refreshTable();
                    showSuccess("Todo berhasil dihapus");
                }
            }
        });
    }

    private void tandaiSelesai(Todo todo) {
        if (todoOperations.tandaiSelesai(todo.getId(), currentUser.getId())) {
            refreshTable();
            showSuccess("Todo berhasil ditandai selesai");
        }
    }

    private void refreshTable() {
        todoList.setAll(todoOperations.getTodos(currentUser.getId()));
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sukses");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}