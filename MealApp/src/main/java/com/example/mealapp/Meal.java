package com.example.mealapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Meal { //class for all meal variables
    private String idMeal; // variable for id
    private String strMeal;// variable for meal name
    private String strCategory;// variable for category
    private String strArea;// variable for area

    private String strInstructions;// variable for instruction
    private String strMealThumb;// variable for meal's image
    private Map<String, String> ingredients;// map string for set variable for ingredient
    private List<String> measures; //list of measure


//create all setter and getter for every variable
    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(String idMeal) {
        this.idMeal = idMeal;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public void setStrCategory(String strCategory) {
        this.strCategory = strCategory;
    }

    public String getStrArea() {
        return strArea;
    }

    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }

    public String getStrInstructions() {
        return strInstructions;
    }

    public void setStrInstructions(String strInstructions) {
        this.strInstructions = strInstructions;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }


    public Map<String, String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<String, String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getMeasures() {
        return measures;
    }

    public void setMeasures(List<String> measures) {
        this.measures = measures;
    }

    public List<String> getIngredientsWithMeasure() { //method for combine the ingredient and measure to match each other
        List<String> ingredientsWithMeasure = new ArrayList<>();
        if (ingredients != null && measures != null) {
            // Iterate over the keys of the ingredients map
            for (String ingredient : ingredients.keySet()) {
                // Retrieve the measure corresponding to the ingredient
                String measure = ingredients.get(ingredient);
                // Create a string combining the ingredient and measure
                String ingredientWithMeasure = ingredient + " - " + measure;
                // Add the combined string to the list
                ingredientsWithMeasure.add(ingredientWithMeasure);
            }
        }
        return ingredientsWithMeasure;
    }

    @Override
    public String toString() {
        return strMeal; // strMeal is the name of the meal
    }
}
