package application;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class QuestionAlertController {

    @FXML
    private Label textLabel;

    @FXML
    private Button goButton;

    @FXML
    private Button stayButton;

    @FXML
    private Label titleLabel;
    
    private LessonController lesson = null;
    private ActionEvent originalWindowEvent = null;

    public QuestionAlertController(LessonController contr, ActionEvent event) {
    	this.lesson = contr;
    	this.originalWindowEvent = event;
    }
    
    @FXML
    void closeWindow(ActionEvent event) {
    	Stage thisStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	thisStage.close();
    }

    @FXML
    void procedeToQuestions(ActionEvent event) throws IOException {
    	Stage thisStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	thisStage.close();
    	this.lesson.goToQuestions(this.originalWindowEvent);
    }

}