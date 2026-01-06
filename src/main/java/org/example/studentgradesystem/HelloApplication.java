package org.example.studentgradesystem;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.sql.*;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {
        DatabaseHandler.initializeDatabase(); // Create DB on startup
        showLoginView(stage);
    }

    private void showLoginView(Stage stage) {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #2c3e50;");

        Label title = new Label("GRADE MANAGEMENT SYSTEM");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 26px; -fx-font-weight: bold;");

        Button teacherBtn = createStyledButton("I am a Teacher", "#3498db");
        Button studentBtn = createStyledButton("I am a Student", "#e67e22");

        teacherBtn.setOnAction(e -> showTeacherView(stage));
        studentBtn.setOnAction(e -> showStudentView(stage));

        root.getChildren().addAll(title, teacherBtn, studentBtn);
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    private void showTeacherView(Stage stage) {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-background-color: #f4f7f6;");

        Label header = new Label("TEACHER CONTROL PANEL");
        header.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // 1. Input Section
        HBox inputArea = new HBox(10);
        inputArea.setAlignment(Pos.CENTER);
        TextField nameIn = new TextField(); nameIn.setPromptText("Student Name");
        TextField subIn = new TextField(); subIn.setPromptText("Subject");
        TextField markIn = new TextField(); markIn.setPromptText("Grade");
        Button saveBtn = new Button("Add New");
        saveBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");
        inputArea.getChildren().addAll(nameIn, subIn, markIn, saveBtn);

        // 2. The Table
        TableView<Grade> table = new TableView<>();
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                nameIn.setText(newSelection.getStudentName());
                subIn.setText(newSelection.getSubject());
                markIn.setText(String.valueOf(newSelection.getMark()));
            }
        });
        TableColumn<Grade, String> colName = new TableColumn<>("Student");
        colName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        TableColumn<Grade, String> colSub = new TableColumn<>("Subject");
        colSub.setCellValueFactory(new PropertyValueFactory<>("subject"));
        TableColumn<Grade, Double> colMark = new TableColumn<>("Grade");
        colMark.setCellValueFactory(new PropertyValueFactory<>("mark"));
        table.getColumns().addAll(colName, colSub, colMark);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // 3. Edit and Delete Buttons
        HBox actionButtons = new HBox(15);
        actionButtons.setAlignment(Pos.CENTER);
        Button editBtn = new Button("Update Selected");
        editBtn.setStyle("-fx-background-color: #f1c40f; -fx-text-fill: black; -fx-font-weight: bold;");
        Button deleteBtn = new Button("Delete Selected");
        deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        actionButtons.getChildren().addAll(editBtn, deleteBtn);

        // --- LOGIC: ADD ---
        saveBtn.setOnAction(e -> {
            if (nameIn.getText().isEmpty() || subIn.getText().isEmpty() || markIn.getText().isEmpty()) return;
            executeSQL("INSERT INTO grades (student_name, subject, mark) VALUES (?, ?, ?)",
                    nameIn.getText(), subIn.getText(), markIn.getText(), null);
            nameIn.clear(); subIn.clear(); markIn.clear();
            refreshTeacherTable(table);
        });

        // --- LOGIC: DELETE ---
        deleteBtn.setOnAction(e -> {
            Grade selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                executeSQL("DELETE FROM grades WHERE id = ?", null, null, null, selected.getId());
                refreshTeacherTable(table);
            }
        });

        // --- LOGIC: UPDATE ---
        editBtn.setOnAction(e -> {
            Grade selected = table.getSelectionModel().getSelectedItem();
            if (selected != null && !nameIn.getText().isEmpty()) {
                executeSQL("UPDATE grades SET student_name = ?, subject = ?, mark = ? WHERE id = ?",
                        nameIn.getText(), subIn.getText(), markIn.getText(), selected.getId());
                refreshTeacherTable(table);
            }
        });

        Button backBtn = new Button("← Logout");
        backBtn.setOnAction(e -> showLoginView(stage));

        layout.getChildren().addAll(header, inputArea, actionButtons, table, backBtn);
        VBox.setVgrow(table, Priority.ALWAYS);
        refreshTeacherTable(table);
        stage.getScene().setRoot(layout);
    }

    // Helper method to handle all SQL Updates (Add, Edit, Delete)
    private void executeSQL(String sql, String name, String sub, String mark, Integer id) {
        try (Connection conn = DatabaseHandler.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (sql.startsWith("INSERT")) {
                pstmt.setString(1, name); pstmt.setString(2, sub); pstmt.setDouble(3, Double.parseDouble(mark));
            } else if (sql.startsWith("DELETE")) {
                pstmt.setInt(1, id);
            } else if (sql.startsWith("UPDATE")) {
                pstmt.setString(1, name); pstmt.setString(2, sub); pstmt.setDouble(3, Double.parseDouble(mark)); pstmt.setInt(4, id);
            }
            pstmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void refreshTeacherTable(TableView<Grade> table) {
        table.getItems().clear();
        try (Connection conn = DatabaseHandler.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM grades")) {
            while (rs.next()) {
                table.getItems().add(new Grade(rs.getInt("id"), rs.getString("student_name"), rs.getString("subject"), rs.getDouble("mark")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void showStudentView(Stage stage) {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-background-color: #2c3e50;");

        Label header = new Label("STUDENT GRADE PORTAL");
        header.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: white;");

        HBox searchArea = new HBox(10);
        searchArea.setAlignment(Pos.CENTER);
        TextField nameSearch = new TextField();
        nameSearch.setPromptText("Enter your Full Name...");
        nameSearch.setPrefWidth(300);
        Button searchBtn = new Button("View My Marks");
        searchBtn.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white; -fx-font-weight: bold;");
        searchArea.getChildren().addAll(nameSearch, searchBtn);

        TableView<Grade> table = new TableView<>();
        TableColumn<Grade, String> colSub = new TableColumn<>("Subject");
        colSub.setCellValueFactory(new PropertyValueFactory<>("subject"));
        TableColumn<Grade, Double> colMark = new TableColumn<>("Mark");
        colMark.setCellValueFactory(new PropertyValueFactory<>("mark"));
        table.getColumns().addAll(colSub, colMark);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        searchBtn.setOnAction(e -> {
            table.getItems().clear();
            try (Connection conn = DatabaseHandler.connect();
                 PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM grades WHERE student_name = ?")) {
                pstmt.setString(1, nameSearch.getText().trim());
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    table.getItems().add(new Grade(rs.getInt("id"), rs.getString("student_name"), rs.getString("subject"), rs.getDouble("mark")));
                }
            } catch (SQLException ex) { ex.printStackTrace(); }
        });

        Button backBtn = new Button("← Back to Role Selection");
        backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #bdc3c7; -fx-underline: true;");
        backBtn.setOnAction(e -> showLoginView(stage));

        layout.getChildren().addAll(header, searchArea, table, backBtn);
        VBox.setVgrow(table, Priority.ALWAYS);
        stage.getScene().setRoot(layout);
    }

    private Button createStyledButton(String text, String color) {
        Button b = new Button(text);
        b.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10 20;");
        b.setPrefWidth(250);
        return b;
    }

    public static void main(String[] args) { launch(); }
}