package controller;

import application.AppContext;
import application.Main;
import application.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import object.User;

public class ProfileController {


	@FXML 
	private NavBarController navBarController;
	
	@FXML
	private TextField nameInput;

	@FXML
	private TextField ageInput;

	@FXML
	private TextField weightInput;

	@FXML
	private TextField heightInput;

	@FXML
	private Label bmiScoreLabel;

	@FXML
	private StackPane buttonStack;

	@FXML
	private HBox editPane;

	@FXML
	private HBox updatePane;

	@FXML
	private TextField calcWeightInput;

	@FXML
	private TextField calcHeightInput;

	@FXML
	private Button calculateButton;

	@FXML
	private Label calcBmiScoreLabel;

	@FXML
	private Label calcBmiResultLabel;

	private User user;
	
	private boolean isEditing = false;
	
	@FXML
	private void initialize(){
		navBarController.setCurrentPage("profile");
		Utils.setIntField(ageInput);
		Utils.setDoubleField(weightInput);
		Utils.setDoubleField(heightInput);
		Utils.setDoubleField(calcWeightInput);
		Utils.setDoubleField(calcHeightInput);
		user = AppContext.getUser();
		updateUI();
		
	}
	
	@FXML
	private void onEdit() {
		isEditing = true;
		updateUI(); 
	}
	
	@FXML
	private void onUpdate() {
        try {
        	user.setName(nameInput.getText());
        	user.setAge(Utils.parseIntOrZero(ageInput.getText()));
        	user.setWeight(Utils.parseDoubleOrZero(weightInput.getText()));
        	user.setHeight(Utils.parseDoubleOrZero(heightInput.getText()));
        	isEditing = false;
    		updateUI(); 
        } catch (Exception  e) {
            Main.errorMessage("Invalid User Info", "Cannot Update", e.getMessage());
        }
        

	}
	
	@FXML
	private void onCancel() {
        if (Main.confirmationAction("Cancel", "Are you sure you want to cancel?", "Any unsaved changes will be lost.")) {
    		isEditing = false;
    		updateUI(); 
        }
	}
	
	public void resetPage() {
		isEditing = false;
		updateUI(); 
		
	}
	
	@FXML
	private void calculateBMI() {
		String result;
		Double score;
		Double weight = Utils.parseDoubleOrZero(calcWeightInput.getText());
		Double height = Utils.parseDoubleOrZero(calcHeightInput.getText());
        try {
        	score = user.calculateBMIScore(weight, height);
        	result = user.getBMIResult(weight, height);
        	calcBmiScoreLabel.setText(String.valueOf(score));
        	calcBmiResultLabel.setText(String.valueOf(result));
        } catch (Exception  e) {
            Main.errorMessage("Invalid Input Info", "Cannot Calculate", e.getMessage());
        }
	}
	
	public void updateUI() {
		nameInput.setText(user.getName());
		ageInput.setText(String.valueOf(user.getAge()));
		weightInput.setText(String.valueOf(user.getWeight()));
		heightInput.setText(String.valueOf(user.getHeight()));
		try {
			double score = user.calculateBMIScore();
			String result = user.getBMIResult();
			bmiScoreLabel.setText(score+" ("+result+")");
		}catch (Exception  e) {
			bmiScoreLabel.setText("N/A");
        }
				
		nameInput.setEditable(isEditing);
		ageInput.setEditable(isEditing);
		weightInput.setEditable(isEditing);
		heightInput.setEditable(isEditing);
		editPane.setVisible(!isEditing);
		updatePane.setVisible(isEditing);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
