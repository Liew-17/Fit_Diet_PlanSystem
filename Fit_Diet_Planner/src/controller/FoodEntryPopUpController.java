package controller;

import application.Main;
import application.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import object.DailyPlan;
import object.Entry;
import object.Entry.EntryType;
import object.Food;
import object.FoodEntry;


public class FoodEntryPopUpController {

    @FXML
    private Label headerLabel;

    @FXML
    private Label foodNameLabel;
    
    @FXML
    private Label foodCategoryLabel;
    
    @FXML
    private Label foodCaloriesLabel;
    
    @FXML
    private Label foodGramPerServingLabel;

    @FXML
    private TextField quantityField;
    
    @FXML
    private TextField gramField;
    
    @FXML
    private Label caloriesPreviewLabel;
    
    @FXML
    private TextArea noteArea;

    @FXML
    private RadioButton plannedRadio;
    
    @FXML
    private RadioButton actualRadio;
    
    @FXML
    private ToggleGroup entryTypeGroup;

    @FXML
    private Button saveBtn; 

    @FXML
    private Button updateBtn;
    
    @FXML
    private Button deleteBtn;
    
    private Stage popUpStage;
    
    private DailyPlan plan;
    
    private FoodEntry entry;
    
    private FoodEntry tempEntry;
    
    private boolean isEdit = false;
    
    @FXML
    public void initialize() {
    	Utils.setIntField(quantityField); 
    	Utils.setDoubleField(gramField);
    	
    	quantityField.textProperty().addListener((obs, oldVal, newVal) -> {
    	    tempEntry.setQuantity(Utils.parseIntOrZero(newVal));
    	    updatePreviewCalorie();
    	});

    	gramField.textProperty().addListener((obs, oldVal, newVal) -> {
    	    tempEntry.setGrams(Utils.parseDoubleOrZero(newVal));
    	    updatePreviewCalorie();
    	});
    	
    	entryTypeGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
    	    if (newToggle != null) {
    	        RadioButton selected = (RadioButton) newToggle;

    	        if (selected == plannedRadio) {
    	            tempEntry.setType(EntryType.PLANNED);
    	        } else if (selected == actualRadio) {
    	            tempEntry.setType(EntryType.RECORDED);
    	        }
    	    }
    	});
    	
    	noteArea.textProperty().addListener((obs, oldText, newText) -> {
    	    tempEntry.setNote(newText);
    	});
    }
    
    public void updatePreviewCalorie() {
    	caloriesPreviewLabel.setText(tempEntry.getCaloriesPreview());	
    }
    
    @FXML
    private void saveEntry() {
        try {     
            plan.addEntry(tempEntry);
            closePopUp();
        } catch (Exception  e) {
            Main.errorMessage("Invalid Entry", "Cannot Save", e.getMessage());
        }

    }

    @FXML
    private void updateEntry() {
        try {
        	entry.updateFrom(tempEntry);
            closePopUp();
        } catch (Exception  e) {
            Main.errorMessage("Invalid Entry", "Cannot Update", e.getMessage());
        }
    }
    
    @FXML
    private void deleteEntry() {
        if (Main.confirmationAction("Delete Entry", "Are you sure you want to delete this entry?", "This action cannot be undone.")) {
            plan.deleteEntry(entry);
            closePopUp();
        }
    }

    @FXML
    private void cancel() {
        if (Main.confirmationAction("Cancel", "Are you sure you want to close?", "Any unsaved changes will be lost.")) {
            closePopUp();
        }
    }
    
    private void closePopUp() {
        if (popUpStage != null) {
        	popUpStage.close();
        }   	
    }
    
    private void updateUI() {
    	if(isEdit) {
    		headerLabel.setText("Edit Food Entry");  
    		saveBtn.setVisible(false);
    		updateBtn.setVisible(true);
    		deleteBtn.setVisible(true);
    		popUpStage.setTitle("Edit Entry");
    	}    			
    	else {
    		headerLabel.setText("New Food Entry");
    		saveBtn.setVisible(true);
    		updateBtn.setVisible(false);
    		deleteBtn.setVisible(false);
    		popUpStage.setTitle("New Entry");
    	}
    	 	
        Food food = tempEntry.getFood();
        foodNameLabel.setText(Utils.capitalizeSafe(food.getName()));
        foodCategoryLabel.setText(food.getDisplayCategory());
        foodCaloriesLabel.setText(food.displayCaloriesPer100g());
        foodGramPerServingLabel.setText(food.displayGramPerServing());
        quantityField.setText(String.valueOf(tempEntry.getQuantity()));
        gramField.setText(String.valueOf(tempEntry.getGram()));
        updatePreviewCalorie();
        
        if(tempEntry.getType()==EntryType.PLANNED || tempEntry.getType()==EntryType.PLANNED_COMPLETE)
        	plannedRadio.setSelected(true);
        else if(tempEntry.getType()==EntryType.RECORDED)
        	actualRadio.setSelected(true);
        
        noteArea.setText(tempEntry.getNote());
    }
    
    public void setStage(Stage popUpStage) {
    	this.popUpStage = popUpStage;
    }
    
    public void setPlan(DailyPlan plan) {
    	this.plan = plan;
    }
    
    public void setEntry(Entry entry) {
        this.entry = (FoodEntry) entry;
        this.tempEntry = this.entry.clone();
        this.isEdit = true;
        updateUI();
    }
    
    public void setEntry(Food food, EntryType prevSelectedType) {
    	FoodEntry newEntry = new FoodEntry(prevSelectedType,"",food,1,food.getGramPerServing());
        this.tempEntry = newEntry;
        this.entry = null;
        this.isEdit = false;
        updateUI();
 
    }
      
}
