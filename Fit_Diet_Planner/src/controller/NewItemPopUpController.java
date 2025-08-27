package controller;

import application.AppContext;
import javafx.collections.FXCollections;
import application.Main;
import application.Utils;
import controller.LibraryController.FilterType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import object.Food;
import object.Food.FoodCategory;
import object.LibraryItem.LibraryType;
import object.Sport;

public class NewItemPopUpController {

	private Stage popUpStage;
	
	@FXML
	private RadioButton foodRadioBtn;

	@FXML
	private RadioButton sportRadioBtn;
	
	@FXML
	private ToggleGroup itemType;

	@FXML
	private VBox foodPane;

	@FXML
	private TextField foodNameInput;

	@FXML
	private ChoiceBox<FoodCategory> foodCategoryBox;

	@FXML
	private TextField foodCalorieInput;

	@FXML
	private TextField foodGramInput;

	@FXML
	private VBox sportPane;

	@FXML
	private TextField sportNameInput;

	@FXML
	private TextField sportMetInput;

	@FXML
	private CheckBox repBasedCheckBox;
	
	@FXML
	private Label secPerRepHintLabel;

	@FXML
	private TextField sportSecondsInput;
	
	@FXML
	private void initialize() {
		Utils.setDoubleField(foodCalorieInput);
		Utils.setDoubleField(foodGramInput);
		Utils.setDoubleField(sportMetInput);
		Utils.setDoubleField(sportSecondsInput);
		
        foodCategoryBox.setItems(FXCollections.observableArrayList(FoodCategory.values()));

        foodCategoryBox.setValue(FoodCategory.PRODUCE);
		
		itemType.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
		    if (newToggle != null) {
		        updateItemPane();
		    }
		});
		
	    repBasedCheckBox.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
	        updateRepBasedFields();
	    });
	    
	    foodRadioBtn.setSelected(true);
	    updateItemPane();
	    updateRepBasedFields();
	}
	
	private void updateRepBasedFields() {
	    boolean repBased = repBasedCheckBox.isSelected();
	    sportSecondsInput.setVisible(repBased);
	    secPerRepHintLabel.setVisible(repBased);
	}
	
	private void updateItemPane() {
		boolean isAddFood = foodRadioBtn.isSelected();
		foodPane.setVisible(isAddFood);
        sportPane.setVisible(!isAddFood);
	}
	
	public void setStage(Stage stage) {
		this.popUpStage = stage;
	}
	
    @FXML
    private void create() {
    	if(foodRadioBtn.isSelected()) {
    		Food newFood = new Food(foodNameInput.getText(), LibraryType.CUSTOM, foodCategoryBox.getValue(), Utils.parseDoubleOrZero(foodCalorieInput.getText())/100, Utils.parseDoubleOrZero(foodGramInput.getText()));
            try {
            	AppContext.getLibrary().addNewFood(newFood);
            	closePopUp();
            } catch (Exception  e) {
                Main.errorMessage("Invalid Food", "Could not add food", e.getMessage());
            }
    		 		
    	}else if(sportRadioBtn.isSelected()) {
    		Sport newSport;
    		if(repBasedCheckBox.isSelected())
    			newSport = new Sport(sportNameInput.getText(), LibraryType.CUSTOM, Utils.parseDoubleOrZero(sportMetInput.getText()), Utils.parseDoubleOrZero(sportSecondsInput.getText()));
    		else
    			newSport = new Sport(sportNameInput.getText(), LibraryType.CUSTOM, Utils.parseDoubleOrZero(sportMetInput.getText()));
    		
            try {
            	AppContext.getLibrary().addNewSport(newSport);
            	closePopUp();
            } catch (Exception  e) {
                Main.errorMessage("Invalid Sport", "Could not add sport", e.getMessage());
            }
            
    	}
    }
	
    @FXML
    private void cancel() {
        if (Main.confirmationAction("Cancel", "Are you sure you want to close?", "")) {
        	closePopUp();
        }
    }
    
    private void closePopUp() {
	   if (popUpStage != null) {
       	popUpStage.close();
       } 	
    }
	
	public void resetField(FilterType Filter) {
		if(Filter == FilterType.FOOD)
			foodRadioBtn.setSelected(true);
		else
			sportRadioBtn.setSelected(true);
	    updateItemPane();
	    repBasedCheckBox.setSelected(false);
	    updateRepBasedFields();    
	    foodNameInput.clear();
	    foodCategoryBox.setValue(FoodCategory.PRODUCE);
	    foodCalorieInput.clear();
	    foodGramInput.clear();
	    sportNameInput.clear();
	    sportMetInput.clear();
	    sportSecondsInput.clear();	
	}
	
}
