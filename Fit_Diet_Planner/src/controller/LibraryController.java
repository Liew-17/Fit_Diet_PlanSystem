package controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.TilePane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import application.*;


import java.util.List;


import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;


import object.Food;
import object.Food.FoodCategory;
import object.LibraryItem.LibraryType;
import object.LibraryItem;
import object.Sport;


public class LibraryController {
	
	@FXML 
	private NavBarController navBarController;
	
	private Stage popupStage;
	
	private Scene newItemPopUp;
		
	private NewItemPopUpController newItemPopUpController;
	
	
	private FilterType currentFilter;
	
	public enum FilterType{
		FOOD,
		SPORT
	}
	
	@FXML
	private Button foodFilterBtn;
	
	@FXML
	private Button sportFilterBtn;
	
	@FXML
	private DatePicker datePicker;
	
	@FXML
	private TilePane libraryPane;
	
	@FXML
	private TextField searchField;
	
	@FXML
	private ChoiceBox<String> foodCategoryBox;
	
	@FXML
	private ChoiceBox<String> sportCategoryBox;

	@FXML
	private void initialize() throws IOException {	
		navBarController.setCurrentPage("library");
		
        foodCategoryBox.getItems().add("All");       
        for (FoodCategory category : FoodCategory.values()) {
            foodCategoryBox.getItems().add(category.toString());
        }
        foodCategoryBox.setValue("All");
        
        sportCategoryBox.getItems().addAll("All", "Rep-Based", "Time-Based");
        sportCategoryBox.setValue("All");
        
		setCurrentFilter(FilterType.FOOD);
	    datePicker.setValue(LocalDate.now());
	    
		FXMLLoader foodPopUpLoader = Main.createLoader("/NewItemPopUp");
		newItemPopUp = new Scene(foodPopUpLoader.load());
		newItemPopUpController = foodPopUpLoader.getController();	
	    
	    popupStage = new Stage();
	    popupStage.initModality(Modality.APPLICATION_MODAL);
	    popupStage.setResizable(false);
	    newItemPopUpController.setStage(popupStage); 
	    
	    	    	    
	    foodFilterBtn.setOnAction(e -> {
	    	try {
				setCurrentFilter(FilterType.FOOD);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    });
	    
	    sportFilterBtn.setOnAction(e -> {
	    	try {
				setCurrentFilter(FilterType.SPORT);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    });
	          
        foodCategoryBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
			try {
				updateUI();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
        
     
        sportCategoryBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
			try {
				updateUI();
			} catch (IOException e1) {
				e1.printStackTrace();
			} 
		}); 
        
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
			try {
				updateUI();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
      
	    updateUI();
	} 
	
	@FXML
	private void addNewItem() throws IOException {
		popupStage.setScene(newItemPopUp);
		newItemPopUpController.resetField(currentFilter);
		popupStage.showAndWait();
		updateUI();	
	}
	
	public void setDatePicker(LocalDate date) {
		datePicker.setValue(date);
	}
	
	public void setCurrentFilter(FilterType currentFilter) throws IOException {
		this.currentFilter = currentFilter;
	    searchField.clear();
	    foodCategoryBox.setValue("All");
	    sportCategoryBox.setValue("All");
	    updateUI();
	}
	
	@FXML
	private void resetSearchField() throws IOException {
		searchField.clear();
		updateUI();	
	}
	
	public void updateUI() throws IOException {
	    foodFilterBtn.setStyle("");
	    sportFilterBtn.setStyle("");

	    if (currentFilter == FilterType.FOOD) {
	        foodFilterBtn.setStyle("-fx-background-color: lightblue;");
	        foodCategoryBox.setVisible(true);
	        sportCategoryBox.setVisible(false);
	    } else if (currentFilter == FilterType.SPORT) {
	        sportFilterBtn.setStyle("-fx-background-color: lightblue;");
	        foodCategoryBox.setVisible(false);
	        sportCategoryBox.setVisible(true);
	    }
	    		
	    updateLibraryItem();	
	}
	
	public void updateLibraryItem() throws IOException {
		libraryPane.getChildren().clear();
		if(currentFilter == FilterType.FOOD) {
			List<Food> foodList = new ArrayList<>(AppContext.getLibrary().getFoodList().values());
			for(Food food: foodList){	
				if(food.getLibraryType()==LibraryType.ARCHIVED)
					continue;
				
				String searchText = searchField.getText().toLowerCase();
				String selectedCategory = foodCategoryBox.getValue();
				boolean matchesSearch = searchText.isEmpty() 
					    || (food.getName() != null && food.getName().toLowerCase().contains(searchText));
			    boolean matchesCategory = selectedCategory.equals("All") || 
                        food.getCategory().toString().equals(selectedCategory);

				if(!matchesSearch||!matchesCategory)
					continue;
				
				generateItemBlock(food);						
			}		 
		}
		else{
			List<Sport> sportList = new ArrayList<>(AppContext.getLibrary().getSportList().values());
			for(Sport sport: sportList){	
				if(sport.getLibraryType()==LibraryType.ARCHIVED)
					continue;
				
				String searchText = searchField.getText().toLowerCase();
				String selectedCategory = sportCategoryBox.getValue();
				boolean matchesSearch = searchText.isEmpty() 
					    || (sport.getName() != null && sport.getName().toLowerCase().contains(searchText));
				boolean matchesCategory = selectedCategory.equals("All") ||
				        (selectedCategory.equals("Rep-Based") && sport.isRepBased()) ||
				        (selectedCategory.equals("Time-Based") && !sport.isRepBased());
				
				if(!matchesSearch||!matchesCategory)
					continue;
				
				generateItemBlock(sport);
					
			}
							
		}
	}
	
	public void generateItemBlock(LibraryItem item) throws IOException {
		FXMLLoader loader = Main.createLoader("/ItemBlock");
		Parent pane = loader.load(); 
		ItemBlockController controller = loader.getController();
		controller.setItem(item);
		
		controller.setOnSelect(()->{
			LocalDate selectedDate = datePicker.getValue();
		    try {
		    	DetailController detailcontroller = (DetailController)Main.setPage("/DetailPage");
		    	detailcontroller.setDate(selectedDate);						
		        if (item instanceof Food food) {
		        	detailcontroller.openEntryPopUp((Food)item);
		        } else if (item instanceof Sport sport) {
		        	detailcontroller.openEntryPopUp((Sport)item);
		        }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		});	
		
		controller.setOnRemove(()->{
			item.archieve();

			
			try {
				updateUI();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});	
		libraryPane.getChildren().add(pane);	
	}
}
