package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;

public class QuestionController {
	
	@FXML
    private Button PrevQuestionButton;

    @FXML
    private Button NextQuestionButton;

    @FXML
    private Label QuestionsTitleLabel;

    @FXML
    private TextArea QuestionTextArea;

    @FXML
    private RadioButton AnswerButton1;

    @FXML
    private ToggleGroup answers;

    @FXML
    private RadioButton AnswerButton2;

    @FXML
    private RadioButton AnswerButton3;

    @FXML
    private Button CheckAnswerButton;

	private boolean rightChoice = false;	
	
	@FXML
    void AnswerSelected(ActionEvent event)  throws IOException {
		RadioButton b = (RadioButton)event.getSource();
		if (b.equals(this.AnswerButton1)) this.rightChoice = true;
		else this.rightChoice = false;
    }

    @FXML
    void checkAnswer(ActionEvent event) throws IOException {
    	Button b = (Button)event.getSource();
		if (b.equals(CheckAnswerButton) && this.rightChoice) 
			this.PrevQuestionButton.setDisable(true);
    }	
	
}