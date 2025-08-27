package controller;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;

import application.AppContext;
import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import object.DailyPlan;

public class EntryDuplicatePopUpController {

    @FXML
    private Label monthYearLabel;
    
	@FXML
	private ToggleGroup duplicateMethod;

    @FXML
    private RadioButton specificDateRadio;

    @FXML
    private RadioButton weekdayRadio;

    @FXML
    private ChoiceBox<ConflictAction> conflictChoiceBox;

    @FXML
    private ChoiceBox<String> weekdayChoiceBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private StackPane modeStackPane;

    @FXML
    private VBox datePane;

    @FXML
    private VBox weekdayPane;

    @FXML
    private Button prevMonthBtn;

    @FXML
    private Button nextMonthBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button confirmBtn;
    
    private Stage popupStage;
    
    private LocalDate date;
    
    private YearMonth selectedMonth;
  
    public enum ConflictAction {
        REPLACE("Replace entirely"),
        COMBINE("Combine"),
        IGNORE("Ignore if exists");

        private final String displayName;

        ConflictAction(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName; 
        }
    }

    @FXML
    private void initialize() {
    	duplicateMethod.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
		    if (newToggle != null) {
		        updateMethodPane();
		    }
		});
    	
    	updateMethodPane();
    	
        weekdayChoiceBox.getItems().addAll("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");                        
        weekdayChoiceBox.getSelectionModel().selectFirst();
        
        conflictChoiceBox.getItems().addAll(ConflictAction.values());      
        conflictChoiceBox.getSelectionModel().selectFirst();;     
        
    }
    
    @FXML
    private void cancel() {
    	closePopUp();
    }

    @FXML
    private void duplicate() { 	
    	if(specificDateRadio.isSelected()) {
	    	try {
		    	if(datePicker.getValue() == null) throw new IllegalArgumentException("Please select a date");
		    	if(datePicker.getValue().equals(date)) throw new IllegalArgumentException("Cant duplicate on same date!");
		    	if(Main.confirmationAction("Confirm Duplicate","Duplicate Plan","Are you sure you want to duplicate this plan?\n\nThis action cannot be undone.")) {
		    		AppContext.getSchedule().duplicatePlan(date, datePicker.getValue(), conflictChoiceBox.getValue());
		    		closePopUp();
		    	}		    	
	    	} catch (IllegalArgumentException e) {
	    	    Main.errorMessage("Invalid Input","Can't duplicate the plan",e.getMessage());          
	    	}
    	}
    	else {
    	   	if(Main.confirmationAction("Confirm Duplicate","Duplicate Plan","Are you sure you want to duplicate this plan?\n\nThis action cannot be undone.")) {
	    		AppContext.getSchedule().duplicatePlan(date, selectedMonth, getSelectedWeekdayInt(weekdayChoiceBox.getValue()) , conflictChoiceBox.getValue());
	    		closePopUp();
    	   	}	  		
    	}
    
    }
    
    @FXML
    private void prevMonth() {
        selectedMonth = selectedMonth.minusMonths(1);
        updateUI();
    }

    @FXML
    private void nextMonth() {
        selectedMonth = selectedMonth.plusMonths(1);
        updateUI();
    }
    
    private void closePopUp() {
    	if (popupStage!=null)
    		popupStage.close();
    }

    private void updateUI() {
        if (selectedMonth != null) {
            monthYearLabel.setText(selectedMonth.getMonth() + " " + selectedMonth.getYear());
        }
    }

    public void updateMethodPane() {
    	datePane.setVisible(specificDateRadio.isSelected());
    	weekdayPane.setVisible(!specificDateRadio.isSelected());
    }
   
    public void setDate(LocalDate selectedDate) {
    	this.date = selectedDate;
    	this.selectedMonth = YearMonth.from(selectedDate);
    	//reset fields
    	specificDateRadio.isSelected();
    	weekdayChoiceBox.getSelectionModel().selectFirst();
    	conflictChoiceBox.getSelectionModel().selectFirst(); 
    	datePicker.setValue(null);
    	
    	updateUI();
    } 
    
    public void setStage(Stage stage) {
    	this.popupStage=stage;  
    }
    
    private int getSelectedWeekdayInt(String weekday) {
        if (weekday == null) return -1; 
        switch (weekday) {
            case "Monday": return 1;
            case "Tuesday": return 2;
            case "Wednesday": return 3;
            case "Thursday": return 4;
            case "Friday": return 5;
            case "Saturday": return 6;
            case "Sunday": return 7;
            default: return -1;
        }
    }

}
