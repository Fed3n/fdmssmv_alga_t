package application;

import java.util.Iterator;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class RestoreSimulController extends HeapSimul{

	@FXML
	protected ChoiceBox<String> maxMinChoiceBox;

	@FXML
	private TextArea infoText;

	@FXML
	private Button btnPrvs;
	
	@FXML
	private Button btnNxt;
	
	private Integer vectorCounter;
	
	private MinHeap minBuildHeap;
	
	private MaxHeap maxBuildHeap;

	//Quando aggiungo un elemento al vettore, lo aggiungo anche al grafico
	public void startSimulation(){
		this.vectorCounter = 0;
		this.infoText.setText("Hai aggiunto " + this.inputArea.getText() + ".");
		this.addToVector();
		//Mostro il passo 0
		this.drawVector();
		this.drawTree();
		this.btnNxt.setDisable(false);
		this.addButton.setDisable(true);
		this.generateHeap();
	}
	
	public void nextStep(){
		this.vectorCounter++;
		this.changeStep();
	}
	
	public void previousStep(){
		if(this.vectorCounter > 0) {
			this.btnPrvs.setDisable(false);
			this.vectorCounter--;
		}
		else this.btnPrvs.setDisable(true);
		this.changeStep();
	}
	
	public void changeStep(){
		//Modifico il vettore di valori passo per passo
		System.out.println(this.maxMinChoiceBox.getValue());
		if(this.maxMinChoiceBox.getValue().compareTo("maxHeapRestore") == 0 && this.maxBuildHeap.getFullList().size() > this.vectorCounter) {
			this.dataVector.clear();
			this.dataVector.addAll(this.maxBuildHeap.getVector(this.vectorCounter));
			this.addButton.setDisable(true);
			this.btnNxt.setDisable(false);
			this.infoText.setText("Premi avanti per continuare.");
		}
		else if(this.maxMinChoiceBox.getValue().compareTo("maxHeapRestore") == 0 && this.maxBuildHeap.getFullList().size() < this.vectorCounter)  {
			System.out.println("Sei andato oltre la grandezza massima.");
			this.addButton.setDisable(false);
			this.btnNxt.setDisable(true);
		}
		else if(this.maxMinChoiceBox.getValue().compareTo("maxHeapRestore") == 0 && this.maxBuildHeap.getFullList().size() == this.vectorCounter) {
			System.out.println("Sono all'ultima simulazione.");
			this.addButton.setDisable(false);
			this.btnNxt.setDisable(true);
			this.infoText.setText("Inserisci un nuovo numero");
		}
		else if(this.maxMinChoiceBox.getValue().compareTo("minHeapRestore") == 0 && this.minBuildHeap.getFullList().size() > this.vectorCounter) {
			this.dataVector.clear();
			//Se utilizzo addAll come per maxHeapRestore da errore nella console
			this.dataVector = this.minBuildHeap.getVector(this.vectorCounter);
			this.addButton.setDisable(true);
			this.btnNxt.setDisable(false);
		}
		else if (this.maxMinChoiceBox.getValue().compareTo("minHeapRestore") == 0 && this.minBuildHeap.getFullList().size() < this.vectorCounter) {
			System.out.println("Sei andato oltre la grandezza massima.");
			System.out.println("la grandezza del vettore é: " + this.dataVector.size() + " e contiene " + this.dataVector.toString());
			this.addButton.setDisable(false);
			this.btnNxt.setDisable(true);
			this.infoText.setText("Premi avanti per continuare.");
		}
		else if(this.maxMinChoiceBox.getValue().compareTo("minHeapRestore") == 0 && this.minBuildHeap.getFullList().size() == this.vectorCounter) {
			System.out.println("Sono all'ultima simulazione.");
			this.addButton.setDisable(false);
			this.btnNxt.setDisable(true);
			this.infoText.setText("Inserisci un nuovo numero");
		}
		this.drawVector();
		this.drawTree();
	}

	@Override
	public void initialize() {
		super.initialize();
		this.infoText.setEditable(false);
		this.infoText.setWrapText(true);
		this.maxMinChoiceBox.getItems().clear();
		this.maxMinChoiceBox.getItems().add("minHeapRestore");
		this.maxMinChoiceBox.getItems().add("maxHeapRestore");
		this.maxMinChoiceBox.setValue("minHeapRestore");
	}

	@Override //Creo i vettori ordinati. Con la creazione degli Heap creo anche la lista di tutti i passaggi effettuati
	public void generateHeap() {
		if(this.maxMinChoiceBox.getValue() != null) {
			if(this.maxMinChoiceBox.getValue() == "maxHeapRestore") {
				maxBuildHeap = new MaxHeap();
				maxBuildHeap.maxHeapBuild(this.dataVector);
			}
			else {
				minBuildHeap = new MinHeap();
				minBuildHeap.minHeapBuild(this.dataVector);
			}
		}
	}
	
	@Override //Funzione invariata
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

	@Override //Funzione invariata
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


}
