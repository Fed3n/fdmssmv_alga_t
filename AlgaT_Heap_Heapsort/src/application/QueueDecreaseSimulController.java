package application;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class QueueDecreaseSimulController extends HeapSimul {

    @FXML
    private Label vectorLabel;

    @FXML
    private Button readyButton;

    @FXML
    private TextArea infoText;
    
    @FXML
    private TextArea inputArea;

    @FXML
    private Button decreaseButton;

    @FXML
    private Button prevButton;

    @FXML
    private Button nextButton;
    
    @FXML
    private Button priorityButton;
    
	protected ArrayList<ArrayList<Integer>> statusList;		//Lista di stati del vettore durante l'operazione
	
	protected ArrayList<String> instructionList;			//Lista di istruzioni da inserire
	
	protected ArrayList<ArrayList<Integer>> lightableIndex;	//Lista dei nodi da illuminare ad ogni passaggio sequenziale
	
	private Boolean treeDrawn;
	
	

	public void initialize() {
		super.initialize();
		this.infoText.setEditable(false);
		this.inputArea.setEditable(false);
		this.infoText.setWrapText(true);	
		this.nextButton.setDisable(true);
		this.prevButton.setDisable(true);
		this.priorityButton.setDisable(true);
		this.infoText.setText("Premi su genera per creare un vettore composto da numeri casuali.");
		this.instructionList = new ArrayList<String>();
		this.lightableIndex = new ArrayList<ArrayList<Integer>>();
		this.treeDrawn = false;
	}

	public void addToVector(ActionEvent event) {
		this.statusList.add(super.randomVector());
		super.drawVector();
		super.isGenerated = true;
		this.infoText.setText("Premi su Ready per generare l'albero relativo al vettore.");
	}
	
    public void readyVector(ActionEvent event) {
    	if (super.isGenerated) {
    		super.drawTree();
    		this.treeDrawn = true;
    		this.infoText.setText("Ora seleziona un nodo dell'albero, inserisci di quanto vuoi decrementare la priorità" 
    				+ " del nodo selezionato nella casella affianco a 'Select' e premi su Decrease.");
    	} else 
    		this.infoText.setText("E' necessario creare il vettore prima di disegnare l'albero");
    }
    
	public void priorityValue(ActionEvent event){
		
	}
	
    public void generateSteps(ActionEvent event) {

    }

    public void nextStatus(ActionEvent event) {

    }
    
    public void prevStatus(ActionEvent event) {

    }
    
    public void removeFromVector(ActionEvent event) {

    }
}
