
package com.example.mealapp;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class MealDetailsController { // create a class for control the second scene (meal detail)

//create a variable and set the setter
    private Stage primaryStage;
    private MealController mealController;
    public void setPrimaryStage(Stage primaryStage) {

        this.primaryStage = primaryStage;
    }
    public void setMealController(MealController mealController) {

        this.mealController = mealController;
    }
//create fxml variable
    @FXML
    private Label mealNameLabel; //for meal name

    @FXML
    private ImageView mealImageView; //for image
    @FXML
    private Label areaLabel; // for area
    @FXML
    private Label categoryLabel; // for category

    @FXML
    private TextArea instructionsLabel; //for instruction area

    @FXML
    private ListView<String> ingredientListView; // for list ingredient

    @FXML
    private void goBack(ActionEvent event) { //goback method for going back to first scene.
        // Set the new scene to the primary stage
        primaryStage.setScene(mealController.getPreviousScene());
    }


    public void setMeal(Meal selectedMeal) { // set meal method
        mealNameLabel.setText(selectedMeal.getStrMeal()+" "+"\uD83C\uDF72"); // Set meal name

        // Set image source for the ImageView
        String imageUrl = selectedMeal.getStrMealThumb();
        Image image = new Image(imageUrl);
        mealImageView.setImage(image);

        // Set area label
        String area = selectedMeal.getStrArea();
        if (area != null && !area.trim().isEmpty()) { //set condition in case cannot find the area
            areaLabel.setText("Area: " + area);
        } else {
            areaLabel.setText("Area: Not specified");
        }
        // Set category label
        String category = selectedMeal.getStrCategory();//set condition in case cannot find the category
        if (category != null && !category.trim().isEmpty()) {
            categoryLabel.setText("Category: " + category);
        } else {
            categoryLabel.setText("Category: Not specified");
        }
        // Set instructions label
        String instructions = selectedMeal.getStrInstructions();//set condition in case cannot find amy instruction
        if (instructions != null && !instructions.trim().isEmpty()) {
            instructionsLabel.setText("Instructions: " +"\n"+ instructions);
        } else {
            instructionsLabel.setText("Instructions: Not available");
        }
      // Display ingredients in the ingredientListView
        ingredientListView.getItems().clear();
        ingredientListView.getItems().addAll(selectedMeal.getIngredientsWithMeasure());
    }

}
