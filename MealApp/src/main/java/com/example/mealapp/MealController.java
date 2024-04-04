package com.example.mealapp;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

// This class serves as the controller for the main view (FXML file)
public class MealController implements Initializable {

    @FXML
    private TextField searchField; //set the search field

    @FXML
    private ListView<Meal> mealList;//set the list of meal

    private ObservableList<Meal> meals;// ObservableList to hold meal data for the ListView
    private Stage primaryStage; // Reference to the primary stage
    // Set the primary stage reference
    public void setPrimaryStage(Stage primaryStage) {

        this.primaryStage = primaryStage;
    }
    // Set the previous scene reference
    private Scene previousScene; // Reference to the previous scene
    //Get the previous scene reference
    public Scene getPreviousScene() {

        return previousScene;
    }
    private final String MealUrl = "https://www.themealdb.com/api/json/v1/1/search.php?s=";//set the api variable
    private final Gson gson = new Gson();// Gson instance for JSON parsing

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        meals = FXCollections.observableArrayList();
        mealList.setItems(meals);
        // Listener for item selection in the ListView
        mealList.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        showMealDetails(newValue);
                    }
                }
        );
    }
    private void showMealDetails(Meal selectedMeal) {// Method to display meal details
        try {
            // Load the meal details scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("meal_details.fxml"));
            Parent root = loader.load();

            // Get the controller
            MealDetailsController detailsController = loader.getController();

            // Pass the primary stage reference to the MealDetailsController
            detailsController.setPrimaryStage(primaryStage);

            // Pass the MealController reference to the MealDetailsController
            detailsController.setMealController(this);

            // Set the selected meal
            detailsController.setMeal(selectedMeal);
            // Set the previous scene if not already set
            if (previousScene == null) {
                previousScene = primaryStage.getScene();
            }

            // Replace the content of the primary stage with the meal details view
            primaryStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading meal details scene: " + e.getMessage());
        }
    }

    public void onSearchClicked() {   // Method to handle search button click
        String searchTerm = searchField.getText();
        if (!searchTerm.isEmpty()) {
            // Make API call and fetch meals
            fetchMeals(searchTerm);
        } else {
            displayErrorAlert("Please enter a food name to search.");
        }
    }


    private void fetchMeals(String searchTerm) {// Method to fetch meals from The Meal API
        // Show ListView before making the API call
        mealList.setVisible(true);
        String url;
        if (searchTerm.length() == 1) {
            searchTerm = searchTerm.replaceAll(" ", "%20");
            // If searchTerm is a single character, search by first letter
            url = "https://www.themealdb.com/api/json/v1/1/search.php?f=" + searchTerm;
        } else {
            // Otherwise, search by meal name
            url = MealUrl + searchTerm;
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                parseMealsJson(response.body());
            } else {
                // Handle API errors
                displayErrorAlert("API error: Status code " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            // Handle network or connection errors
            displayErrorAlert("Error fetching meals: " + e.getMessage());
        }
    }


private void parseMealsJson(String jsonString) {// Method to parse JSON response from The Meal API
    try {
        // Parse the JSON string into a Map object
        Map<String, Object> responseMap = gson.fromJson(jsonString, Map.class);
        // Extract the "meals" list from the Map
        List<Map<String, Object>> mealListFromMap = (List) responseMap.get("meals");
        meals.clear();
        if (mealListFromMap != null) {
            for (Map<String, Object> mealMap : mealListFromMap) {
                Meal meal = new Meal();
                // Add setters for meal information fields
                meal.setIdMeal((String) mealMap.get("idMeal"));
                meal.setStrMeal((String) mealMap.get("strMeal"));
                meal.setStrMealThumb((String) mealMap.get("strMealThumb"));
                meal.setStrArea((String) mealMap.get("strArea"));
                meal.setStrCategory((String) mealMap.get("strCategory"));
                meal.setStrInstructions((String) mealMap.get("strInstructions"));

                // Populate ingredients and measures
                Map<String, String> ingredients = new LinkedHashMap<>();
                List<String> measures = new ArrayList<>();
                for (int i = 1; i <= 20; i++) { //create loop to run all ingredient along with measure
                    String ingredient = (String) mealMap.get("strIngredient" + i);
                    String measure = (String) mealMap.get("strMeasure" + i);
                    if (ingredient != null && !ingredient.isEmpty()) {//set condition to prevent null value be displayed in the app
                        ingredients.put(ingredient, measure);
                        measures.add(measure);
                    }
                }
                meal.setIngredients(ingredients);
                meal.setMeasures(measures);

                meals.add(meal);
            }
        } else {
            // Handle case where no meals are found for the search term
            displayErrorAlert("No meals found for the search term: " + searchField.getText());
        }
    } catch (JsonSyntaxException e) {
        // Handle JSON parsing errors
        displayErrorAlert("Error parsing JSON: " + e.getMessage());
    } catch (Exception e) {
        // Handle other exceptions
        displayErrorAlert("Error processing meals: " + e.getMessage());
        e.printStackTrace();
    }
}

    private void displayErrorAlert(String message) {// Method to alert an error when the data cannot be found
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Search Error");
        alert.setContentText(message);
        alert.showAndWait();
    }



}

