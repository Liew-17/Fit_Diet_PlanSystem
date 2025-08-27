package object;

import java.util.HashMap;
import java.util.Map;

import application.AppContext;
import object.LibraryItem.LibraryType;

/**
 * Represents a library that stores predefined and user-added {@link Food} and {@link Sport} items.
 * <p>
 * The library is preloaded with a set of default items and also supports 
 * adding new validated foods and sports. Items are stored in maps keyed by their unique IDs.
 * </p>
 */
public class Library {
	private Map<String, Food> foodList;
	private Map<String, Sport> sportList;
	
    /**
     * Creates a new library and initializes it with default food and sport items.
     */
    public Library() { 	
    	foodList = new HashMap<>();
    	sportList = new HashMap<>();
    	intializeItem();
    }   
	
    /**
     * Initializes the library with a set of default {@link Food} and {@link Sport} items.
     * These items are added to the library at startup so that users have
     * some predefined options available before adding their own.
     */
    private void intializeItem() {
    	addNewFood(new Food("FOOD_001", "Apple", LibraryType.DEFAULT, Food.FoodCategory.PRODUCE, 0.52, 150));
    	addNewFood(new Food("FOOD_002", "Banana", LibraryType.DEFAULT, Food.FoodCategory.PRODUCE, 0.96, 120));
    	addNewFood(new Food("FOOD_003", "Orange", LibraryType.DEFAULT, Food.FoodCategory.PRODUCE, 0.47, 130));
    	addNewFood(new Food("FOOD_004", "Rice", LibraryType.DEFAULT, Food.FoodCategory.GRAIN, 1.3, 100));
    	addNewFood(new Food("FOOD_005", "Bread", LibraryType.DEFAULT, Food.FoodCategory.GRAIN, 2.5, 50));
    	addNewFood(new Food("FOOD_006", "Chicken Breast", LibraryType.DEFAULT, Food.FoodCategory.PROTEIN, 1.65, 100));
    	addNewFood(new Food("FOOD_007", "Egg", LibraryType.DEFAULT, Food.FoodCategory.PROTEIN, 1.55, 50));
    	addNewFood(new Food("FOOD_008", "Milk", LibraryType.DEFAULT, Food.FoodCategory.DAIRY, 0.64, 200));
    	addNewFood(new Food("FOOD_009", "Cheese", LibraryType.DEFAULT, Food.FoodCategory.DAIRY, 4.0, 30));
    	addNewFood(new Food("FOOD_010", "Almonds", LibraryType.DEFAULT, Food.FoodCategory.OTHER, 5.7, 30));
    	addNewFood(new Food("FOOD_011", "Chocolate", LibraryType.DEFAULT, Food.FoodCategory.OTHER, 5.3, 20));
    	addNewFood(new Food("FOOD_012", "Yogurt", LibraryType.DEFAULT, Food.FoodCategory.DAIRY, 0.6, 150));

    	addNewSport(new Sport("SPORT_001", "Running", LibraryType.DEFAULT, 9.8));
    	addNewSport(new Sport("SPORT_002", "Walking", LibraryType.DEFAULT, 3.5));
    	addNewSport(new Sport("SPORT_003", "Push-ups", LibraryType.DEFAULT, 8.0, 2)); 
    	addNewSport(new Sport("SPORT_004", "Plank", LibraryType.DEFAULT, 4.0, 60));   
    	addNewSport(new Sport("SPORT_005", "Cycling", LibraryType.DEFAULT, 7.5));
    	addNewSport(new Sport("SPORT_006", "Jump Rope", LibraryType.DEFAULT, 12.3));
    	addNewSport(new Sport("SPORT_007", "Squats", LibraryType.DEFAULT, 7.0, 3));   
    	addNewSport(new Sport("SPORT_008", "Swimming", LibraryType.DEFAULT, 8.0));
    	addNewSport(new Sport("SPORT_009", "Yoga", LibraryType.DEFAULT, 3.0, 60));    
    	addNewSport(new Sport("SPORT_010", "Elliptical", LibraryType.DEFAULT, 5.0));
    	addNewSport(new Sport("SPORT_011", "Bench Press", LibraryType.DEFAULT, 6.0, 3));
    	addNewSport(new Sport("SPORT_012", "Burpees", LibraryType.DEFAULT, 10.0, 4));
        
    }
    
    /**
     * Adds a new food to the library after validation.
     *
     * @param food the food item to add
     * @throws IllegalArgumentException if validation fails
     */
	public void addNewFood(Food food) {	
		food.validate();
		getFoodList().put(food.getId(),food);	
	} 
	
    /**
     * Adds a new sport to the library after validation.
     *
     * @param sport the sport item to add
     * @throws IllegalArgumentException if validation fails
     */
	public void addNewSport(Sport sport) {
		sport.validate();
		getSportList().put(sport.getId(),sport);
	}

	public Map<String, Food> getFoodList() {
		return foodList;
	}

	public Map<String, Sport> getSportList() {
		return sportList;
	}
	
}
