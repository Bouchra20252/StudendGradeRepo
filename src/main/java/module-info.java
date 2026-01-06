module org.example.studentgradesystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.studentgradesystem to javafx.fxml;
    exports org.example.studentgradesystem;
}