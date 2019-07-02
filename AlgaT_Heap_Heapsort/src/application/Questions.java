package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Questions {
	
	private LessonController lessonController;
	
	private ActionEvent questionEvent;
    
    private Stage questionStage;
    
    private Integer maxScore;	//punteggio massimo ottenibile
    
    private Integer score;
    
    private Boolean completed;
    
    public Questions(ActionEvent event, LessonController lessController, Boolean complete) throws IOException {
    	this.questionEvent = event;
    	this.lessonController = lessController;
    	this.completed = complete;
    	boolean exist = true;
    	this.loadQuestion(1,exist,true,complete); 	
    	if (!exist) System.out.println("Couldn't find ./lesson"+this.lessonController.getLessonNumber().toString()+"/question_1.txt. ");
    	this.score = 0;
    	this.maxScore = 5;  //per ora tengo conto solamente della prima domanda. Il punteggio è riferito alla costante presente nel controller
    }
    
    //la funzione seguente carica la domanda richiesta modificando lo stage
    //@param exists : ritorna false sono nel caso la domanda non sia presente
    //@param firstQuestion : true se la domanda è la prima domanda ad essere richiamata (non necessariamente sempre la domanda numero 1), false altrimenti 	
		   	
    public void loadQuestion(Integer questionNumber, Boolean exists, Boolean firstCall, Boolean complete) throws IOException {
    	
    	//verifico se la domanda è presente caricando il file. Se il caricamento del file fallisce significa che non c'è.
    	exists = true;
    	
    	try {
			BufferedReader reader = Files.newBufferedReader(Paths.get("./lesson"+this.lessonController.getLessonNumber().toString()
			+"/question_"+questionNumber.toString()+".txt"), StandardCharsets.UTF_8);

			//ora verifico se la domanda è l'ultima (devo passarlo come parametro al controller) cercando la successiva
			Boolean isLast = false;
			
		   	try {
		   	    Integer nextQuestionNumber = questionNumber+1;
		   		BufferedReader reader2 = Files.newBufferedReader(Paths.get("./lesson"+this.lessonController.getLessonNumber().toString()
		   		+"/question_"+nextQuestionNumber.toString()+".txt"), StandardCharsets.UTF_8);
		   					
		   	} catch (Exception e) {
		   		isLast = true;
		   	}
		   		
		   	QuestionController controller = new QuestionController(this, questionNumber, isLast);
			
		   	FXMLLoader loader = new FXMLLoader(getClass().getResource("Question.fxml"));
		   	loader.setController(controller);
			Parent root = loader.load();
			
	    	Scene scene = new Scene(root);
	
	    	//se sono alla prima chiamata devo creare l'oggetto stage, se invece sono nella successive chiamate è sufficiente impostare la scena
	    	if (firstCall) this.questionStage = (Stage)((Node)this.questionEvent.getSource()).getScene().getWindow();
	    	this.questionStage.setScene(scene);
	    	this.questionStage.show();
	    	
		   	//se la domanda c'è considero anche il punteggio di questa domanda all'interno del punteggio massimo
	    	if (!this.completed) this.maxScore = this.maxScore+controller.getMaxPoint();
	    	
    	} catch (Exception e) {
    		exists = false;
		}
    }
    
    //il parametro booleano mi informa nel caso in cui i punti siano già stati assegnati
    public void nextQuestion(ActionEvent nextPressed, Integer points, QuestionController controller) throws IOException {
    	this.score += points;  
    	this.loadQuestion(controller.getQuestionNumber()+1, true, false, this.completed); //non inserisco una variabile booleana perchè non mi interessa sapere se questa domanda è l'ultima
    }
    
    public void prevQuestion(ActionEvent prevPressed, Integer points, QuestionController controller) throws IOException {
    	this.score += points; 
    	this.loadQuestion(controller.getQuestionNumber()-1, true, false, this.completed); //non inserisco una variabile booleana perchè non mi interessa sapere se questa domanda è l'ultima
    }
    
    //metodo necessario per il caso in cui non devo chiamare next o prev question ma ho necessità di memorizzare il punteggio
    public void addPoints(Integer value) {
    	this.score += value;
    }
    
    public void setCompleted(Boolean completed) {
    	this.completed = completed;
    	this.lessonController.setCompleted(this.completed);
    }
    
    public Integer getScore() {
    	return this.score;
    }
    
    public Integer getMaxScore() {
    	return this.maxScore;
    }
    
    public Boolean getCompleted() {
    	return this.completed;
    }
    
    public Integer getLessonNumber() {
    	return this.lessonController.getLessonNumber();
    }
    
    public LessonController getLessonController() {
    	return this.lessonController;
    }

}
