package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ExplanationController {

    @FXML
    private Label textLabel;
    
    private String textValue = null;
    
    public void initialize() {
    	this.textLabel.setText(this.textValue);
    }
    
    public ExplanationController(String text) {
    	this.textValue = text;
    }

}
