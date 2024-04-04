package com.example.mealapp;

import com.example.mealapp.MealController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MealSearchApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the first scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("search-view.fxml"));
        Parent root = loader.load();

        // Accessing the controller
        MealController controller = loader.getController();
        // Setting the primary stage in the controller
        controller.setPrimaryStage(primaryStage);
        //Set application title
        primaryStage.setTitle("Meal Application");
        //Set application icon
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("images/diet.png")));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) { //main method to run the application

        launch(args);
    }
}
