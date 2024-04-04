module com.example.mealapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.net.http;


    opens com.example.mealapp to javafx.fxml;
    exports com.example.mealapp;
}