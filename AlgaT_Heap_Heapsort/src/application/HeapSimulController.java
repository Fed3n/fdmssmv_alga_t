package application;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class HeapSimulController {

    @FXML
    private Pane vectorPane;
    
    
    //Tutti i pane in cui può essere generato un nodo dell'albero
    @FXML
    private Pane treePane;
    
    @FXML
    private HBox treeLevel0;
    
    @FXML
    private HBox treeLevel1;

    @FXML
    private HBox treeLevel2;
    
    @FXML
    private HBox treeLevel3;

    @FXML
    private HBox vectorHbox;

    @FXML
    private TextField inputArea;

    @FXML
    private Button addButton;

    @FXML
    private Button removeButton;
    
    @FXML
    private Button generateButton;
    
    private final Integer MAX_VECTOR_SIZE = 15;
    
    private ArrayList<Integer> dataVector;
    
    private ArrayList<StackPane> nodeVector;

	public void initialize() {
		this.dataVector = new ArrayList<Integer>();
	}
	
	public void drawVector() {
		this.vectorHbox.getChildren().clear();	//Prima rimuovo il vettore già disegnato
		
		//Itero sulla lista e per ogni membro creo uno slot del vettore
		Iterator<Integer> vectorIterator = this.dataVector.iterator();
		while(vectorIterator.hasNext()) {
			Integer num = vectorIterator.next();
			System.out.println(num.toString());		
			
			StackPane numPane = new StackPane();
			Label numLabel = new Label();
			numLabel.setText(num.toString());
			Rectangle numBox = new Rectangle(30,30);
			numBox.setFill(Color.WHITE);
			numBox.setStroke(Color.BLACK);
					
			numPane.getChildren().add(numBox);
			numPane.getChildren().add(numLabel);
			this.vectorHbox.getChildren().add(numPane);
			
			//TODO Eventuali eventi relativi all'interazione con lo slot del vettore
		}
		
		
	}
	
	public void addToVector() {
		if (this.dataVector.size() < this.MAX_VECTOR_SIZE) {
			
			try {
				Integer num = Integer.parseInt(this.inputArea.getText());
				this.dataVector.add(num);
				this.drawVector();
			} catch (NumberFormatException e) {
				System.out.println("Please input a number");				
			}
			
		}		
	}
	
	public void generateHeap() {
		MaxHeap buildHeap = new MaxHeap();
		this.dataVector = buildHeap.maxHeapBuild(this.dataVector);
		this.drawVector();
		this.drawTree();		
	}
	
	public void drawTree() {
		//Prima rimuovo l'albero già disegnato
		this.treeLevel0.getChildren().clear();
		this.treeLevel1.getChildren().clear();
		this.treeLevel2.getChildren().clear();
		this.treeLevel3.getChildren().clear();
		
		this.nodeVector = new ArrayList<StackPane>();	//Inizializzo la lista di nodi dell'albero
		Integer index = 0;
		
		//Itero sul vettore e disegno i suoi membri in ordine come albero binario partendo dalla radice
		Iterator<Integer> heapIterator = this.dataVector.iterator();
		while(heapIterator.hasNext()) {
			Integer num = heapIterator.next();
			
			StackPane nodePane = new StackPane();
			nodePane.prefWidth(40);
			Label numLabel = new Label();
			numLabel.setText(num.toString());
			Circle nodeCircle = new Circle(20);
			nodeCircle.setStroke(Color.BLUE);
			nodeCircle.setFill(Color.WHITE);
			
			nodePane.getChildren().add(nodeCircle);
			nodePane.getChildren().add(numLabel);
			this.nodeVector.add(nodePane);
			
			//TODO Eventuali eventi relativi all'interazione col nodo
			
			if(index == 0) {
				this.treeLevel0.getChildren().add(nodePane);
			}
			
			else if(index <= 2) {
				this.treeLevel1.getChildren().add(nodePane);
			}
			
			else if(index <= 6) {
				this.treeLevel2.getChildren().add(nodePane);
			}
			
			else {
				this.treeLevel3.getChildren().add(nodePane);
			}
			
			index++;
		}
		
	}
	
	

}
