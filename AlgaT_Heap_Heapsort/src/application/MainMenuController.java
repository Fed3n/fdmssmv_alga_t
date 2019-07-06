package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class MainMenuController {

	@FXML
	private Button creditsButton;

	@FXML
	private Button quitButton;

	@FXML
	private Button heapButton;

	@FXML
	private Button priorityQueueButton;

	@FXML
	private Button heapsortButton;

	private LessonController lesson1;

	private LessonController lesson2;

	private LessonController lesson3;

	private Stage currentStage = null;
	
	public MainMenuController() {
		this.lesson1 = null;
		this.lesson2 = null;
		this.lesson3 = null;
	}

	public MainMenuController(MainMenuController lastController) {
		if (lastController.getFirstLesson() != null) this.lesson1 = lastController.getFirstLesson();
		if (lastController.getSecondLesson() != null) this.lesson2 = lastController.getSecondLesson();
		if (lastController.getThirdLesson() != null) this.lesson3 = lastController.getThirdLesson();
	}

	public void quit(ActionEvent quitPressed) {
		Alert alert = new Alert(AlertType.CONFIRMATION, "Sicuro di volere uscire?", ButtonType.YES, ButtonType.NO);
		alert.showAndWait();

		if(alert.getResult() == ButtonType.YES) {
		Stage stage = (Stage)((Node)quitPressed.getSource()).getScene().getWindow();
		stage.close();
		this.currentStage = null;
		}
	}

	public void openCredits() throws IOException {
		Parent creditsParent = FXMLLoader.load(getClass().getResource("Credits.fxml"));
		Stage creditsWindow = new Stage();
		Scene creditsScene = new Scene(creditsParent);

		creditsWindow.setTitle("Credits");
		creditsWindow.setScene(creditsScene);
		creditsWindow.show();

	}

	public void goToHeapLesson(ActionEvent heapPressed) throws IOException {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("Lesson.fxml"));
		LessonController controller;
		if (this.lesson1 == null) {
			controller = new LessonController("Lezione 1: Heap", 5, "lesson1");
			this.lesson1 = controller;
		} else 
			controller = this.lesson1;
		loader.setController(controller);

		//Creo il parent dal loader con fxml e controller associato *IL FILE FXML NON DEVE AVERE UN CONTROLLER DI DEFAULT*
		Parent heapLessonParent = (Parent)loader.load();

    	Scene heapLessonScene = new Scene(heapLessonParent);

    	Stage window = (Stage)((Node)heapPressed.getSource()).getScene().getWindow();

    	window.setScene(heapLessonScene);
    	window.setUserData(this);
    	window.show();

    	this.currentStage = window;
	}

	public void goToPriorityQueueLesson(ActionEvent heapPressed) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Lesson.fxml"));
		LessonController controller;
		if (this.lesson2 == null) {
			//Creo manualmente un controller e lo inizializzo col suo costruttore
			controller = new LessonController("Lezione 2: PriorityQueue", 5, "lesson2");
			this.lesson2 = controller;	
		} else 
			controller = this.lesson2;
		loader.setController(controller);

		//Creo il parent dal loader con fxml e controller associato *IL FILE FXML NON DEVE AVERE UN CONTROLLER DI DEFAULT*
		Parent priorityQueueParent = (Parent)loader.load();

    	Scene priorityQueueScene = new Scene(priorityQueueParent);

    	Stage window = (Stage)((Node)heapPressed.getSource()).getScene().getWindow();

    	window.setScene(priorityQueueScene);
    	window.setUserData(this);
    	window.show();

    	this.currentStage = window;
	}
	
	public void goToHeapsortLesson(ActionEvent heapPressed) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Lesson.fxml"));
		LessonController controller;
		if (this.lesson3 == null) {
			controller = new LessonController("Lezione 3: Heapsort", 2, "lesson3");
			this.lesson3 = controller;
		} else 
			controller = this.lesson3;
		loader.setController(controller);

		//Creo il parent dal loader con fxml e controller associato *IL FILE FXML NON DEVE AVERE UN CONTROLLER DI DEFAULT*
		Parent heapSortLessonParent = (Parent)loader.load();

    	Scene heapSortLessonScene = new Scene(heapSortLessonParent);

    	Stage window = (Stage)((Node)heapPressed.getSource()).getScene().getWindow();

    	window.setScene(heapSortLessonScene);
    	window.setUserData(this);
    	window.show();

    	this.currentStage = window;
	}
	
	public void upgradeLessons(LessonController lesson) {
		Integer num = lesson.getLessonNumber();
		if (num == 1) this.lesson1 = lesson;
		if (num == 2) this.lesson2 = lesson;
		if (num == 3) this.lesson3 = lesson;
	}

	public LessonController getFirstLesson() {
		return this.lesson1;
	}

	public LessonController getSecondLesson() {
		return this.lesson2;
	}

	public LessonController getThirdLesson() {
		return this.lesson3;
	}

	public void setFirstLesson(LessonController controller) {
		this.lesson1 = controller;
	}

	public void setSecondLesson(LessonController controller) {
		this.lesson2 = controller;
	}

	public void setThirdLesson(LessonController controller) {
		this.lesson3 = controller;
	}
	
	public Stage getStage() {
		return this.currentStage;
	}
}
