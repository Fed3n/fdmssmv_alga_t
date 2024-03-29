package application;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class HeapSimulController {

    @FXML
    private Pane vectorPane;
    
    
    //Tutti i livelli in cui possono trovarsi nodi dell'albero (max 4 = 15 elementi)
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
    
    @FXML
    private ChoiceBox<String> maxMinChoiceBox;
    
    private final Integer MAX_VECTOR_SIZE = 15;
    
    private ArrayList<Integer> dataVector;
    
    private ArrayList<StackPane> nodeVector;

	public void initialize() {
		this.dataVector = new ArrayList<Integer>();
		this.maxMinChoiceBox.getItems().add("MaxHeap");
		this.maxMinChoiceBox.getItems().add("MinHeap");
	}
	
	public void drawVector() {
		this.vectorHbox.getChildren().clear();	//Prima rimuovo il vettore gi� disegnato
		
		//Itero sulla lista e per ogni membro creo uno slot del vettore
		Iterator<Integer> vectorIterator = this.dataVector.iterator();
		while(vectorIterator.hasNext()) {
			Integer num = vectorIterator.next();	
			
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
	
	//Aggiunge un elemento al vettore
	public void addToVector() {
		//Controlla che non vada oltre la dimensione massima
		if (this.dataVector.size() < this.MAX_VECTOR_SIZE) {
			
			try {
				Integer num = Integer.parseInt(this.inputArea.getText());	//Prende l'input dalla inputArea
				this.dataVector.add(num);									//Aggiunge al campo del vettore
				this.drawVector();											//Aggiorna la grafica del vettore
			} catch (NumberFormatException e) {				
			}
			this.inputArea.clear();			
		}		
	}
	
	public void removeFromVector() {
		if (this.dataVector.size() > 0)
			this.dataVector.remove(this.dataVector.size()-1);			//Rimuove ultimo elemento dal vettore
			this.drawVector();
	}
	
	public void generateHeap() {
		if(this.maxMinChoiceBox.getValue() != null) {
			if(this.maxMinChoiceBox.getValue() == "MaxHeap") {
				MaxHeap buildHeap = new MaxHeap();
				this.dataVector = buildHeap.maxHeapBuild(this.dataVector);
			}
			else {
				MinHeap buildHeap = new MinHeap();
				this.dataVector = buildHeap.minHeapBuild(this.dataVector);
			}
			
			this.drawVector();
			this.drawTree();	
		}
	}
	
	public void drawTree() {
		//Prima rimuovo l'albero gi� disegnato
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
			
			//Aggiungo all'HBox relativo al livello dell'albero a seconda della loro posizione nel vettore
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
			
			//Disegno la linea tra un nodo e suo padre
			
			if(index != 0) {
				StackPane node = this.nodeVector.get((index-1)/2);
				
				//Queste sono conversioni coordinate pane > coordinate scena > coordinate pane a livello pi� alto
				Bounds circle1ToScene = nodePane.localToScene(nodePane.getBoundsInLocal());
				Bounds circle1ToTreePane = this.treePane.sceneToLocal(circle1ToScene);
				
				Bounds circle2ToScene = node.localToScene(node.getBoundsInLocal());
				Bounds circle2ToTreePane = this.treePane.sceneToLocal(circle2ToScene);
				
				Point2D circle1Center = new Point2D((circle1ToTreePane.getMinX() + nodeCircle.getRadius()*2), circle1ToTreePane.getMinY() + nodeCircle.getRadius()*3);
				Point2D circle2Center = new Point2D(circle2ToTreePane.getMinX() + nodeCircle.getRadius()*2, circle2ToTreePane.getMinY() + nodeCircle.getRadius()*3);
				
				
				Line line = new Line(circle1Center.getX(), circle1Center.getY(), circle2Center.getX(), circle2Center.getY());
				line.setStroke(Color.BLACK);
				this.treePane.getChildren().add(line);
				
				
			}
			
			
			index++;
		}
		
	}
	
	

}
