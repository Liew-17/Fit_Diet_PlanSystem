package controller;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import application.Utils;
import javafx.fxml.FXML;
import object.Food;
import object.LibraryItem;
import object.LibraryItem.LibraryType;

public class ItemBlockController {

	private LibraryItem item;
	
	@FXML
	private Button removeBtn;
	
    @FXML
    private Label nameLabel;    

    @FXML
    private Label descriptionLabel;     
        
    @FXML
    private Label categoryLabel; 
    
    private Runnable onSelect; 
    
    private Runnable onRemove; 
    
    public void setOnSelect(Runnable callback) {
        this.onSelect = callback;
    }
	
    public void setOnRemove(Runnable callback) {
        this.onRemove = callback;
    }
    
    @FXML 
    private void selectItem() {
    	onSelect.run();    
    } 
    
    @FXML
    private void remove() {
    	onRemove.run();
    }
    
    private void updateUI() {
    	nameLabel.setText(Utils.capitalizeSafe(item.getName()));
    	categoryLabel.setText(item.getDisplayCategory());
    	descriptionLabel.setText(item.generateDescription());
    	if(item.getLibraryType()!=LibraryType.CUSTOM) {
    		removeBtn.setVisible(false);
    		removeBtn.setManaged(false);
    		
    	}
    }
	
	public void setItem(LibraryItem item) {
		this.item = item;
		updateUI();
	}
}
