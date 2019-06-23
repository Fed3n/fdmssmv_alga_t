package application;
import java.io.IOException;
import java.net.URL;
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

public class HeapsortLessonController implements Initializable{

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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.lessonText.setEditable(false);
		
	}
	
	public void goBackToMenu(ActionEvent backPressed) throws IOException {
		Parent mainMenuParent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
    	Scene mainMenuScene = new Scene(mainMenuParent);
    	
    	Stage window = (Stage)((Node)backPressed.getSource()).getScene().getWindow();
    	
    	window.setScene(mainMenuScene);
    	window.show();		
	}

}
