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
		Parent heapLessonParent = FXMLLoader.load(getClass().getResource("HeapLesson.fxml"));
    	Scene heapLessonScene = new Scene(heapLessonParent);
    	
    	Stage window = (Stage)((Node)heapPressed.getSource()).getScene().getWindow();
    	
    	window.setScene(heapLessonScene);
    	window.show();
		
	}
	
	public void goToHeapsortLesson(ActionEvent heapsortPressed) throws IOException {
		Parent heapsortLessonParent = FXMLLoader.load(getClass().getResource("HeapsortLesson.fxml"));
    	Scene heapsortLessonScene = new Scene(heapsortLessonParent);
    	
    	Stage window = (Stage)((Node)heapsortPressed.getSource()).getScene().getWindow();
    	
    	window.setScene(heapsortLessonScene);
    	window.show();
		
	}

}
