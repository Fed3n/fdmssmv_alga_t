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

public class InsertSimulController extends HeapSimul{
	
	@FXML
	private TextArea infoText;
	
	private ArrayList<ArrayList<Integer>> statusList;
	
	private Integer currentStatusIndex;
	
	@Override
	public void initialize() {
		super.initialize();
		this.infoText.setEditable(false);
		this.infoText.setWrapText(true);
		this.maxMinChoiceBox.setValue("MinHeap");
		this.removeButton.setDisable(true);
		this.generateButton.setDisable(true);
		this.infoText.setText("Scrivi il numero da inserire nella PriorityQueue e premi Add.");
		this.currentStatusIndex = 0;
	}
	
	@Override
	public void drawVector() {
		this.vectorHbox.getChildren().clear();	//Prima rimuovo il vettore già disegnato
		this.numVector.clear();
		
		
		Integer index = 0;
		//Itero sulla lista e per ogni membro creo uno slot del vettore
		Iterator<Integer> vectorIterator = this.dataVector.iterator();
		while(vectorIterator.hasNext()) {
			final Integer finalIndex = index;	//Da inserire nell'evento dato che richiede variabili final
			
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
			
			numPane.setOnMouseEntered(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					//Per evitare out of bound si attiva solo se l'albero e vettore sono finalizzati e se il nodo effettivamente può esserci
					if(isGenerated == true && finalIndex < dataVector.size()) {
						numBox.setFill(Color.GREENYELLOW);
						StackPane node = nodeVector.get(finalIndex);
						Circle circle = (Circle)node.getChildren().get(0);	//Il cerchio nello StackPane del nodo dell'albero
						circle.setFill(Color.GREENYELLOW);
						
						//Metto informazioni riguardo al nodo selezionato
						String info = "";
						info = info + "Questo nodo è in posizione " + (finalIndex+1) + " nel vettore.\n";
						//Se non è la radice
						if(finalIndex != 0) {
							info = info + "Il padre di questo nodo è in posizione " + ((finalIndex+1)/2) + " ed è colorato in rosso.\n";
							node = nodeVector.get((finalIndex-1)/2);
							circle = (Circle)node.getChildren().get(0);
							circle.setFill(Color.PALEVIOLETRED);
							StackPane pNode = numVector.get((finalIndex-1)/2);
							Rectangle box = (Rectangle)pNode.getChildren().get(0);
							box.setFill(Color.PALEVIOLETRED);
						}
						//Nel caso sia la radice
						else {
							info = info + "Questo nodo è la radice dell'albero, di conseguenza non ha padre.\n";
						}
						//Evidenzio e inserisco informazioni su eventuali figli
						if(finalIndex < dataVector.size()/2) {
							//Se ha due figli
							if(finalIndex < (dataVector.size()-1)/2) {
								info = info + "I figli di questo nodo sono in posizione " + ((finalIndex+1)*2) + " e " + ((finalIndex+1)*2+1) + ", colorati in blu\n";
								//Primo figlio
								node = nodeVector.get((finalIndex*2)+1);
								circle = (Circle)node.getChildren().get(0);
								circle.setFill(Color.CORNFLOWERBLUE);
								StackPane pNode = numVector.get((finalIndex*2)+1);
								Rectangle box = (Rectangle)pNode.getChildren().get(0);
								box.setFill(Color.CORNFLOWERBLUE);
								//Secondo figlio
								node = nodeVector.get((finalIndex*2)+2);
								circle = (Circle)node.getChildren().get(0);
								circle.setFill(Color.CORNFLOWERBLUE);
								pNode = numVector.get((finalIndex*2)+2);
								box = (Rectangle)pNode.getChildren().get(0);
								box.setFill(Color.CORNFLOWERBLUE);
							}
							//Un solo figlio
							else {
								info = info + "Il figlio di questo nodo è in posizione " + ((finalIndex+1)*2) + ", colorato in blu\n";
								//Primo figlio
								node = nodeVector.get((finalIndex*2)+1);
								circle = (Circle)node.getChildren().get(0);
								circle.setFill(Color.CORNFLOWERBLUE);
								StackPane pNode = numVector.get((finalIndex*2)+1);
								Rectangle box = (Rectangle)pNode.getChildren().get(0);
								box.setFill(Color.CORNFLOWERBLUE);
							}
						}
						
						infoText.setText(info);
					}
				}
			});
			
