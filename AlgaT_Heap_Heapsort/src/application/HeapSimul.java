package application;

import java.util.Random;
import java.util.ArrayList;
import java.util.Iterator;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class HeapSimul {

    @FXML
    protected Pane vectorPane;
    
    @FXML
    protected Pane treePane;
    
    //Tutti i possibili nodi dell'albero
    @FXML
    protected StackPane node0, node1, node2, node3, node4, node5, node6, node7, node8, node9, node10, node11, node12, node13, node14;

    @FXML
    protected HBox vectorHbox;

    @FXML
    protected TextField inputArea;

    @FXML
    protected Button addButton;

    @FXML
    protected Button removeButton;
    
    @FXML
    protected Button generateButton;
    
    @FXML
    protected ChoiceBox<String> maxMinChoiceBox;
    
    protected final Integer MAX_VECTOR_SIZE = 15;
    
    protected Boolean isGenerated;
    
    protected Boolean restoreController;
    
    //Vettore degli interi inseriti
    protected ArrayList<Integer> dataVector;
    
    //Vettore degli StackPane del vettore disegnato
    protected ArrayList<StackPane> numVector;
    
    //Vettori degli StackPane dell'albero e delle linee
    protected ArrayList<StackPane> nodeVector;
    protected ArrayList<Line> treeLines;

	public void initialize() {
		this.dataVector = new ArrayList<Integer>();
		this.numVector = new ArrayList<StackPane>();
		this.treeLines = new ArrayList<Line>();
		this.nodeVector = new ArrayList<StackPane>();
		this.nodeVector.add(node0);
		this.nodeVector.add(node1);
		this.nodeVector.add(node2);
		this.nodeVector.add(node3);
		this.nodeVector.add(node4);
		this.nodeVector.add(node5);
		this.nodeVector.add(node6);
		this.nodeVector.add(node7);
		this.nodeVector.add(node8);
		this.nodeVector.add(node9);
		this.nodeVector.add(node10);
		this.nodeVector.add(node11);
		this.nodeVector.add(node12);
		this.nodeVector.add(node13);
		this.nodeVector.add(node14);
		this.maxMinChoiceBox.getItems().add("MaxHeap");
		this.maxMinChoiceBox.getItems().add("MinHeap");
		this.isGenerated = false;
	}
	
	public ArrayList<Integer> randomVector(){
		System.out.println("Sono nel randomVector");
		Random random = new Random();
		Integer min = 4;
		Integer m = 15-min;
		Integer n = random.nextInt(m)+min;	//Valori compresi tra 4 e 15
		System.out.println("Ho creato la lista di elementi  " + n);
		
		//Creo vettore
		ArrayList<Integer> vector = new ArrayList<Integer>();
		for(Integer i=0; i < n; i++){
			vector.add(random.nextInt(99));
		}
		System.out.println("La lista contiene i seguenti numeri casuali: " +vector.toString());
		return(vector);
	}
	
	public void drawVector() {
		this.vectorHbox.getChildren().clear();	//Prima rimuovo il vettore già disegnato
		this.numVector.clear();
		
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
			this.numVector.add(numPane);
			
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
				Alert alert = new Alert(AlertType.INFORMATION, "Inserisci un numero intero.");
				alert.showAndWait();
				System.out.println("Please input a number");
			}
			this.inputArea.clear();		
			this.isGenerated = false;
			System.out.println(this.dataVector.toString());
		}		
		else {
			Alert alert = new Alert(AlertType.INFORMATION, "Dimensione massima del vettore raggiunta.");
			alert.showAndWait();
		}
	}
	
	public void removeFromVector() {
		if (this.dataVector.size() > 0)
			this.dataVector.remove(this.dataVector.size()-1);			//Rimuove ultimo elemento dal vettore
			this.drawVector();
			this.isGenerated = false;
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
		//Prima rimuovo l'albero già disegnato
		this.nodeVector.forEach((node) -> node.getChildren().clear());
		
		for (Line line : treeLines) {
			this.treePane.getChildren().remove(line);
		}

		Integer index = 0;
		
		//Itero sul vettore e disegno i suoi membri in ordine come albero binario partendo dalla radice
		Iterator<Integer> heapIterator = this.dataVector.iterator();
		while(heapIterator.hasNext()) {
			Integer num = heapIterator.next();
			
			StackPane node = this.nodeVector.get(index);
			Label numLabel = new Label();
			numLabel.setText(num.toString());
			Circle nodeCircle = new Circle(20);
			nodeCircle.setStroke(Color.BLUE);
			nodeCircle.setFill(Color.WHITE);
			
			node.getChildren().add(nodeCircle);
			node.getChildren().add(numLabel);
			
			//TODO Eventuali eventi relativi all'interazione col nodo
			
			
			
			//Disegno la linea tra un nodo e suo padre
			
			if(index != 0) {
				StackPane node2 = this.nodeVector.get((index-1)/2);
				
				//Queste sono conversioni coordinate local pane > coordinate parent pane
				Bounds node1ToParent = node.localToParent(node.getBoundsInLocal());
				
				Bounds node2ToParent = node2.localToParent(node.getBoundsInLocal());
				
				Point2D node1Center = new Point2D((node1ToParent.getMinX() + nodeCircle.getRadius()*2), node1ToParent.getMinY() + nodeCircle.getRadius()*2);
				Point2D node2Center = new Point2D(node2ToParent.getMinX() + nodeCircle.getRadius()*2, node2ToParent.getMinY() + nodeCircle.getRadius()*2);
				
				
				Line line = new Line(node1Center.getX(), node1Center.getY(), node2Center.getX(), node2Center.getY());
				line.setStroke(Color.BLACK);
				this.treePane.getChildren().add(line);
				line.toBack();
				this.treeLines.add(line);
				
			}
			
			index++;
		}
		
		this.isGenerated = true;
		
	}
	
	

}
