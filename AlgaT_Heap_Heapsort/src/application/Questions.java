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
	
    private Integer questionsNumber;
	    
    private Integer maxScore;	//punteggio massimo ottenibile
    
    private Integer score;
    
    private final Integer MAX_POINT_FOR_ONE_QUESTION = 5;  //questa costante deve coincidere con la costante 
    													   //POINT_FIRST_RIGHT_SELECTION presente in QuestionController
    
    private Boolean completed;  //true se si ha risposto a tutte le domande
    
    private Integer lastQuestionLoaded;   //ultima domanda mostrata
    
	private LessonController lessonController;  //controller della lezione precedente per ottenere il numero della lezione
    
    private Stage questionStage;  //stage 
    
    public Questions(Stage lessonStage, LessonController lessController) throws IOException {
    	this.questionStage = lessonStage;
    	this.lessonController = lessController;
    	this.completed = false;
    	this.setQuestionNumber();
    	this.maxScore = this.questionsNumber*this.MAX_POINT_FOR_ONE_QUESTION;
    	boolean exist = true;
    	this.loadQuestion(1,exist,true); 
    	if (!exist) System.out.println("Couldn't find ./lesson"+this.lessonController.getLessonNumber().toString()+"/question_1.txt. ");
    	this.score = 0;
    	this.lastQuestionLoaded = 1;
    }
    
    //la funzione seguente carica la domanda richiesta modificando lo stage
    //@param exists : ritorna false sono nel caso la domanda non sia presente
    //@param firstQuestion : true se la domanda è la prima domanda ad essere richiamata (non necessariamente sempre la domanda numero 1), false altrimenti 	
		   	
    public void loadQuestion(Integer questionNumber, Boolean exists, Boolean firstCall) throws IOException {
    	
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
	
	    	this.questionStage.setScene(scene);
	    	this.questionStage.show();
	    	
    	} catch (Exception e) {
    		exists = false;
		}
    }
    
    //metodo per settare dinamicamente la variabile di classe : numero di domande
    //il metodo cerca di caricare i file richiesti fino a che non finiscono e viene lanciata un'eccezione
    public void setQuestionNumber() {
    	Integer i = 1;
    	Boolean presence = true;
    	while (presence) {
    	   	try {
    	   		BufferedReader reader = Files.newBufferedReader(Paths.get("./lesson"+this.lessonController.getLessonNumber().toString()
    	   		+"/question_"+i.toString()+".txt"), StandardCharsets.UTF_8);
    	   		i++;
    	   	} catch (Exception e) {
    	   		presence = false;
    	   	}
    	}
    	this.questionsNumber = i-1;
    }
    
    //il parametro booleano mi informa nel caso in cui i punti siano già stati assegnati
    public void nextQuestion(ActionEvent nextPressed, Integer points, QuestionController controller) throws IOException {
    	this.score += points;  
    	this.loadQuestion(controller.getQuestionNumber()+1, true, false); //non inserisco una variabile booleana perchè non mi interessa sapere se questa domanda è l'ultima
    }
    
    public void prevQuestion(ActionEvent prevPressed, Integer points, QuestionController controller) throws IOException {
    	this.score += points; 
    	this.loadQuestion(controller.getQuestionNumber()-1, true, false); //non inserisco una variabile booleana perchè non mi interessa sapere se questa domanda è l'ultima
    }
    
    //metodo necessario per il caso in cui non devo chiamare next o prev question ma ho necessità di memorizzare il punteggio
    public void addPoints(Integer value) {
    	this.score += value;
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
    
    public void setCompleted(Boolean completed) {
    	this.completed = completed;
    }
    
    public Integer getLessonNumber() {
    	return this.lessonController.getLessonNumber();
    }
    
    public LessonController getLessonController() {
    	return this.lessonController;
    }

    public Integer getQuestionsNumber() {
    	return this.questionsNumber;
    }
    
    public Stage getQuestionStage() {
    	return this.questionStage;
    }
    
    public Integer getLastQuestionLoaded() {
    	return this.lastQuestionLoaded;
    }
    
    public void setLastQuestionLoaded(Integer num) {
    	this.lastQuestionLoaded = num;
    }
}