			numPane.setOnMouseExited(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					//Per evitare out of bound si attiva solo se l'albero e vettore sono finalizzati e se il nodo effettivamente può esserci
					if(isGenerated == true && finalIndex < dataVector.size()) {
						numBox.setFill(Color.WHITE);
						StackPane node = nodeVector.get(finalIndex);
						Circle circle = (Circle)node.getChildren().get(0);	//Il cerchio nello StackPane del nodo dell'albero
						circle.setFill(Color.WHITE);
						
						if(finalIndex != 0) {
							node = nodeVector.get((finalIndex-1)/2);
							circle = (Circle)node.getChildren().get(0);
							circle.setFill(Color.WHITE);
							StackPane pNode = numVector.get((finalIndex-1)/2);
							Rectangle box = (Rectangle)pNode.getChildren().get(0);
							box.setFill(Color.WHITE);
						}
						//Evidenzio e inserisco informazioni su eventuali figli
						if(finalIndex < dataVector.size()/2) {
							//Se ha due figli
							if(finalIndex < (dataVector.size()-1)/2) {
								//Primo figlio
								node = nodeVector.get((finalIndex*2)+1);
								circle = (Circle)node.getChildren().get(0);
								circle.setFill(Color.WHITE);
								StackPane pNode = numVector.get((finalIndex*2)+1);
								Rectangle box = (Rectangle)pNode.getChildren().get(0);
								box.setFill(Color.WHITE);
								//Secondo figlio
								node = nodeVector.get((finalIndex*2)+2);
								circle = (Circle)node.getChildren().get(0);
								circle.setFill(Color.WHITE);
								pNode = numVector.get((finalIndex*2)+2);
								box = (Rectangle)pNode.getChildren().get(0);
								box.setFill(Color.WHITE);
							}
							//Un solo figlio
							else {
								//Primo figlio
								node = nodeVector.get((finalIndex*2)+1);
								circle = (Circle)node.getChildren().get(0);
								circle.setFill(Color.WHITE);
								StackPane pNode = numVector.get((finalIndex*2)+1);
								Rectangle box = (Rectangle)pNode.getChildren().get(0);
								box.setFill(Color.WHITE);
							}
						}
						infoText.clear();
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
			Integer finalIndex = index;
			
			StackPane node = this.nodeVector.get(index);
			Label numLabel = new Label();
			numLabel.setText(num.toString());
			Circle nodeCircle = new Circle(20);
			nodeCircle.setStroke(Color.BLUE);
			nodeCircle.setFill(Color.WHITE);
			
			node.getChildren().add(nodeCircle);
			node.getChildren().add(numLabel);
			
			node.setOnMouseEntered(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					//Per evitare out of bound si attiva solo se l'albero e vettore sono finalizzati e se il nodo effettivamente può esserci
					if(isGenerated == true && finalIndex < dataVector.size()) {
						nodeCircle.setFill(Color.GREENYELLOW);
						StackPane node = numVector.get(finalIndex);
						Rectangle rect = (Rectangle)node.getChildren().get(0);	//Il cerchio nello StackPane del nodo dell'albero
						rect.setFill(Color.GREENYELLOW);
						
						//Metto informazioni riguardo al nodo selezionato
						String info = "";
						info = info + "Questo nodo è in posizione " + (finalIndex+1) + " nel vettore.\n";
						//Se non è la radice
						if(finalIndex != 0) {
							info = info + "Il padre di questo nodo è in posizione " + ((finalIndex+1)/2) + " ed è colorato in rosso.\n";
							node = nodeVector.get((finalIndex-1)/2);
							Circle circle = (Circle)node.getChildren().get(0);
							circle.setFill(Color.PALEVIOLETRED);
							StackPane pNode = numVector.get((finalIndex-1)/2);
							Rectangle box = (Rectangle)pNode.getChildren().get(0);
							box.setFill(Color.PALEVIOLETRED);
						}
						//Nel caso sia la radice
						else {
							info = info + "Questo nodo è la radice dell'albero, di conseguenza non ha padre.\n";
						}
						//Evidenzio e inserisco informazioni su eventuali figli
						if(finalIndex < dataVector.size()/2) {
							//Se ha due figli
							if(finalIndex < (dataVector.size()-1)/2) {
								info = info + "I figli di questo nodo sono in posizione " + ((finalIndex+1)*2) + " e " + ((finalIndex+1)*2+1) + ", colorati in blu\n";
								//Primo figlio
								node = nodeVector.get((finalIndex*2)+1);
								Circle circle = (Circle)node.getChildren().get(0);
								circle.setFill(Color.CORNFLOWERBLUE);
								StackPane pNode = numVector.get((finalIndex*2)+1);
								Rectangle box = (Rectangle)pNode.getChildren().get(0);
								box.setFill(Color.CORNFLOWERBLUE);
								//Secondo figlio
								node = nodeVector.get((finalIndex*2)+2);
								circle = (Circle)node.getChildren().get(0);
								circle.setFill(Color.CORNFLOWERBLUE);
								pNode = numVector.get((finalIndex*2)+2);
								box = (Rectangle)pNode.getChildren().get(0);
								box.setFill(Color.CORNFLOWERBLUE);
							}
							//Un solo figlio
							else {
								info = info + "Il figlio di questo nodo è in posizione " + ((finalIndex+1)*2) + ", colorato in blu\n";
								//Primo figlio
								node = nodeVector.get((finalIndex*2)+1);
								Circle circle = (Circle)node.getChildren().get(0);
								circle.setFill(Color.CORNFLOWERBLUE);
								StackPane pNode = numVector.get((finalIndex*2)+1);
								Rectangle box = (Rectangle)pNode.getChildren().get(0);
								box.setFill(Color.CORNFLOWERBLUE);
							}
						}
						
						infoText.setText(info);
					}
				}
			});
			
