package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class HeapRestore extends HeapSimul2{
	
	@FXML
	protected Label vectorLabel;
	
	@FXML
	protected TextArea infoText;
	
	@FXML
	protected Button restoreButton;
	
	@FXML
	protected Button nextButton;
	
	@FXML
	protected Button prevButton;
	
	@FXML
	protected Button readyButton;
	
	protected ArrayList<ArrayList<Integer>> statusList;		//Lista di stati del vettore durante l'operazione
	
	protected ArrayList<String> instructionList;			//Lista di istruzioni da inserire
	
	protected ArrayList<ArrayList<Integer>> lightableIndex;	//Lista dei nodi da illuminare ad ogni passaggio sequenziale
	
	protected Integer currentStatusIndex;					//Indice dello stato visualizzato del vettore
	
	protected Integer selectedIndex;						//Indice del nodo selezionato
	
	protected Boolean selectable;							//Indica se è possibile selezionare un nodo
	
	@Override
	public void initialize() {
		super.initialize();
		this.infoText.setEditable(false);
		this.infoText.setWrapText(true);	
		this.currentStatusIndex = 0;
		this.selectedIndex = null;
		this.selectable = false;
		this.nextButton.setDisable(true);
		this.prevButton.setDisable(true);
		this.maxMinChoiceBox.setValue("MinHeap");
		this.infoText.setText("Premi su genera per creare un vettore composto da numeri casuali.");
		this.instructionList = new ArrayList<String>();
		this.lightableIndex = new ArrayList<ArrayList<Integer>>();
		this.restoreButton.setDisable(true);
		this.removeButton.setDisable(true);
		this.readyButton.setDisable(true);
	}
	
	//La funzione crea la lista di status del vettore con una maxHeapRestore step-by-step
	public ArrayList<ArrayList<Integer>> stepByStepMaxRestore(ArrayList<Integer> vector, Integer index) {
		ArrayList<ArrayList<Integer>> statusList = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> l = new ArrayList<Integer>();
		Boolean finished = false;
		
		//Per come funzionano gli oggetti in java devo creare un nuovo vettore ogni volta che aggiorno la lista di vettori
		ArrayList<Integer> v = new ArrayList<Integer>();
		v.addAll(vector);
		statusList.add(v);
		this.instructionList.add("Applichiamo maxHeapRestore sul nodo in posizione " + (index+1));
		l.add(index);
		this.lightableIndex.add(l);
		
		while(!finished) {
			Integer max = index;
			
			if(!(index >= vector.size()/2)){	
				//Figlio sinistro > figlio destro, prima controlla che esista un figlio destro
				if((this.rChild(index) >= vector.size()) || vector.get(this.lChild(index)) >= vector.get(this.rChild(index))){
					//Se il figlio più grande è maggiore del padre, si scambiano
					if(vector.get(index) < vector.get(this.lChild(index))) {
						this.instructionList.add("Il figlio sinistro " + vector.get(this.lChild(index)) + " è maggiore del padre " + vector.get(index) + ", quindi vengono scambiati.");
						l = new ArrayList<Integer>();
						l.add(index);
						l.add(this.lChild(index));
						this.lightableIndex.add(l);
						Collections.swap(vector, index, this.lChild(index));
						max = this.lChild(index);
					}				
				}
			
				else {
					//Se il figlio più grande è maggiore del padre, si scambiano
					if(vector.get(index) < vector.get(this.rChild(index))) {
						this.instructionList.add("Il figlio destro " + vector.get(this.rChild(index)) + " è maggiore del padre " + vector.get(index) + ", quindi vengono scambiati.");
						l = new ArrayList<Integer>();
						l.add(index);
						l.add(this.rChild(index));
						this.lightableIndex.add(l);
						Collections.swap(vector, index, this.rChild(index));
						max = this.rChild(index);
					}
				}	
			}		
			
			//Se non c'è stato da scambiare l'operazione è terminata
			if(index == max) {
				finished = true;
				v = new ArrayList<Integer>();
				v.addAll(vector);
				statusList.add(v);
				
				this.instructionList.add("Non c'è nulla da scambiare. maxHeapRestore termina.");
				this.lightableIndex.add(null);
				}
			//L'operazione procede sull'indice
			else {
				v = new ArrayList<Integer>();
				v.addAll(vector);
				statusList.add(v);
				index = max;
			}	
			this.printVector(statusList);
			System.out.println("@@@@@@@@@@@@@@@@");
		}
		return statusList;
	}
	
	//La funzione crea la lista di status del vettore con una minHeapRestore step-by-step
		public ArrayList<ArrayList<Integer>> stepByStepMinRestore(ArrayList<Integer> vector, Integer index) {
			ArrayList<ArrayList<Integer>> statusList = new ArrayList<ArrayList<Integer>>();
			ArrayList<Integer> l = new ArrayList<Integer>();
			Boolean finished = false;
			
			ArrayList<Integer> v = new ArrayList<Integer>();
			v.addAll(vector);
			statusList.add(v);
			this.instructionList.add("Applichiamo minHeapRestore sul nodo in posizione " + (index+1));
			l.add(index);
			this.lightableIndex.add(l);
			
			while(!finished) {
				Integer min = index;
				
				if(!(index >= vector.size()/2)){		
					//Figlio sinistro < figlio destro, prima controlla che esista un figlio destro
					if((this.rChild(index) >= vector.size()) || vector.get(this.lChild(index)) <= vector.get(this.rChild(index))){
						//Se il figlio più piccolo è minore del padre, si scambiano
						if(vector.get(index) > vector.get(this.lChild(index))) {
							this.instructionList.add("Il figlio sinistro " + vector.get(this.lChild(index)) + " è maggiore del padre " + vector.get(index) + ", quindi vengono scambiati.");
							l = new ArrayList<Integer>();
							l.add(index);
							l.add(this.lChild(index));
							this.lightableIndex.add(l);
							Collections.swap(vector, index, this.lChild(index));
							min = this.lChild(index);
						}
					}
				
					else {
						//Se il figlio più piccolo è minore del padre, si scambiano
						if(vector.get(index) > vector.get(this.rChild(index))) {
							this.instructionList.add("Il figlio destro " + vector.get(this.rChild(index)) + " è maggiore del padre " + vector.get(index) + ", quindi vengono scambiati.");
							l = new ArrayList<Integer>();
							l.add(index);
							l.add(this.rChild(index));
							this.lightableIndex.add(l);
							Collections.swap(vector, index, this.rChild(index));
							min = this.rChild(index);
						}
					}				
				}
				
				//Se non c'è stato da scambiare l'operazione è terminata
				if(index == min) {
					finished = true;
					v = new ArrayList<Integer>();
					v.addAll(vector);
					statusList.add(v);
					
					this.instructionList.add("Non c'è nulla da scambiare. minHeapRestore termina.");
					this.lightableIndex.add(null);
					}
				//L'operazione procede sull'indice
				else {
					v = new ArrayList<Integer>();
					v.addAll(vector);
					statusList.add(v);
					index = min;
				}	
				this.printVector(statusList);
				System.out.println("@@@@@@@@@@@@@@@@");
			}
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
			ArrayList<Integer> i;
			
			this.currentStatusIndex++;
			//Disabilito next se sono all'ultimo
			if (this.currentStatusIndex+1 >= this.statusList.size()) {
				this.nextButton.setDisable(true);
				this.readyButton.setDisable(false);
				this.addButton.setDisable(false);
				this.removeButton.setDisable(false);
				this.restoreButton.setDisable(true);
			} else {
				this.nextButton.setDisable(false);
				this.readyButton.setDisable(true);
				this.addButton.setDisable(true);
				this.addButton.setDisable(true);
				this.removeButton.setDisable(true);
				this.restoreButton.setDisable(true);
			}
			
			this.prevButton.setDisable(false);
			
			this.dataVector = this.statusList.get(this.currentStatusIndex);
			this.infoText.setText(this.instructionList.get(this.currentStatusIndex));
			
			
			this.drawVector();
			this.drawTree();
			
			
			i = this.lightableIndex.get(this.currentStatusIndex);
			if(i != null) {
				for(int j = 0; j < i.size(); j++) {
					System.out.println("Devo colorare " + i.get(j));
					Circle circ = (Circle)nodeVector.get(i.get(j)).getChildren().get(0);
					circ.setStroke(Color.CORAL);
					Rectangle rect = (Rectangle)numVector.get(i.get(j)).getChildren().get(0);
					rect.setStroke(Color.CORAL);
				}
			}
			
		}
	}
	
	public void prevStatus() {
		this.addButton.setDisable(true);
		this.readyButton.setDisable(true);
		this.removeButton.setDisable(true);
		this.restoreButton.setDisable(true);
		
		if(currentStatusIndex > 0) {
			ArrayList<Integer> i;
			currentStatusIndex--;
			
			//Disabilito prev se sono al primo
			if(this.currentStatusIndex <= 0)
				this.prevButton.setDisable(true);
			
			this.nextButton.setDisable(false);
			
			this.dataVector = this.statusList.get(this.currentStatusIndex);
			this.infoText.setText(this.instructionList.get(this.currentStatusIndex));
			
			this.drawVector();
			this.drawTree();
			
			
			i = this.lightableIndex.get(this.currentStatusIndex);
			if(i != null) {
				for(int j = 0; j < i.size(); j++) {
					Circle circ = (Circle)nodeVector.get(i.get(j)).getChildren().get(0);
					circ.setStroke(Color.CORAL);
					Rectangle rect = (Rectangle)numVector.get(i.get(j)).getChildren().get(0);
					rect.setStroke(Color.CORAL);
				}				
			}
		}
	}
	
	//Pulsante restore, genera la successione di step-by-step
	public void generateSteps() {
		if(this.selectedIndex != null && (this.maxMinChoiceBox.getValue() != null)) {
			//Controllo che non sia una foglia
			if(!(this.selectedIndex >= ((this.dataVector.size())/2))) {
				this.prevButton.setDisable(true);
				this.nextButton.setDisable(false);
				
				//Pulisco la instructionList dai precedenti messaggi
				this.instructionList.clear();
				this.lightableIndex.clear();
				
				System.out.println(this.selectedIndex);
				System.out.println(this.dataVector.size());
				ArrayList<ArrayList<Integer>> vector = new ArrayList<ArrayList<Integer>>();
				if(this.maxMinChoiceBox.getValue().contentEquals("MaxHeap")) {
					vector = this.stepByStepMaxRestore(this.dataVector, this.selectedIndex);
				}
				else {
					vector = this.stepByStepMinRestore(this.dataVector, this.selectedIndex);
				}
				//Assegno il vettore di status generato al suo campo
				this.statusList = vector;
				this.printVector(vector);	
				
				//Poi resetto la schermata dell'interazione
				this.currentStatusIndex = 0;
				this.selectedIndex = null;
				this.dataVector = this.statusList.get(this.currentStatusIndex);
				this.infoText.setText(this.instructionList.get(this.currentStatusIndex));
				this.drawVector();
				this.drawTree();
				this.nextButton.setDisable(false);
				this.selectable = false;
				
				ArrayList<Integer> i = new ArrayList<Integer>();
				i = this.lightableIndex.get(this.currentStatusIndex);
				if(i != null) {
					for(int j = 0; j < i.size(); j++) {
						System.out.println("Devo colorare " + i.get(j));
						Circle circ = (Circle)nodeVector.get(i.get(j)).getChildren().get(0);
						circ.setStroke(Color.CORAL);
						Rectangle rect = (Rectangle)numVector.get(i.get(j)).getChildren().get(0);
						rect.setStroke(Color.CORAL);
					}
				}
				
				System.out.println("Dimensione stringhe: " + this.instructionList.size());
				System.out.println("Dimensione vettori: " + this.statusList.size());
			}
			else {
				Alert alert = new Alert(AlertType.WARNING, "Non puoi selezionare una foglia.");
				alert.showAndWait();
			}
		}
		else if(this.maxMinChoiceBox.getValue() == null) {
			Alert alert = new Alert(AlertType.WARNING, "Devi selezionare un elemento dal menu a tenda.");
			alert.showAndWait();
		}
	}
	
	public void readyVector() {
		this.nextButton.setDisable(true);
		this.prevButton.setDisable(true);
		this.addButton.setDisable(true);
		this.removeButton.setDisable(true);
		if(this.dataVector.size() >= 1) this.restoreButton.setDisable(false);
		this.readyButton.setDisable(true);
		this.infoText.setText("Scegli dalla tendina sottostante la simulazione che vuoi effettuare, poi seleziona il nodo dell'albero da cui far partire la simulazione ed infine premi Restore!");
		this.selectable = true;
		//this.drawVector();
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
					if(isGenerated && selectable) {
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
					if(isGenerated && selectable) {
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
		this.dataVector = super.randomVector();
		super.drawVector();
		this.infoText.setText("Premi su Ready per generare l'albero relativo al vettore.");
		this.nextButton.setDisable(true);
		this.prevButton.setDisable(true);
		this.addButton.setDisable(false);
		this.removeButton.setDisable(false);
		this.readyButton.setDisable(false);
		this.selectable = false;
	}
	
	@Override
	public void removeFromVector() {
		super.removeFromVector();
		String s = " ";
		if (this.isGenerated) s = " ri"; 
		this.infoText.setText("Premi su Ready per"+s+"disegnare l'heap.");
		this.nextButton.setDisable(true);
		this.prevButton.setDisable(true);
		if(this.dataVector.size() < 1) this.removeButton.setDisable(true);
		if(this.dataVector.size() < 1) this.readyButton.setDisable(true);
		else this.readyButton.setDisable(false);
		this.restoreButton.setDisable(true);
		this.selectable = false;
	}
}



	
	

