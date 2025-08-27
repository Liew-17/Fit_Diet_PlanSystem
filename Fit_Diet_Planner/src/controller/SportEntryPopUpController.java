package controller;

import application.AppContext;
import application.Main;
import application.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import object.Entry.EntryType;
import object.DailyPlan;
import object.Entry;
import object.ExerciseEntry;
import object.FoodEntry;
import object.RepBasedExerciseEntry;
import object.Sport;
import object.TimeBasedExerciseEntry;

public class SportEntryPopUpController {

    @FXML 
    private Label headerLabel;
    
    @FXML 
    private Label exerciseNameLabel;
    
    @FXML 
    private Label exerciseMetLabel;
    
    @FXML 
    private Label exerciseTypeLabel;
    
    @FXML 
    private Label secPerRepLabel;
    
    @FXML
    private Label secPerRepHintLabel;

    @FXML 
    private TextField weightField;

    @FXML 
    private Label caloriesPreviewLabel;

    @FXML 
    private StackPane exerciseInputStack;
    
    @FXML 
    private HBox timeBasedPane;
    
    @FXML 
    private TextField durationField;

    @FXML 
    private HBox repBasedPane;
    
    @FXML 
    private TextField repsField;
    
    @FXML 
    private TextField setsField;
    
    @FXML 
    private TextField secPerRepField;

    @FXML 
    private TextArea noteArea;
    
    @FXML
    private RadioButton plannedRadio;
    
    @FXML
    private RadioButton actualRadio;
    
    @FXML
    private ToggleGroup entryTypeGroup;

    @FXML 
    private Button deleteBtn;
    
    @FXML 
    private Button updateBtn;
    
    @FXML 
    private Button saveBtn;

    private Stage popUpStage;
    
    private DailyPlan plan;
    
    private ExerciseEntry entry;
    
    private ExerciseEntry tempEntry;
    
    private boolean isEdit = false;
    
    @FXML
    private void initialize(){
    	Utils.setDoubleField(weightField);
    	Utils.setIntField(repsField);
    	Utils.setIntField(setsField);
    	Utils.setDoubleField(secPerRepField);
    	Utils.setDoubleField(durationField);
    	
    	weightField.textProperty().addListener((obs, oldValue, newValue) -> {
    		tempEntry.setWeight(Utils.parseDoubleOrZero(newValue));
    		updatePreviewCalorie();
    	});

    	repsField.textProperty().addListener((obs, oldValue, newValue) -> {
    	    if (tempEntry instanceof RepBasedExerciseEntry repBased) {
    	        repBased.setReps(Utils.parseIntOrZero(newValue));
    	        updatePreviewCalorie();
    	    }
    	});

    	setsField.textProperty().addListener((obs, oldValue, newValue) -> {
    	    if (tempEntry instanceof RepBasedExerciseEntry repBased) {
    	        repBased.setSets(Utils.parseIntOrZero(newValue));
    	        updatePreviewCalorie();
    	    }
    	});

    	secPerRepField.textProperty().addListener((obs, oldValue, newValue) -> {
    	    if (tempEntry instanceof RepBasedExerciseEntry repBased) {
    	        repBased.setSecPerRep(Utils.parseDoubleOrZero(newValue));
    	        updatePreviewCalorie();
    	    }
    	});
    	
    	durationField.textProperty().addListener((obs, oldValue, newValue) -> {
    	    if (tempEntry instanceof TimeBasedExerciseEntry timeBased) {
    	    	timeBased.setDuration(Utils.parseDoubleOrZero(newValue));
    	    	updatePreviewCalorie();
    	    }
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
    private void saveEntry() {
        try {
            tempEntry.validate();        
            plan.addEntry(tempEntry);
            closePopUp();
        } catch (Exception  e) {
            Main.errorMessage("Invalid Entry", "Cannot Save", e.getMessage());
        }
    }
    
    private void closePopUp() {
        if (popUpStage != null) {
        	popUpStage.close();
        }   	
    }

    private void toggleInputMode(boolean repBased) {
    	repBasedPane.setVisible(repBased);
    	timeBasedPane.setVisible(!repBased);
    }
    
    public void updatePreviewCalorie() {
    	caloriesPreviewLabel.setText(tempEntry.getCaloriesPreview());	
    }

    private void updateUI() {
    	if(isEdit) {
    		headerLabel.setText("Edit Sport Entry");  
    		saveBtn.setVisible(false);
    		updateBtn.setVisible(true);
    		deleteBtn.setVisible(true);
    		popUpStage.setTitle("Edit Entry");
    	}    			
    	else {
    		headerLabel.setText("New Sport Entry");
    		saveBtn.setVisible(true);
    		updateBtn.setVisible(false);
    		deleteBtn.setVisible(false);
    		popUpStage.setTitle("New Entry");
    	}
    	
    	Sport sport = tempEntry.getSport();
        exerciseNameLabel.setText(Utils.capitalizeSafe(sport.getName()));
        exerciseMetLabel.setText(String.valueOf(sport.getMet()));
        exerciseTypeLabel.setText(sport.getDisplayCategory());

        if (sport.isRepBased()) {
            secPerRepLabel.setText(sport.getSecondsPerRep()+" s");
            secPerRepLabel.setVisible(true);
            secPerRepHintLabel.setVisible(true);
        } else {
            secPerRepLabel.setVisible(false);
            secPerRepHintLabel.setVisible(false);
        }
        
        weightField.setText(String.valueOf(tempEntry.getWeight()));
    	
        if (tempEntry instanceof RepBasedExerciseEntry repBased) {
        	toggleInputMode(true);
        	repsField.setText(String.valueOf(repBased.getReps()));
        	setsField.setText(String.valueOf(repBased.getSets()));
        	secPerRepField.setText(String.valueOf(repBased.getSecPerRep()));
        	
        }else if(tempEntry instanceof TimeBasedExerciseEntry timeBased) {
        	toggleInputMode(false);
        	durationField.setText(String.valueOf(timeBased.getDuration()));      	
        }
        
        updatePreviewCalorie();
    	
        if(tempEntry.getType()==EntryType.PLANNED || tempEntry.getType()==EntryType.PLANNED_COMPLETE)
        	plannedRadio.setSelected(true);
        else if(tempEntry.getType()==EntryType.RECORDED)
        	actualRadio.setSelected(true);
        
        noteArea.setText(tempEntry.getNote());
    		
    }
    
	public void setEntry(Sport sport, EntryType prevSelectedType) {
		ExerciseEntry newEntry;
		if(sport.isRepBased()) {
			newEntry = new RepBasedExerciseEntry(prevSelectedType,"" ,sport,AppContext.getUser().getWeight(),1,1,sport.getSecondsPerRep());						
		}else {
			newEntry = new TimeBasedExerciseEntry(prevSelectedType,"",sport,AppContext.getUser().getWeight(),5);		
		}
    	   	
        this.tempEntry = newEntry;
        this.isEdit = false;
        updateUI();
 
	}
	
	public void setEntry(Entry entry) {
		this.entry = (ExerciseEntry) entry;
		this.tempEntry = (ExerciseEntry) this.entry.clone(); 
		isEdit = true;
		updateUI();
	}

	public void setStage(Stage popUpStage) {
		this.popUpStage = popUpStage;
		
	}
	
    public void setPlan(DailyPlan plan) {
    	this.plan = plan;
    }

	
}