			node.setOnMouseExited(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					//Per evitare out of bound si attiva solo se l'albero e vettore sono finalizzati e se il nodo effettivamente può esserci
					if(isGenerated == true && finalIndex < dataVector.size()) {
						nodeCircle.setFill(Color.WHITE);
						StackPane node = numVector.get(finalIndex);
						Rectangle rect = (Rectangle)node.getChildren().get(0);	//Il cerchio nello StackPane del nodo dell'albero
						rect.setFill(Color.WHITE);
						
						if(finalIndex != 0) {
							node = nodeVector.get((finalIndex-1)/2);
							Circle circle = (Circle)node.getChildren().get(0);
							circle.setFill(Color.WHITE);
							StackPane pNode = numVector.get((finalIndex-1)/2);
							Rectangle box = (Rectangle)pNode.getChildren().get(0);
							box.setFill(Color.WHITE);
						}
						//Evidenzio e inserisco informazioni su eventuali figli
						if(finalIndex < dataVector.size()/2) {
							//Se ha due figli
							if(finalIndex < (dataVector.size()-1)/2) {
								//Primo figlio
								node = nodeVector.get((finalIndex*2)+1);
								Circle circle = (Circle)node.getChildren().get(0);
								circle.setFill(Color.WHITE);
								StackPane pNode = numVector.get((finalIndex*2)+1);
								Rectangle box = (Rectangle)pNode.getChildren().get(0);
								box.setFill(Color.WHITE);
								//Secondo figlio
								node = nodeVector.get((finalIndex*2)+2);
								circle = (Circle)node.getChildren().get(0);
								circle.setFill(Color.WHITE);
								pNode = numVector.get((finalIndex*2)+2);
								box = (Rectangle)pNode.getChildren().get(0);
								box.setFill(Color.WHITE);
							}
							//Un solo figlio
							else {
								//Primo figlio
								node = nodeVector.get((finalIndex*2)+1);
								Circle circle = (Circle)node.getChildren().get(0);
								circle.setFill(Color.WHITE);
								StackPane pNode = numVector.get((finalIndex*2)+1);
								Rectangle box = (Rectangle)pNode.getChildren().get(0);
								box.setFill(Color.WHITE);
							}
						}
						infoText.clear();
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
		if(this.dataVector.size() >= 1) {
			this.addButton.setDisable(true);
			this.removeButton.setDisable(true);
			this.infoText.setText("Premi genera per visualizzare l'albero relativo.");
		}
		this.generateButton.setDisable(false);
	}
	
	@Override
	public void removeFromVector() {
		super.removeFromVector();
		if(this.dataVector.size() >= 1 ) {
			this.infoText.setText("Trasforma in heap prima di interagire!");
			this.generateButton.setDisable(false);
			this.addButton.setDisable(true);
			this.removeButton.setDisable(true);
		} else {
			this.infoText.setText("Inserisci un numero e premi 'Add'/noppure genera un vettore casuale.");
			this.addButton.setDisable(false);
			this.removeButton.setDisable(true);
			this.generateButton.setDisable(true);
			this.drawTree();
		}
	}
	
	@Override
	public void generateHeap() {
		this.infoText.setText("Premi su avanti per procedere nei passi per il riposizionamento della foglia.");
		this.addButton.setDisable(false);
		this.generateButton.setDisable(false);
		if(this.dataVector.size() >= 1) this.removeButton.setDisable(false);
		else this.removeButton.setDisable(true);
		this.drawTree();
		this.currentStatusIndex = 0;
		this.generateStepbyStep();
	}
	
	public void generateStepbyStep(){
		System.out.println("Sono nello step by step");
		
		//Creo il primo passaggio
		ArrayList<ArrayList<Integer>> statusList = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> vector = new ArrayList<Integer>();
		ArrayList<Integer> l;
		vector.addAll(this.dataVector);
		statusList.add(vector);
		System.out.println(vector.toString() + " aggiunto a statusList");
		System.out.println(statusList.toString() + " statusList momentanea");
		
		//Creo i passaggi sucessivi
		Integer i = vector.size()-1;
		while (this.maxMinChoiceBox.getValue().equalsIgnoreCase("MinHeap") && i > 0 && vector.get(i) < vector.get(i/2)) {
			System.out.println(vector.get(i) + " é minore di " + vector.get(i/2) + " per cui li scambio.");
			System.out.println(statusList.toString() + " prima di scambio.");
			l = new ArrayList<Integer>();
			l.addAll(vector);
			Collections.swap(l, i, (int)(i/2));
			statusList.add(l);
			System.out.println(vector.toString() + " aggiunto a statusList");
			System.out.println(statusList.toString() + " statusList momentanea");
			i = i/2;
		}
		while (this.maxMinChoiceBox.getValue().equalsIgnoreCase("MaxHeap") && i > 0 && vector.get(i) > vector.get(i/2)) {
			System.out.println(vector.get(i) + " é maggiore di " + vector.get(i/2) + " per cui li scambio.");
			System.out.println(statusList.toString() + " prima di scambio.");
			l = new ArrayList<Integer>();
			l.addAll(vector);
			Collections.swap(l, i, (i/2));
			statusList.add(l);
			System.out.println(vector.toString() + " aggiunto a statusList");
			System.out.println(statusList.toString() + " statusList momentanea");
			i = i/2;
		}
		this.statusList = new ArrayList<ArrayList<Integer>>();
		this.statusList.addAll(statusList);
		System.out.println("statusList completa: " + statusList.toString());
	}

	public void next(){
		this.currentStatusIndex++;
		if(this.currentStatusIndex < this.statusList.size()) {
			this.dataVector = new ArrayList<Integer>();
			this.dataVector.addAll(this.statusList.get(this.currentStatusIndex));
			this.drawVector();
			this.drawTree();
		} else {
			this.infoText.setText("Passaggi completati, aggiungi un altro elemento e premi su genera.");
		}
	}
}
