package application;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class HeapLessonController implements Initializable{ 

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
    
    private	Integer textNumber;		//Indica a che pagina della lezione ci si trova
    private final int MAX_LESSON_NUMBER = 3;

    //Il metodo viene chiamato appena viene caricato il file FXML corrispondente
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.textNumber = 1;
		this.lessonText.setEditable(false);		//Disabilita la scrittura sull'area di testo in cui si legge la lezione
		this.lessonText.setWrapText(true);
		this.prevTextButton.setDisable(true);
		try {
			String text = new String(Files.readAllBytes(Paths.get("./lesson1_" + this.textNumber.toString() + ".txt")));
			this.lessonText.setText(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void goBackToMenu(ActionEvent backPressed) throws IOException {
		Parent mainMenuParent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
    	Scene mainMenuScene = new Scene(mainMenuParent);
    	
    	Stage window = (Stage)((Node)backPressed.getSource()).getScene().getWindow();
    	
    	window.setScene(mainMenuScene);
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
			String text = new String(Files.readAllBytes(Paths.get("./lesson1_" + this.textNumber.toString() + ".txt")));
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
