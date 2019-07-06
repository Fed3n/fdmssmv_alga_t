package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ExplanationController {

    @FXML
    private Label textLabel;
    
    private String textValue;
    
    private Stage stage;
    
    public void initialize() {
    	this.textLabel.setText(this.textValue);
    }
    
    public ExplanationController(String text) {
    	this.textValue = text;
    }
    
    public void setStage(Stage s) {
    	this.stage = s;
    	//questo settaggio di lunghezza massima del testo e calcolato in base alla dimensione del font.
    	if (this.textValue.length() > 715) 
    		this.stage.setHeight((double)380);
    }

}
