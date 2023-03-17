module com.example.projectnetwork {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.projectnetwork to javafx.fxml;
    exports com.example.projectnetwork;
}