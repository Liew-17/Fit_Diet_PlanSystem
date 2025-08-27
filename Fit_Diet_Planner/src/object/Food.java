package object;
import java.util.UUID;

import application.Utils;

/**
 * Represents a food item in the library system. 
 * <p>
 * A {@code Food} is a specific type of {@link LibraryItem} that stores 
 * nutritional information such as calories per gram, grams per serving, 
 * and category (e.g., Produce, Grain, Protein, Dairy).
 * </p>
 */
public class Food extends LibraryItem {
	private FoodCategory category;
	private double caloriePerG;
	private double gramPerServing;
		
    /**
     * Enumeration of food categories with user-friendly display names.
     */
	public enum FoodCategory {
	    PRODUCE("Produce"),
	    GRAIN("Grain"),      
	    PROTEIN("Protein"),   
	    DAIRY("Dairy"),      
	    OTHER("Other");      

	    private final String displayName;

	    FoodCategory(String displayName) {
	        this.displayName = displayName;
	    }

	    @Override
	    public String toString() {
	        return displayName;
	    }
	}
	
    /**
     * Constructs a new {@code Food} with a specific ID.
     *
     * @param id the unique identifier of the food item
     * @param name the name of the food
     * @param libraryType the type of library item
     * @param category the food category
     * @param caloriePerG calories per gram of the food
     * @param gramPerServing grams per serving size
     */
	public Food(String id, String name, LibraryType libraryType, FoodCategory category, double caloriePerG, double gramPerServing) {
		super(id, name, libraryType);
		this.category = category;
		this.caloriePerG = caloriePerG;
		this.gramPerServing = gramPerServing;
	}
	
    /**
     * Constructs a new {@code Food} without a predefined ID.
     *
     * @param name the name of the food
     * @param libraryType the type of library item
     * @param category the food category
     * @param caloriePerG calories per gram of the food
     * @param gramPerServing grams per serving size
     */
	public Food(String name, LibraryType libraryType, FoodCategory category, double caloriePerG, double gramPerServing) {
		super(name, libraryType);
		this.category = category;
		this.caloriePerG = caloriePerG;
		this.gramPerServing = gramPerServing;
	}

    /**
     * Validates this food object to ensure its values are valid.
     * <p>
     * This includes checking that the name is not empty,
     * and that calories and serving size are greater than zero.
     * </p>
     *
     * @throws IllegalArgumentException if validation fails
     */
	@Override
    public void validate() {
        if (getName() == null || getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Food name cannot be empty.");
        }
        if (caloriePerG <= 0) {
            throw new IllegalArgumentException("Calorie per gram must be greater than 0.");
        }
        if (gramPerServing <= 0) {
            throw new IllegalArgumentException("Gram per serving must be greater than 0.");
        }
    }

    /**
     * Returns the category of this food as a displayable string.
     *
     * @return the display category string
     */
	@Override
	public String getDisplayCategory() {
		return category.toString();
	}

    /**
     * Generates a detailed description of the food item including 
     * serving size, total calories, and typical energy value.
     *
     * @return a formatted string description of the food
     */
	@Override
	public String generateDescription() {
	    double totalCalories = caloriePerG * gramPerServing;

	    return String.format("%s per serving (%.0f g) contains %.0f kcal.%nTypical energy: %s.",
	        Utils.capitalizeSafe(getName()),
	        gramPerServing,
	        totalCalories,
	        displayCaloriesPer100g()
	    );
	}
	
    /**
     * Returns a formatted string of calories per 100 grams.
     *
     * @return a string showing calories per 100g, e.g. "250 kcal per 100g"
     */
	public String displayCaloriesPer100g() {
	    return String.format("%.0f kcal per 100g", caloriePerG * 100);
	}

    /**
     * Returns a formatted string of grams per serving.
     *
     * @return a string showing serving size in grams, e.g. "50 g/serving"
     */
	public String displayGramPerServing() {
	    return String.format("%.0f g/serving", gramPerServing);
	}

	public double getGramPerServing() {
		return gramPerServing;
	}
	
	public FoodCategory getCategory() {
		return category;
	}
	
	public double getCaloriePerG() {		
		return caloriePerG;
	}

}
