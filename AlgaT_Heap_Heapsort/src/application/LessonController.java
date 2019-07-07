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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
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

    private Questions questionObject = null;
    private Boolean lessonCompleted = false;  
    private Boolean questionAlertShown = false; //variabile che utilizzo per mostrare solo una volta la finestra che 
    											//avverte di non poter tornare indietro dopo aver visto le domande

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
		this.lessonText.setEditable(false);		//Disabilita la scrittura sull'area di testo in cui si legge la lezione
		this.lessonText.setWrapText(true);
		if (!this.lessonCompleted) this.questionButton.setDisable(true);

		this.reloadPage();
	}

	//Importante accertarsi che il numero delle lezioni corrisponda al numero di file text e al numero di righe dentro simulLocation.txt nella cartella corrispondente
	//Prende in input titolo della lezione, numero delle parti della lezione e nome della cartella in ./ dove si trovano i file
	//Il numero delle parti della lezione deve essere >=1
	public LessonController(String lessonName, Integer numberOfLessons, String lessonFileName) {
		this.lessonTitle = lessonName;
		this.MAX_LESSON_NUMBER = numberOfLessons;
		this.fileGetter = lessonFileName;
		this.textNumber = 1;
	}

	//se il metodo ritorna zero c'è un errore nel nome delle cartelle
	public Integer getLessonNumber() {
		Integer n = 0;
		try {
			n = Integer.parseInt(this.fileGetter.substring(6));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			System.out.print("The name of some lesson's folder is incorrect.");
		}
		return n;
	}

	public void goBackToMenu(ActionEvent backPressed) throws IOException {

		//ottengo la finestra della lezione dall'evento
		Stage thisStage = (Stage)((Node)backPressed.getSource()).getScene().getWindow();
		//riprendo il controller del mainMenù dalla stage, dove lo avevo memorizzato, con il metodo getUserData
		MainMenuController controller = (MainMenuController)thisStage.getUserData();
		//aggiorno nel MainMenùController il controller di questa lezione per non perdere eventuali modifiche
		controller.upgradeLessons(this); 
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
		//setto il controller che ho memorizzato, per ristabilire la situazione precedente
		loader.setController(controller);
		Parent mainMenuParent = (Parent)loader.load();			
		Scene mainMenuScene = new Scene(mainMenuParent);
    	thisStage.setScene(mainMenuScene);
    	thisStage.show();
	}

	public void goToSimulation(ActionEvent simulPressed) throws IOException {
		//Parent simulParent = FXMLLoader.load(getClass().getResource("VectorToTreeSimul.fxml"));	//Se volete provare una simulazione velocemente usate questo
		Parent simulParent = FXMLLoader.load(getClass().getResource(this.simulLocationList.get(this.textNumber-1)));	//Va a prendere il nome dell'fxml a cui accedere dal vettore

    	Scene simulScene = new Scene(simulParent);

    	Stage window = (Stage)((Node)simulPressed.getSource()).getScene().getWindow();

    	window.setScene(simulScene);
    	
    	//utilizzo il metodo setUserData che mi permette di incamerare un oggetto nella stage, in questo
    	//modo quando dovrò tornare indietro potrò avere facilmente l'oggetto attuale
    	simulScene.setUserData(this);
    	
    	window.show();
	}

	//Ricarica la pagina usando i parametri di textNumber
	public void reloadPage() {		
		//Controllo se sono all'inizio o alla fine della lezione e accendo/spengo i bottoni per avanzare o retrocedere
		if (this.textNumber == 1)
			this.prevTextButton.setDisable(true);
		else this.prevTextButton.setDisable(false);

		if(this.textNumber == MAX_LESSON_NUMBER) {
			this.nextTextButton.setDisable(true);
			this.questionButton.setDisable(false);
			this.lessonCompleted = true;
		} else this.nextTextButton.setDisable(false);

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

	public void goToQuestions(ActionEvent questionPressed) throws IOException {
		
		if (!this.questionAlertShown) {
			this.questionAlertShown = true;
			this.openAlertQuestion(questionPressed);
			
		} else {
			if (this.questionObject == null) {
				Stage thisStage = (Stage)((Node)questionPressed.getSource()).getScene().getWindow();
				this.questionObject = new Questions(thisStage,this);
			}
			else {
				Integer questionNumber = this.questionObject.getLastQuestionLoaded();
				this.questionObject.loadQuestion(questionNumber);
				}
		}
	}
	
	public void openAlertQuestion(ActionEvent event) throws IOException {
		
		Alert alert = new Alert(AlertType.CONFIRMATION, "Dopo aver visto la prima domanda non ti sarà "
				+ "più possibile accedere alle lezioni fino a che non avrai completato tutte le domande."
				+ " Assicurati di aver letto attentamente la parte della lezione prima di procedere."
				, ButtonType.OK, ButtonType.CANCEL);
		alert.setTitle("Warning");
		alert.setHeaderText("Attenzione!");
		alert.showAndWait();
		
		if (alert.getResult() == ButtonType.OK) {
			this.goToQuestions(event);
		}
	}
}
