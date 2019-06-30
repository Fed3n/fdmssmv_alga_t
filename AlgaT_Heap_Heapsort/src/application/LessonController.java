package application;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    
    @FXML
    private ImageView image;

    private String lessonTitle;

    private ArrayList<String> simulLocationList;
    private String fileGetter;

    private	Integer textNumber;		//Indica a che pagina della lezione ci si trova
    private final int MAX_LESSON_NUMBER;
    
    private Questions questionObject;
    private Boolean completedQuestion = false;

    //Il metodo viene chiamato appena viene caricato il file FXML corrispondente
	public void initialize() {
		this.simulLocationList = new ArrayList<String>();
		//Inizializzo una lista di file fxml in cui trovare le simulazioni per ogni parte della lezione, se la parte non ha simulazione inserire "null" nella riga di file
		try {
			Scanner scanner = new Scanner(new File("./" + this.fileGetter + "/simulLocation.txt"));
			while(scanner.hasNext()) {
				this.simulLocationList.add(scanner.next());
			}
			scanner.close();
		} catch (FileNotFoundException e1) {
			System.out.println("Couldn't find simulLocation.txt inside the directory" + this.fileGetter);
		}
		this.titleLabel.setText(lessonTitle);
		this.textNumber = 1;
		this.lessonText.setEditable(false);		//Disabilita la scrittura sull'area di testo in cui si legge la lezione
		this.lessonText.setWrapText(true);

		this.reloadPage();
	}

	//Importante accertarsi che il numero delle lezioni corrisponda al numero di file text e al numero di righe dentro simulLocation.txt nella cartella corrispondente
	//Prende in input titolo della lezione, numero delle parti della lezione e nome della cartella in ./ dove si trovano i file
	//Il numero delle parti della lezione deve essere >=1
	public LessonController(String lessonName, Integer numberOfLessons, String lessonFileName) {
		this.lessonTitle = lessonName;
		this.MAX_LESSON_NUMBER = numberOfLessons;
		this.fileGetter = lessonFileName;
	}
	
	//se il metodo ritorna zero c'è un errore nel nome delle cartelle
	private Integer getLessonNumber() {
		Integer n = 0;
		try {
			n = Integer.parseInt(this.fileGetter.substring(6));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.out.print("The name of some lesson's folder are incorrect.");
		}
		return n;
	}

	public void goBackToMenu(ActionEvent backPressed) throws IOException {

		
		Parent mainMenuParent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
    	Scene mainMenuScene = new Scene(mainMenuParent);

    	Stage window = (Stage)((Node)backPressed.getSource()).getScene().getWindow();

    	window.setScene(mainMenuScene);
    	window.show();
	}

	public void goToSimulation(ActionEvent simulPressed) throws IOException {
		//Parent simulParent = FXMLLoader.load(getClass().getResource("VectorToTreeSimul.fxml"));	//Se volete provare una simulazione velocemente usate questo
		Parent simulParent = FXMLLoader.load(getClass().getResource(this.simulLocationList.get(this.textNumber-1)));	//Va a prendere il nome dell'fxml a cui accedere dal vettore

    	Scene simulScene = new Scene(simulParent);

    	Stage window = (Stage)((Node)simulPressed.getSource()).getScene().getWindow();

    	window.setScene(simulScene);
    	window.show();
	}

	//Ricarica la pagina usando i parametri di textNumber
	public void reloadPage() {
		//Controllo se sono all'inizio o alla fine della lezione e accendo/spengo i bottoni per avanzare o retrocedere
		if (this.textNumber == 1)
			this.prevTextButton.setDisable(true);
		else this.prevTextButton.setDisable(false);

		if(this.textNumber == MAX_LESSON_NUMBER)
			this.nextTextButton.setDisable(true);
		else this.nextTextButton.setDisable(false);

		//Controllo se la parte della lezione ha una simulazione associata o meno, attivo/disattivo pulsante di conseguenza
		if(this.simulLocationList.get(this.textNumber-1).contentEquals("null")) {
			this.simulButton.setDisable(true);
		}
		else this.simulButton.setDisable(false);

		//Prelevo il testo dalla directory della lezione dal file previsto
		try {
			BufferedReader br = Files.newBufferedReader(Paths.get("./"+ this.fileGetter + "/text_" + this.textNumber.toString() + ".txt"), StandardCharsets.UTF_8);
			String text = "";
			String line;
			while((line = br.readLine()) != null) {
				text = text + line + "\n";
			}
			this.lessonText.setText(text);
		} catch (IOException e) {
			System.out.println("Couldn't find text_" + this.textNumber.toString() + " in the given directory");
		}
		
		Image img = new Image(new File("./"+ this.fileGetter + "/image_" + this.textNumber.toString() + ".png").toURI().toString());
		this.image.setImage(img);

	}

	public void nextText() {
		this.textNumber++;
		this.reloadPage();
	}

	public void prevText() {
		this.textNumber--;
		this.reloadPage();
	}
	
	public void setCompleted(Boolean completed) {
		this.completedQuestion = completed;
	}

	public void goToQuestions(ActionEvent questionPressed) throws IOException {
		if (!this.completedQuestion) 
			this.questionObject = new Questions(this.getLessonNumber(),questionPressed,this,this.completedQuestion);
		else {
			this.questionObject.loadQuestion(1, true, false, this.completedQuestion);
		}
	}

}
