package controller;

import java.io.IOException;
import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
 
public class NavBarController {

	@FXML
	private Button profileBtn;

	@FXML
	private Button libraryBtn;

	@FXML
	private Button scheduleBtn;
	
	@FXML
	private Button detailsBtn;
	
    @FXML
    private void goDetails() throws IOException {
    	Main.setPage("/DetailPage");
     
    }

    @FXML
    private void goLibrary() throws IOException {
        Main.setPage("/LibraryPage");
    }

    @FXML
    private void goProfile() throws IOException {
        ProfileController controller = (ProfileController)Main.setPage("/ProfilePage");
        controller.resetPage();
    }

    @FXML
    private void goSchedule() throws IOException {
        ScheduleController controller = (ScheduleController)Main.setPage("/SchedulePage");
        controller.updateUI();
    }
    

    public void setCurrentPage(String currentPage) {
        switch (currentPage) {
        case "profile":
            profileBtn.setStyle("-fx-background-color: lightblue;");
            break;
        case "library":
            libraryBtn.setStyle("-fx-background-color: lightblue;");
            break;
        case "schedule":
            scheduleBtn.setStyle("-fx-background-color: lightblue;");
            break;
        case "details":
        	detailsBtn.setStyle("-fx-background-color: lightblue;");
            break;

    }
    	
    }
    
}
