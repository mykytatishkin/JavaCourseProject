module com.example.marketcourseprojectfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.marketcourseprojectfx to javafx.fxml;
    exports com.example.marketcourseprojectfx;
    exports com.example.marketcourseprojectfx.Controller;
    opens com.example.marketcourseprojectfx.Controller to javafx.fxml;
}