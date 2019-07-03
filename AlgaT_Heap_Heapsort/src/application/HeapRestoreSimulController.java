package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;


public class HeapRestoreSimulController extends HeapSimul2Controller{
	
	@FXML
	private Label vectorLabel;
	
	@FXML
	private TextArea infoText;
	
	@FXML
	private Button restoreButton;
	
	@FXML
	private Button nextButton;
	
	@FXML
	private Button prevButton;
	
	@FXML
	private Button drawButton;
	
	private ArrayList<ArrayList<Integer>> statusList;
	
	private Integer currentStatusIndex;
	
	private Integer selectedIndex;
	
	
	
	@Override
	public void initialize() {
		super.initialize();
		this.infoText.setEditable(false);
		this.infoText.setWrapText(true);	
		this.currentStatusIndex = 0;
		this.selectedIndex = null;
		this.nextButton.setDisable(true);
		this.prevButton.setDisable(true);
	}
	
	//La funzione crea la lista di status del vettore con una maxHeapRestore step-by-step
	public ArrayList<ArrayList<Integer>> stepByStepMaxRestore(ArrayList<Integer> vector, Integer index) {
		ArrayList<ArrayList<Integer>> statusList = new ArrayList<ArrayList<Integer>>();
		Boolean finished = false;
		Boolean swapped = false;
		
		ArrayList<Integer> v = new ArrayList<Integer>();
		v.addAll(vector);
		statusList.add(v);
		
		while(!finished) {
			swapped = false;
			
			//Figlio sinistro > figlio destro
			if(vector.get(this.lChild(index)) >= vector.get(this.rChild(index))){
				//Se il figlio più grande è maggiore del padre, si scambiano
				if(vector.get(index) < vector.get(this.lChild(index))) {
					Collections.swap(vector, index, this.lChild(index));
					//TODO Evidenza scambio tra padre e lchild
					swapped = true;
				}				
			}
			
			else {
				//Se il figlio più grande è maggiore del padre, si scambiano
				if(vector.get(index) < vector.get(this.rChild(index))) {
					Collections.swap(vector, index, this.rChild(index));
					//TODO Evidenza scambio tra padre e rchild
					swapped = true;
				}
			}
			
			//Se non c'è stato da scambiare o index era il padre l'operazione è terminata
			if(!swapped || index == 0) {
				finished = true;
				ArrayList<Integer> w = new ArrayList<Integer>();
				w.addAll(vector);
				statusList.add(w);
				//TODO Evidenza che non c'è più da scambiare
			}
			
			//Altrimenti l'operazione procede sul padre
			else {
				ArrayList<Integer> w = new ArrayList<Integer>();
				w.addAll(vector);
				statusList.add(w);
				index = this.parent(index);
			}	
			this.printVector(statusList);
			System.out.println("@@@@@@@@@@@@@@@@");
		}
		this.statusList = statusList;
		return statusList;
	}
	
	//La funzione crea la lista di status del vettore con una minHeapRestore step-by-step
		public ArrayList<ArrayList<Integer>> stepByStepMinRestore(ArrayList<Integer> vector, Integer index) {
			ArrayList<ArrayList<Integer>> statusList = new ArrayList<ArrayList<Integer>>();
			Boolean finished = false;
			Boolean swapped = false;
			
			ArrayList<Integer> v = new ArrayList<Integer>();
			v.addAll(vector);
			statusList.add(v);
			
			while(!finished) {
				swapped = false;
				
				//Figlio sinistro > figlio destro
				if(vector.get(this.lChild(index)) <= vector.get(this.rChild(index))){
					//Se il figlio più grande è maggiore del padre, si scambiano
					if(vector.get(index) > vector.get(this.lChild(index))) {
						Collections.swap(vector, index, this.lChild(index));
						//TODO Evidenza scambio tra padre e lchild
						swapped = true;
					}				
				}
				
				else {
					//Se il figlio più grande è maggiore del padre, si scambiano
					if(vector.get(index) > vector.get(this.rChild(index))) {
						Collections.swap(vector, index, this.rChild(index));
						//TODO Evidenza scambio tra padre e rchild
						swapped = true;
					}
				}
				
				//Se non c'è stato da scambiare o index era il padre l'operazione è terminata
				if(!swapped || index == 0) {
					finished = true;
					ArrayList<Integer> w = new ArrayList<Integer>();
					w.addAll(vector);
					statusList.add(w);
					//TODO Evidenza che non c'è più da scambiare
				}
				//L'operazione procede sul padre
				else {
					ArrayList<Integer> w = new ArrayList<Integer>();
					w.addAll(vector);
					statusList.add(w);
					index = this.parent(index);
				}	
				this.printVector(statusList);
				System.out.println("@@@@@@@@@@@@@@@@");
			}
			this.statusList = statusList;
			return statusList;
		}
	
	public void printVector(ArrayList<ArrayList<Integer>> vector) {
		Iterator<ArrayList<Integer>> iterator = vector.iterator();
		
		while(iterator.hasNext()) {
			System.out.println(iterator.next().toString());			
		}
	}
	
