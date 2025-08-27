package controller;
import java.io.IOException;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import object.Entry;
import object.Entry.EntryType;

public class EntryCellController {

	private Entry entry;
	
	@FXML
	private Label itemLabel; 
	
	@FXML
	private Label infoLabel;
	
	@FXML
	private Button finishBtn;
	
	@FXML
	private Button editBtn;
	 
    private Runnable onOpenPopUp; 
    
    private Runnable onCompleted;

    public void setOnOpenPopUp(Runnable callback) {
        this.onOpenPopUp = callback;
    }
    
    public void setOnCompleted(Runnable callback) {
        this.onCompleted = callback;
    }
    
	@FXML
	private void completeEntry() throws IOException {
		if(entry.getType()==EntryType.PLANNED) {
			onCompleted.run();	 
		}              	
	}
	
	@FXML
	private void editEntry() throws IOException {
		onOpenPopUp.run();	
	}
	
	private void updateUI() {		
		itemLabel.setText(entry.getDisplayName());
		infoLabel.setText(entry.getDisplayInfo());
		if(entry.getType()!=EntryType.PLANNED) {
			finishBtn.setVisible(false);			
		}
	}
	
	public void setEntry(Entry entry) {
		this.entry = entry;
		updateUI();
	}
	

	
	
}
