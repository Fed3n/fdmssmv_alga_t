package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
    private Button prevButton;

    @FXML
    private Button nextButton;

    @FXML
    private Label titleLabel;

    @FXML
    private TextArea textArea;

    @FXML
    private ToggleGroup radioButtons;
    
    @FXML
    private RadioButton answerButton1;

    @FXML
    private RadioButton answerButton2;

    @FXML
    private RadioButton answerButton3;

    @FXML
    private Button checkButton;
	
    //costante non modificabile
    private final int MAX_QUESTION_NUMBER = 3; 
    
    //vettore contenente le risposte
    private ArrayList<String> answers = new ArrayList<String>();
    //vettore contenente booleani relativi alle risposte
    private ArrayList<Boolean> results = new ArrayList<Boolean>();
    
    private Integer lessonNumber = 1;
    
    private Integer questionNumber = 1;
    //la variabile segnala se la corrente selezione della risposta è esatta
    private Boolean rightSelection = false;
	
	public void initialize() throws Exception {
		this.prevButton.setDisable(true);
		this.nextButton.setDisable(true);
		this.textArea.setEditable(false);
		this.textArea.setWrapText(true);
		this.titleLabel.setText("Domanda n° "+this.questionNumber.toString());
		try {
			BufferedReader reader = Files.newBufferedReader(Paths.get("./question_lesson"+this.lessonNumber.toString()+"_"+this.questionNumber.toString()+".txt"), StandardCharsets.UTF_8);
			int i = 0;
			String line;
			String quest = null;
			while ((line = reader.readLine()) != null){
				if (i == 0) //la prima riga contiene la domanda
					quest = line;
				else {  //il primo carattere contiene (T/F) true o false relativo alla risposta e segue la domanda 
					results.set(i-1, line.charAt(0) == 'T');
					answers.set(i-1, line.substring(1));
				}
				i++;
				if (i > this.MAX_QUESTION_NUMBER+1) throw new Exception();
			}
			this.textArea.setText(quest);
			this.answerButton1.setText(answers.get(0));
			this.answerButton2.setText(answers.get(1));
			this.answerButton3.setText(answers.get(2));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public QuestionController(Integer lessNum, Integer questNum) {
		this.lessonNumber = lessNum;
		this.questionNumber = questNum;
	}
	
    public void AnswerSelected(ActionEvent event)  throws IOException {
		RadioButton b = (RadioButton)event.getSource();
		if ((b.equals(this.answerButton1)) && results.get(0)) this.rightSelection = true;
		else if ((b.equals(this.answerButton2)) && results.get(1)) this.rightSelection = true;
		else if ((b.equals(this.answerButton3)) && results.get(2)) this.rightSelection = true;
		else this.rightSelection = false;
    }

    public void checkAnswer(ActionEvent event) throws IOException {
    	Button b = (Button)event.getSource();
		if (b.equals(this.checkButton) && this.rightSelection) 
			this.nextButton.setDisable(false);
    }	
	
}