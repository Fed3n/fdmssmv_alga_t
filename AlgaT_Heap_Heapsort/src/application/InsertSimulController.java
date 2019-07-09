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

public class InsertSimulController extends HeapSimul2{
	
	@FXML
	private TextArea infoText;
	
	@FXML
	private Button nextButton;
	
	@FXML
	private Button prevButton;
	
	private ArrayList<ArrayList<Integer>> statusList;
	
	private ArrayList<String> instructionList;
	
	private ArrayList<ArrayList<Integer>> lightableIndex;
	
	private Integer currentStatusIndex;
	
	@Override
	public void initialize() {
		super.initialize();
		this.infoText.setEditable(false);
		this.infoText.setWrapText(true);
		this.maxMinChoiceBox.setValue("MinHeap");
		this.maxMinChoiceBox.setVisible(false);
		this.removeButton.setDisable(true);
		this.generateButton.setDisable(true);
		this.prevButton.setDisable(true);
		this.nextButton.setDisable(true);
		this.infoText.setText("Scrivi il numero da inserire nella PriorityQueue e premi Add.");
		this.currentStatusIndex = 0;
		this.lightableIndex = new ArrayList<ArrayList<Integer>>();
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
			this.removeButton.setDisable(false);
			this.prevButton.setDisable(true);
			this.nextButton.setDisable(true);
			this.generateButton.setDisable(false);
			this.infoText.setText("Premi genera per visualizzare l'albero relativo.");
		}
		else {
			this.addButton.setDisable(false);
		}
	}
	
	@Override
	public void removeFromVector() {
		super.removeFromVector();
		if(this.dataVector.size() >= 1 ) {
			this.infoText.setText("Premi genera prima di interagire.");
			this.generateButton.setDisable(false);
			this.addButton.setDisable(true);
			this.removeButton.setDisable(true);
		} else {
			this.infoText.setText("Inserisci un numero e premi 'Add'.");
			this.addButton.setDisable(false);
			this.removeButton.setDisable(true);
			this.generateButton.setDisable(true);
			this.drawTree();
		}
	}
	
	@Override
	public void generateHeap() {
		this.infoText.setText("Premi su avanti per procedere nei passi per il riposizionamento della foglia.");
		if(this.dataVector.size() >= 1) {
			this.nextButton.setDisable(false);
			this.prevButton.setDisable(true);
			this.addButton.setDisable(true);
			this.removeButton.setDisable(true);
			this.generateButton.setDisable(true);
			this.drawTree();
			this.currentStatusIndex = 0;
			this.generateStepbyStep();
			this.dataVector = new ArrayList<Integer>();
			this.dataVector.addAll(this.statusList.get(this.currentStatusIndex));
			this.drawVector();
			this.drawTree();
		}
	}
	
	public void generateStepbyStep(){
		this.lightableIndex.clear();
		
		ArrayList<String> instruction = new ArrayList<String>();
		this.lightableIndex = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> lightable = new ArrayList<Integer>();
		//Creo il primo passaggio
		ArrayList<ArrayList<Integer>> statusList = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> vector = new ArrayList<Integer>();
		ArrayList<Integer> l;
		ArrayList<Integer> k;
		instruction.add("Premi avanti per scorrere nella simulazione interattiva.");
		lightable.add(vector.size());
		this.lightableIndex.add(lightable);
		vector.addAll(this.dataVector);
		statusList.add(vector);
		
		//Creo i passaggi sucessivi
		Integer i = vector.size()-1;
		System.out.println("Il vettore arriva fino alla posizione: " + i);
		l = new ArrayList<Integer>();
		l.addAll(vector);
		while (i > 0 && l.get(i) < l.get(super.parent(i))) {
			instruction.add(l.get(i) + " é minore di " + l.get((i-1)/2) + " per cui si procede con lo scambio.");
			k = new ArrayList<Integer>();
			k.addAll(l);
			lightable = new ArrayList<Integer>();
			lightable.add(i);
			lightable.add(super.parent(i));
			this.lightableIndex.add(lightable);
			Collections.swap(k, i, super.parent(i));
			Collections.swap(l, i, super.parent(i));
			statusList.add(k);
			i = super.parent(i);
		}
		this.statusList = new ArrayList<ArrayList<Integer>>();
		instruction.add("Simulazione terminata.");
		this.statusList.addAll(statusList);
		System.out.println("statusList completa: " + statusList.toString());
		this.instructionList = new ArrayList<String>();
		this.instructionList.addAll(instruction);
		System.out.println(this.lightableIndex.toString());
	}

	public void next(){
		this.currentStatusIndex++;
		System.out.println(this.statusList.toString());
		System.out.println("Index: " + this.currentStatusIndex);
		this.prevButton.setDisable(false);
		Integer size = this.statusList.size();
		System.out.println("La grandezza della statusList é: " + size);
		if(this.currentStatusIndex < this.statusList.size()) {
			Integer k = this.statusList.size();
			System.out.println("La grandezza della statusList é: " + k);
			this.dataVector = new ArrayList<Integer>();
			this.dataVector.addAll(this.statusList.get(this.currentStatusIndex));
			this.drawVector();
			this.drawTree();
		} else {
			this.infoText.setText("Passaggi completati, aggiungi un altro elemento e premi su genera.");
			this.addButton.setDisable(false);
			this.generateButton.setDisable(true);
			this.nextButton.setDisable(true);
			this.removeButton.setDisable(false);
		}
		if(this.currentStatusIndex > 0 && this.currentStatusIndex < this.statusList.size()) {
			System.out.println("Sono dentro all'if");
			ArrayList<Integer> i = new ArrayList<Integer>();
			i = this.lightableIndex.get(this.currentStatusIndex);
			System.out.println("Devo colorare " + this.lightableIndex.toString());
			if(i != null) {
				for(int j = 0; j < i.size(); j++) {
					Circle circ = (Circle)nodeVector.get(i.get(j)).getChildren().get(0);
					circ.setStroke(Color.CORAL);
					Rectangle rect = (Rectangle)numVector.get(i.get(j)).getChildren().get(0);
					rect.setStroke(Color.CORAL);
				}
			} else this.drawTree();
		}
		if(this.instructionList.size() > this.currentStatusIndex) this.infoText.setText(this.instructionList.get(this.currentStatusIndex));
	}
	
	public void prev() {
		this.currentStatusIndex--;
		System.out.println(this.statusList.toString());
		System.out.println("Index: " + this.currentStatusIndex);
		if(this.currentStatusIndex == 0) {
			this.dataVector = new ArrayList<Integer>();
			this.dataVector.addAll(this.statusList.get(this.currentStatusIndex));
			this.prevButton.setDisable(true);
			this.generateButton.setDisable(true);
			this.addButton.setDisable(true);
			this.removeButton.setDisable(true);
			this.nextButton.setDisable(false);
			this.drawVector();
			this.drawTree();
		} else {
			this.dataVector = new ArrayList<Integer>();
			this.dataVector.addAll(this.statusList.get(this.currentStatusIndex));
			this.nextButton.setDisable(false);
			this.addButton.setDisable(true);
			this.removeButton.setDisable(true);
			this.generateButton.setDisable(true);
			this.drawVector();
			this.drawTree();
		}
		if(this.currentStatusIndex > 0) {
			ArrayList<Integer> i = new ArrayList<Integer>();
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
		this.infoText.setText(this.instructionList.get(this.currentStatusIndex));
	}
}
