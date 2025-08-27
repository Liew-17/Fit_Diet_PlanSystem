package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;

import application.AppContext;
import application.Main;
import controller.LibraryController.FilterType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import object.DailyPlan;
import object.Entry;
import object.Entry.EntryType;
import object.Food;
import object.FoodEntry;
import object.Sport;


public class DetailController {
	
	@FXML 
	private NavBarController navBarController;
	
	private EntryType prevSelectedType;
	
	private LocalDate selectedDate;
	
	private DailyPlan plan;
	
	private Stage popupStage;
	
	private Scene foodEntryPopUp;
		
	private FoodEntryPopUpController foodEntryPopUpController;
	
	private Scene sportEntryPopUp;
	
	private SportEntryPopUpController sportEntryPopUpController;
	
	private Scene entryDuplicatePopUp;
	
	private EntryDuplicatePopUpController entryDuplicatePopUpController;
	
	@FXML
	private Label dateLabel;
	
	@FXML
	private Label plannedIntakeLabel;

	@FXML
	private Label actualIntakeLabel;

	@FXML
	private Label plannedBurnLabel;

	@FXML
	private Label actualBurnLabel;

	@FXML
	private Label plannedNetLabel;

	@FXML
	private Label actualNetLabel;
	
	@FXML
	private VBox plannedFoodBox;

	@FXML
	private VBox actualFoodBox;

	@FXML
	private Button addFoodPlanBtn;

	@FXML
	private Button addFoodRecordBtn;

	@FXML
	private VBox plannedExeBox;

	@FXML
	private VBox actualExeBox;

	@FXML
	private Button addExeRecordBtn;

	@FXML
	private Button addExePlanBtn;

