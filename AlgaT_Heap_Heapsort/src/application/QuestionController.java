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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    
    @FXML
    private Button backToLessonButton;
    
    @FXML
    private Button explanationButton;    
    
    @FXML
    private Label resultLabel;
    
    @FXML
    private Label pointsLabel;
    
    @FXML
    private ProgressBar progressBar;
    
    //costante non modificabile (a meno che non si vada a cambiare il file fxml e a modificare la gestione delle risposte)
    private final int MAX_ANSWERS_NUMBER = 3; 
    
    private final Integer POINT_FIRST_RIGHT_SELECTION = 5;	//punteggio assegnato ad una risposta corretta al primo tentativo
    
    private final Integer POINT_SECOND_RIGHT_SELECTION = 2; 	//punteggio assegnato ad una risposta corretta al secondo tentativo
    
    //vettore contenente le risposte
    private ArrayList<String> answers = new ArrayList<String>() {{ add(null); add(null); add(null); }};
    
    //vettore contenente booleani relativi alle risposte
    private ArrayList<Boolean> results = new ArrayList<Boolean>() {{ add(false); add(false); add(false); }};
    
    //stringa che conterrà la spiegazione delle risposte
    private String explanation = null;
    
    //contenente parametri provenienti dall'esterno
    private Questions questionsObject;
    
    private Integer questionNumber;
    
    private Integer attemptsNumber = 0;  //numero di tentativi di risposta fatti 
       	
    private Boolean lastQuestion = false;  //true se è l'ultima domanda
    
    //la variabile segnala se la corrente selezione della risposta è esatta
    private Boolean rightSelection = false;
    
    //variabili booleane per la gestione dei vari casi per determinare se mostrare o meno la spiegazione
    private Boolean rightSelectionHasHappened = false;
    private Boolean allClicked = false;
    private Boolean oneClicked = false;
    private Boolean twoClicked = false;
    private Boolean threeClicked = false;
	
	public void initialize() throws Exception {
		this.textArea.setEditable(false);
		this.textArea.setWrapText(true);
		this.answerButton1.setWrapText(true);
		this.answerButton2.setWrapText(true);
		this.answerButton3.setWrapText(true);
		this.titleLabel.setText("Domanda n° "+this.questionNumber.toString());
		if (this.questionsObject.getCompleted()) this.progressBar.setVisible(false);
		try {
			BufferedReader reader = Files.newBufferedReader(Paths.get("./lesson"+this.questionsObject.getLessonNumber().toString()
								+"/question_"+this.questionNumber.toString()+".txt"), StandardCharsets.UTF_8);
			int i = 0;
			String line;
			String question = null;
			while ((line = reader.readLine()) != null){
				if (i == 0) //la prima riga contiene la domanda
					question = line;
				else if (i == 4) {
					this.explanation = line;
				} else {  //il primo carattere contiene (T/F) true o false relativo alla risposta e segue la domanda. Solo una risposta 
						//può essere quella corretta
					results.set(i-1, line.charAt(0) == 'T');
					answers.set(i-1, line.substring(1));
				}
				i++;
			}
				if (i > this.MAX_ANSWERS_NUMBER+2) //è presente la spiegazione ed inoltre una riga vuota che può scappare
					throw new IOException("Error in file ./lesson"+this.questionsObject.getLessonNumber().toString()+"/question_"+this.questionNumber.toString()+".txt. "
							+ "The file does not respect the maximum number of line allowed.");
			this.textArea.setText(question);
			this.answerButton1.setText(answers.get(0));
			this.answerButton2.setText(answers.get(1));
			this.answerButton3.setText(answers.get(2));
			this.setProgressBar();
			if (!this.questionsObject.getCompleted()) {
				this.prevButton.setVisible(false);
				this.nextButton.setDisable(true);
				this.pointsLabel.setVisible(false);
				this.explanationButton.setVisible(false);
				this.backToLessonButton.setDisable(true);
			} else {
				this.prevButton.setVisible(true);
				this.backToLessonButton.setDisable(false);
				if (this.questionNumber == 1) 
					this.prevButton.setDisable(true);
				else this.prevButton.setDisable(false);
				if (this.lastQuestion) this.nextButton.setDisable(true);
				else this.nextButton.setDisable(false);
				this.checkButton.setDisable(true);
				this.answerButton1.setDisable(true);
				this.answerButton2.setDisable(true);
				this.answerButton3.setDisable(true);
				this.setRightSelection();
				this.pointsLabel.setVisible(true);
				this.pointsLabel.setText("Punteggio raggiunto: "+this.questionsObject.getScore().toString()+"/"+this.questionsObject.getMaxScore().toString());
				this.explanationButton.setVisible(true);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.print(" .Couldn't find question_"+this.questionNumber.toString()+".txt in the given directory");
		}
	} 
	
	public QuestionController(Questions quest, Integer questNum, Boolean last) {
		this.questionsObject = quest;
		this.questionNumber = questNum;
		this.lastQuestion = last;
	}
	
	private void setProgressBar() {
		this.progressBar.setProgress((double)1/this.questionsObject.getQuestionsNumber()*questionNumber);
	}
	
    public void AnswerSelected(ActionEvent event) {
    	this.resultLabel.setText(null);
		RadioButton b = (RadioButton)event.getSource();
		if ((b.equals(this.answerButton1)) && results.get(0)) this.rightSelection = true;
		else if ((b.equals(this.answerButton2)) && results.get(1)) this.rightSelection = true;
		else if ((b.equals(this.answerButton3)) && results.get(2)) this.rightSelection = true;
		else this.rightSelection = false;
    }

    public void checkAnswer(ActionEvent event) throws IOException {
    	if (this.radioButtons.getSelectedToggle() != null) {
	    	//aggiorno i booleani della selezione
	    	if (this.answerButton1.isSelected()) this.oneClicked = true;
	    	if (this.answerButton2.isSelected()) this.twoClicked = true;
	    	if (this.answerButton3.isSelected()) this.threeClicked = true;
	    	this.allClickedText();
	    	if (this.rightSelection) {
	    		if (this.attemptsNumber >= 2 && this.allClicked && !this.rightSelectionHasHappened) //mostro la spiegazione all'ultimo tentativo di risposta. in realtà il tentativo sarebbe il numero tre ma lo incremento successivamente
	    			this.explanationButton.setVisible(true);  
				if (!this.lastQuestion) {
					this.nextButton.setDisable(false);
					this.resultLabel.setTextFill(Color.GREEN);
					this.resultLabel.setText("Risposta Corretta!");
					this.attemptsNumber++;
				} else {
					this.resultLabel.setTextFill(Color.BLUE);
					this.resultLabel.setText("Risposta Corretta! Hai terminato con successo le domande");
					this.attemptsNumber++;
					this.questionsObject.addPoints(this.getPoints());
					this.questionsObject.setCompleted(true);
					this.questionsObject.loadQuestion(this.questionNumber, true, false, this.questionsObject.getCompleted());
				}
				this.rightSelectionHasHappened = true;
			} else {
				this.nextButton.setDisable(true);
				this.resultLabel.setTextFill(Color.RED);
				this.resultLabel.setText("Risposta Errata, Ritenta.");
				this.attemptsNumber++;
			}
    	} else {
    		this.resultLabel.setTextFill(Color.RED);
    		this.resultLabel.setText("Nessuna risposta selezionata");
    	}
    	if (!this.questionsObject.getCompleted()) this.backToLessonButton.setDisable(true);
    }	
    
    private void allClickedText() {
    	if (this.oneClicked && this.twoClicked && this.threeClicked) this.allClicked = true;
    }
    
    public void setRightSelection() {
		this.resultLabel.setTextFill(Color.GREEN);
		this.resultLabel.setText("Risposta Corretta!");
		if (this.results.get(0)) this.answerButton1.setSelected(true);
		if (this.results.get(1)) this.answerButton2.setSelected(true);
		if (this.results.get(2)) this.answerButton3.setSelected(true);
    }
    
    //ritorna il punteggio ottenuto
    public Integer getPoints() {
    	Integer points = 0;
    	if (this.attemptsNumber == 2) points = POINT_SECOND_RIGHT_SELECTION;
    	if (this.attemptsNumber == 1) points = POINT_FIRST_RIGHT_SELECTION;
    	return points;
    }
    
    public Integer getMaxPoint() {
    	return this.POINT_FIRST_RIGHT_SELECTION;
    }
    
    public Integer getQuestionNumber() {
    	return this.questionNumber;
    }
    
	//la funzione non verrà chiamata se questa lezione è la prima perchè il bottone sarà disattivato
    public void goToPrevQuestion(ActionEvent event) throws IOException {
    	Integer points = 0;
    	if (this.rightSelection) points = this.getPoints();
    	this.questionsObject.prevQuestion(event, points, this);
    }
    
    //se questa sarà l'ultima funzione il bottone sarà disabilitato
    public void goToNextQuestion(ActionEvent event) throws IOException {
    	Integer points = 0;
    	if (this.rightSelection) points = this.getPoints();
    	this.questionsObject.nextQuestion(event, points, this);
    }    
    
    public void goToLesson(ActionEvent backToLessonPressed) throws IOException {
    	
    	this.questionsObject.getLessonController().setLastQuestionLoaded(this.questionNumber);
    	
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Lesson.fxml"));
		
		loader.setController(this.questionsObject.getLessonController());
		
		Parent heapLessonParent = (Parent)loader.load();
	
    	Scene heapLessonScene = new Scene(heapLessonParent);
    	
    	Stage window = (Stage)((Node)backToLessonPressed.getSource()).getScene().getWindow();
    	
    	window.setScene(heapLessonScene);
    	window.show();
    	
    }
    
    public void openExplanation(ActionEvent e) throws IOException {
    	
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Explanation.fxml"));
		ExplanationController controller = new ExplanationController(this.explanation);
		loader.setController(controller);
		Parent explanationParent = (Parent)loader.load();
		Scene explanationScene = new Scene(explanationParent);
		Stage explanationWindow = new Stage();
		explanationWindow.initOwner(this.questionsObject.getQuestionStage());
		explanationWindow.initModality(Modality.WINDOW_MODAL);
		explanationWindow.setTitle("spiegazione risposta corretta");
		explanationWindow.setScene(explanationScene);
		controller.setStage(explanationWindow);
		//reimposto la dimensione della stage in base al testo contenuto
		explanationWindow.show();
    }
}