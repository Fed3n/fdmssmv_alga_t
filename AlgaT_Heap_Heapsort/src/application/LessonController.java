package application;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class LessonController { 
	
	@FXML
	private Label titleLabel;  

    @FXML
    private TextArea lessonText;

    @FXML
    private Button prevTextButton;

    @FXML
    private Button nextTextButton;

    @FXML
    private Button backToMenuButton;

    @FXML
    private Button simulButton;

    @FXML
    private Button questionButton;
    
    private String lessonTitle;
    
    private ArrayList<String> lessonTextList = new ArrayList<String>();
    private String fileGetter;
    
    private	Integer textNumber;		//Indica a che pagina della lezione ci si trova
    private final int MAX_LESSON_NUMBER;

    //Il metodo viene chiamato appena viene caricato il file FXML corrispondente
	public void initialize() {
		this.titleLabel.setText(lessonTitle);
		this.textNumber = 1;
		this.lessonText.setEditable(false);		//Disabilita la scrittura sull'area di testo in cui si legge la lezione
		this.lessonText.setWrapText(true);
		this.prevTextButton.setDisable(true);
		try {
			BufferedReader reader = Files.newBufferedReader(Paths.get("./"+ this.fileGetter + "_" + this.textNumber.toString() + ".txt"), StandardCharsets.UTF_8);
			String text = "";
			String line;
			//Aggiungo una riga alla volta alla stringa finale, dato che readLine legge solo una riga
			while((line = reader.readLine()) != null) {
				text = text + line + "\n";
			}			
			this.lessonText.setText(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public LessonController(String lessonName, Integer numberOfLessons, String lessonFileName) {
		this.lessonTitle = lessonName;
		this.MAX_LESSON_NUMBER = numberOfLessons;
		this.fileGetter = lessonFileName;
	}
	
	public void goBackToMenu(ActionEvent backPressed) throws IOException {
		Parent mainMenuParent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
    	Scene mainMenuScene = new Scene(mainMenuParent);
    	
    	Stage window = (Stage)((Node)backPressed.getSource()).getScene().getWindow();
    	
    	window.setScene(mainMenuScene);
    	window.show();		
	}
	
	public void goToSimulation(ActionEvent simulPressed) throws IOException {
		Parent heapSimulParent = FXMLLoader.load(getClass().getResource("HeapSimul.fxml"));
    	Scene heapSimulScene = new Scene(heapSimulParent);
    	
    	Stage window = (Stage)((Node)simulPressed.getSource()).getScene().getWindow();
    	
    	window.setScene(heapSimulScene);
    	window.show();		
	}
	
	//Ricarica la pagina usando i parametri di textNumber
	public void reloadPage() {
		if (this.textNumber == 1)
			this.prevTextButton.setDisable(true);
		else
			this.prevTextButton.setDisable(false);
		
		if(this.textNumber == MAX_LESSON_NUMBER)
			this.nextTextButton.setDisable(true);
		else
			this.nextTextButton.setDisable(false);
		
		//TODO Disabilitare simul in alcune istanze
		
		try {
			BufferedReader br = Files.newBufferedReader(Paths.get("./lesson1_" + this.textNumber.toString() + ".txt"), StandardCharsets.UTF_8);
			String text = "";
			String line;
			while((line = br.readLine()) != null) {
				text = text + line + "\n";
			}			
			this.lessonText.setText(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void nextText() {
		this.textNumber++;
		this.reloadPage();
	}
	
	public void prevText() {
		this.textNumber--;
		this.reloadPage();
	}
	
	
	
	
	

}
