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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
    
    private Stage lessonStage;  //stage of the lesson
    
    public Questions(Stage lessonStage, LessonController lessController) throws IOException {
    	this.lessonStage = lessonStage;
    	this.lessonController = lessController;
    	this.completed = false;
    	this.setQuestionNumber();
    	this.maxScore = this.questionsNumber*this.MAX_POINT_FOR_ONE_QUESTION;
    	this.loadQuestion(1); 
    	this.score = 0;
    	this.lastQuestionLoaded = 1;
    }
    
    //la funzione seguente carica la domanda richiesta modificando lo stage
    //postcondition: se la domanda non è presente lancia un'eccezione
    public void loadQuestion(Integer questionNumber) {
    	
    	try {
    		QuestionController controller = new QuestionController(this, questionNumber, this.questionsNumber == questionNumber);
			
		   	FXMLLoader loader = new FXMLLoader(getClass().getResource("Question.fxml"));
		   	loader.setController(controller);
			Parent root = loader.load();
	    	Scene scene = new Scene(root);
	
	    	this.lessonStage.setScene(scene);
	    	this.lessonStage.show();
	    	
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.WARNING, "Cannot find file ./lesson"+this.getLessonNumber()+"/question_"+questionNumber+" . Unable to"
					+" load the question.");
			alert.showAndWait();
			e.printStackTrace();
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
    
    public void nextQuestion(ActionEvent nextPressed, Integer points, QuestionController controller) throws IOException {
    	this.score += points;  
    	this.loadQuestion(controller.getQuestionNumber()+1); 
    }
    
    public void prevQuestion(ActionEvent prevPressed, Integer points, QuestionController controller) throws IOException {
    	this.score += points; 
    	this.loadQuestion(controller.getQuestionNumber()-1); 
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
    	return this.lessonStage;
    }
   
    public Integer getLastQuestionLoaded() {
    	return this.lastQuestionLoaded;
    }
    
    public void setLastQuestionLoaded(Integer num) {
    	this.lastQuestionLoaded = num;
    }
}

