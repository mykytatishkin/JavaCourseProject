module com.example.javacourseproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.javacourseproject to javafx.fxml;
    exports com.example.javacourseproject;
}