	public void nextStatus() {
		if(this.statusList.size() > currentStatusIndex+1) {
			currentStatusIndex++;
			this.dataVector = this.statusList.get(this.currentStatusIndex);
			this.drawVector();
			this.drawTree();
		}
	}
	
	public void prevStatus() {
		if(currentStatusIndex > 0) {
			currentStatusIndex--;
			this.dataVector = this.statusList.get(this.currentStatusIndex);
			this.drawVector();
			this.drawTree();
		}
	}
	
	//Pulsante restore, genera la successione di step-by-step
	public void generateSteps() {
		if(this.selectedIndex != null && (this.maxMinChoiceBox.getValue() != null)) {
			ArrayList<ArrayList<Integer>> test = new ArrayList<ArrayList<Integer>>();
			if(this.maxMinChoiceBox.getValue().contentEquals("MaxHeap")) {
				test = this.stepByStepMaxRestore(this.dataVector, this.selectedIndex);
			}
			else {
				test = this.stepByStepMinRestore(this.dataVector, this.selectedIndex);
			}
			this.printVector(test);	
			this.currentStatusIndex = 0;
			this.selectedIndex = null;
			this.dataVector = this.statusList.get(this.currentStatusIndex);
			this.drawVector();
			this.drawTree();
			this.nextButton.setDisable(false);
			this.prevButton.setDisable(false);
		}
	}
	
	public void draw() {
		this.drawVector();
		this.drawTree();
	}
	
	@Override
	public void drawVector() {
		this.vectorHbox.getChildren().clear();	//Prima rimuovo il vettore già disegnato
		this.numVector.clear();
		Integer index = 0;
		
		//Itero sulla lista e per ogni membro creo uno slot del vettore
		Iterator<Integer> vectorIterator = this.dataVector.iterator();
		while(vectorIterator.hasNext()) {
			Integer num = vectorIterator.next();	
			final Integer finalIndex = index;
			
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
			
			//Evento per selezionare nodo su cui applicare la funzione
			numPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					if(isGenerated) {
						for(Integer index = 0; index < numVector.size(); index++) {
							Rectangle rect = (Rectangle)numVector.get(index).getChildren().get(0);
							rect.setStroke(Color.BLUE);
						}
						for(Integer index = 0; index < nodeVector.size(); index++) {
							if((nodeVector.get(index).getChildren().size() > 0)) {		//Controlla se il pane del nodo dell'albero è disegnato o meno
								Circle circ = (Circle)nodeVector.get(index).getChildren().get(0);
								circ.setStroke(Color.BLUE);
							}
						}
						numBox.setStroke(Color.RED);
						Circle circle = (Circle)nodeVector.get(finalIndex).getChildren().get(0);
						circle.setStroke(Color.RED);
						
						selectedIndex = finalIndex;
					}
				}
			});
			
			index++;
		}
	}
	
	@Override
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
			final Integer finalIndex = index;
			
			StackPane node = this.nodeVector.get(index);
			Label numLabel = new Label();
			numLabel.setText(num.toString());
			Circle nodeCircle = new Circle(20);
			nodeCircle.setStroke(Color.BLUE);
			nodeCircle.setFill(Color.WHITE);
			
			node.getChildren().add(nodeCircle);
			node.getChildren().add(numLabel);
			
			//Evento per selezionare nodo su cui applicare la funzione
			node.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					if(isGenerated) {
						for(Integer index = 0; index < numVector.size(); index++) {
							Rectangle rect = (Rectangle)numVector.get(index).getChildren().get(0);
							rect.setStroke(Color.BLUE);
						}
						for(Integer index = 0; index < nodeVector.size(); index++) {
							if((nodeVector.get(index).getChildren().size() > 0)) {		//Controlla se il pane del nodo dell'albero è disegnato o meno
								Circle circ = (Circle)nodeVector.get(index).getChildren().get(0);
								circ.setStroke(Color.BLUE);
							}
						}
						nodeCircle.setStroke(Color.RED);
						Rectangle rect = (Rectangle)numVector.get(finalIndex).getChildren().get(0);
						rect.setStroke(Color.RED);
						
						selectedIndex = finalIndex;
					}
				}
			});
			
			
			
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
	
	@Override
	public void addToVector() {
		super.addToVector();
		this.nextButton.setDisable(true);
		this.prevButton.setDisable(true);
	}
	
	@Override
	public void removeFromVector() {
		super.removeFromVector();
		this.nextButton.setDisable(true);
		this.prevButton.setDisable(true);
	}
	
	@Override
	public void generateHeap() {
		super.generateHeap();
		this.vectorLabel.setText("");
	}
	
	//Nei seguenti metodi l'indexing è un po' differente perché il vettore parte da 0 e non da 1 come nella teoria//
		private Integer parent(Integer index) {
			return ((index-1)/2);
		}

		private Integer lChild(Integer index) {
			return (index*2+1);
		}

		private Integer rChild(Integer index) {
			return (index*2+2);
		}
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
}



	
	

