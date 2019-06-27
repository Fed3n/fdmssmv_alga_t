package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenuController {
	
	@FXML  
	private Button heapButton;
	
	@FXML
	private Button heapsortButton;     
	
	public void goToHeapLesson(ActionEvent heapPressed) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Lesson.fxml"));
		
		//Creo manualmente un controller e lo inizializzo col suo costruttore
		LessonController controller = new LessonController("Lezione 1: Heap", 3, "lesson1");
		//Setto manualmente il controller nel loader
		loader.setController(controller);
		
		//Creo il parent dal loader con fxml e controller associato *IL FILE FXML NON DEVE AVERE UN CONTROLLER DI DEFAULT*
		Parent heapLessonParent = (Parent)loader.load();
		
		
		
    	Scene heapLessonScene = new Scene(heapLessonParent);
    	
    	Stage window = (Stage)((Node)heapPressed.getSource()).getScene().getWindow();
    	
    	window.setScene(heapLessonScene);
    	window.show();
		
	}
	
	public void goToHeapsortLesson(ActionEvent heapPressed) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Lesson.fxml"));
		
		//Creo manualmente un controller e lo inizializzo col suo costruttore
		LessonController controller = new LessonController("Lezione 2: Heapsort", 1, "lesson1");
		//Setto manualmente il controller nel loader
		loader.setController(controller);
		
		//Creo il parent dal loader con fxml e controller associato *IL FILE FXML NON DEVE AVERE UN CONTROLLER DI DEFAULT*
		Parent heapSortLessonParent = (Parent)loader.load();
		
		
		
    	Scene heapSortLessonScene = new Scene(heapSortLessonParent);
    	
    	Stage window = (Stage)((Node)heapPressed.getSource()).getScene().getWindow();
    	
    	window.setScene(heapSortLessonScene);
    	window.show();
		
	}

}
