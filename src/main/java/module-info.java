module com.example.mazefx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.mazefx to javafx.fxml;
    exports com.example.mazefx;
}