	@FXML
	private void initialize() throws IOException{
		
		navBarController.setCurrentPage("details");
		prevSelectedType = EntryType.PLANNED;
		
		FXMLLoader foodPopUpLoader = Main.createLoader("/FoodEntryPopUp");
		foodEntryPopUp = new Scene(foodPopUpLoader.load());
		foodEntryPopUpController = foodPopUpLoader.getController();	
		
		FXMLLoader sportPopUpLoader = Main.createLoader("/SportEntryPopUp");
		sportEntryPopUp = new Scene(sportPopUpLoader.load());
		sportEntryPopUpController = sportPopUpLoader.getController();	
		
		FXMLLoader duplicatePopUpLoader = Main.createLoader("/EntryDuplicatePopUp");
		entryDuplicatePopUp = new Scene(duplicatePopUpLoader.load());
		entryDuplicatePopUpController = duplicatePopUpLoader.getController();	
		
	    popupStage = new Stage();
	    popupStage.initModality(Modality.APPLICATION_MODAL);
	    popupStage.setResizable(false);
	    foodEntryPopUpController.setStage(popupStage); 
	    sportEntryPopUpController.setStage(popupStage);
	    entryDuplicatePopUpController.setStage(popupStage);
	    
	    //setup btn
	    addExeRecordBtn.setOnAction(e -> {
	        try {
	            addNewEntry(Entry.EntryType.RECORDED, FilterType.SPORT);
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	    });

	    addExePlanBtn.setOnAction(e -> {
	        try {
	            addNewEntry(Entry.EntryType.PLANNED, FilterType.SPORT);
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	    });

	    addFoodPlanBtn.setOnAction(e -> {
	        try {
	            addNewEntry(Entry.EntryType.PLANNED, FilterType.FOOD);
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	    }); 

	    addFoodRecordBtn.setOnAction(e -> {
	        try {
	            addNewEntry(Entry.EntryType.RECORDED, FilterType.FOOD);
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	    });
	    
	    setDate(LocalDate.now());
	}
	
	@FXML
	private void openDuplicatePopUp() throws IOException {
		popupStage.setScene(entryDuplicatePopUp);
		entryDuplicatePopUpController.setDate(selectedDate);
		popupStage.showAndWait();
		updateUI();
		
	}
	
	private void addEntryCell(Entry entry) throws IOException {
		FXMLLoader loader = Main.createLoader("/EntryCell");
		Parent pane = loader.load(); 
		EntryCellController controller = loader.getController();
		controller.setEntry(entry);
		
		controller.setOnOpenPopUp(()->{
			try {
				openEntryPopUp(entry);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		controller.setOnCompleted(()->{	
			plan.completeEntry(entry);
			try {
				updateUI();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		if(entry.getType() == EntryType.PLANNED || entry.getType() == EntryType.PLANNED_COMPLETE ) {
			if(entry instanceof FoodEntry) {
				plannedFoodBox.getChildren().add(pane);			
			}else {
				plannedExeBox.getChildren().add(pane);				
			}
		} else if (entry.getType() == EntryType.RECORDED) {
			if(entry instanceof FoodEntry) {
				actualFoodBox.getChildren().add(pane);			
			}else {
				actualExeBox.getChildren().add(pane);			
			}			
		}
	}
	
	public void addNewEntry(EntryType entryType, FilterType itemType) throws IOException {
		this.prevSelectedType = entryType;	
		LibraryController controller = (LibraryController) Main.setPage("/LibraryPage");
		controller.setCurrentFilter(itemType);
		controller.setDatePicker(selectedDate);
	}
	
	public void clearEntries() {
		plannedFoodBox.getChildren().clear();
		plannedExeBox.getChildren().clear();
		actualFoodBox.getChildren().clear();
		actualExeBox.getChildren().clear();	
	}
	
	public void updateUI() throws IOException {
		clearEntries();
		dateLabel.setText(selectedDate.toString());
		plannedIntakeLabel.setText(String.format("%.0f kcal", plan.getIntakeCalories(EntryType.PLANNED)));
		actualIntakeLabel.setText(String.format("%.0f kcal", plan.getIntakeCalories(EntryType.RECORDED)));

		plannedBurnLabel.setText(String.format("%.0f kcal", plan.getBurnCalories(EntryType.PLANNED)));
		actualBurnLabel.setText(String.format("%.0f kcal", plan.getBurnCalories(EntryType.RECORDED)));
		
		plannedNetLabel.setText(String.format("%.0f kcal", plan.getNetCalories(EntryType.PLANNED)));
		actualNetLabel.setText(String.format("%.0f kcal", plan.getNetCalories(EntryType.RECORDED)));
		
		List<Entry> entries = plan.getEntries();
		for(Entry entry:entries) {
			addEntryCell(entry);
		}	
	}
	
	public void openEntryPopUp(Food food) throws IOException {
		popupStage.setScene(foodEntryPopUp);
		foodEntryPopUpController.setEntry(food, prevSelectedType);
		popupStage.showAndWait();
		prevSelectedType = EntryType.PLANNED;
		updateUI();
	}
	
	public void openEntryPopUp(Sport sport) throws IOException {
		popupStage.setScene(sportEntryPopUp);
		sportEntryPopUpController.setEntry(sport, prevSelectedType);
		popupStage.showAndWait();
		prevSelectedType = EntryType.PLANNED;
		updateUI();
	}
	
	public void openEntryPopUp(Entry entry) throws IOException {
		if(entry == null)
			return;
    
		if(entry instanceof FoodEntry) {
			popupStage.setScene(foodEntryPopUp);
			foodEntryPopUpController.setEntry(entry);
		}else {
			popupStage.setScene(sportEntryPopUp);	
			sportEntryPopUpController.setEntry(entry);
		}
		
		popupStage.showAndWait();
		updateUI();
	}
	
	public void setDate(LocalDate date) throws IOException {
		this.selectedDate = date;
		plan = AppContext.getSchedule().getDailyPlan(selectedDate);
		foodEntryPopUpController.setPlan(plan);
		sportEntryPopUpController.setPlan(plan);
		updateUI();
	}

}
