package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import java.util.*;
import object.Food;
import object.Food.FoodCategory;
import object.Sport;
import object.TimeBasedExerciseEntry;
import object.DailyPlan;
import object.Entry;
import object.FoodEntry;
import object.LibraryItem.LibraryType;
import object.RepBasedExerciseEntry;

import java.time.LocalDate;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import java.util.Optional;

/**
 * Main application class.
 * <p>
 * Manages all loaded pages and their controllers to minimize repetitive UI reloads.
 * Also initializes required data from the database at startup.
 * Provides static methods for scene switching and retrieving the corresponding controllers.
 */
public class Main extends Application {
	
	private static Stage stage; 
    private static Scene scene;	
    
    /**
     * Cache for loaded FXML pages.
     * <p>
     * The key is the page name (String), and the value is the corresponding {@link Parent} node.
     * This prevents reloading the same FXML file multiple times.
     */
    private static Map<String, Parent> pages = new HashMap<>();
    
    /**
     * A cache mapping page names to their corresponding controller instances.
     * <p>
     * This allows quick retrieval of controllers for pages that have already been loaded,
     * avoiding the need to reload FXML files multiple times.
     * </p>
     */
    private static Map<String, Object> controllers = new HashMap<>(); 
	
	@Override 
	public void start(Stage primaryStage) { 
		try { 
			Parent root = FXMLLoader.load(getClass().getResource("/DetailPage.fxml")); 			
			scene = new Scene(root); 			
			stage = primaryStage; 			
			primaryStage.setScene(scene); 			
			primaryStage.setTitle("Diet Plan System"); 			
			primaryStage.setResizable(false); 
			primaryStage.show();			
			setPage("/ProfilePage");		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
		
	public static void main(String[] args) {
		launch(args);		
	}
	
	public static Stage getStage() {
		return stage;
	}
		
	/**
	 * Sets the current page (scene) in the application.
	 * <p>
	 * If the specified page has already been loaded, it will be retrieved from
	 * the {@code pages} cache to avoid reloading. Otherwise, the page will be
	 * loaded from its FXML file, cached along with its controller, and then displayed.
	 * </p>
	 *
	 * <p><b>Note:</b> A leading "/" before the page name is required when specifying
	 * the {@code page} parameter to ensure correct resource resolution.</p>
	 *
	 * @param page the name of the page (FXML file without extension) to load and display
	 * @throws IOException if the FXML file cannot be loaded
	 */
	public static Object setPage(String page) throws IOException {
	    if(!pages.containsKey(page)) {
	        FXMLLoader fxmlLoader = createLoader(page);
	        Parent root = fxmlLoader.load();
	        pages.put(page, root);
	        controllers.put(page, fxmlLoader.getController());
	    }
	    scene.setRoot(pages.get(page));
	    return controllers.get(page); // return controller
	}
    
    /**
     * Retrieves the controller instance associated with the specified page.
     * <p>
     * The controller is obtained from the {@code controllers} cache, which stores controllers
     * that were previously loaded along with their FXML pages.
     * </p>
     *
     * @param page the name of the page whose controller is to be retrieved
     * @return the controller object for the specified page, or {@code null} if not found
     */  
    public static Object getController(String page){
        return controllers.get(page);  
    }
    
    public static FXMLLoader createLoader(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        return fxmlLoader;
    }
    
    /**
     * Displays a confirmation dialog with the specified title, header, and content.
     * <p>
     * This static method creates a JavaFX {@link Alert} of type 
     * {@link Alert.AlertType#CONFIRMATION} and waits for the user 
     * to respond. It returns boolean if the user selects OK, false if user select cancels or closes the dialog.
     * </p>
     *
     * @param title   the title of the confirmation dialog window
     * @param header  the header text displayed at the top of the dialog
     * @param content the main content message displayed in the dialog
     * @return {@code true} if the user clicks OK; {@code false} if the user cancels or closes the dialog
     */ 
    public static boolean confirmationAction(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
    
    
    /**
     * Displays a error message with the specified title, header, and content.
     * <p>
     * This static method creates a JavaFX {@link Alert} of type 
     * {@link Alert.AlertType#ERROR} and is used to display a error message.
     * </p>
     *
     * @param title   the title of the confirmation dialog window
     * @param header  the header text displayed at the top of the dialog
     * @param content the main content message displayed in the dialog
     */ 
    public static void errorMessage(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
