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

public class HeapSimulatorSBController {

    @FXML
    private Pane vectorPane;

    //Tutti i nodi dell'albero
    @FXML
    private Circle c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15;
    
    @FXML
    private Label l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12,l13,l14,l15;
    
    @FXML
    private Line f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f11,f12,f13,f14;
    
    //Tutti i livelli in cui possono trovarsi nodi dell'albero (max 4 = 15 elementi)
    @FXML
    private Pane treePane;

    @FXML
    private Pane treeLevel0;

    @FXML
    private Pane treeLevel1;

    @FXML
    private Pane treeLevel2;

    @FXML
    private Pane treeLevel3;

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
		
		//Rendo l'albero invisibile
		this.hideTree();
		
		this.dataVector = new ArrayList<Integer>();
		this.maxMinChoiceBox.getItems().add("MaxHeap");
		this.maxMinChoiceBox.getItems().add("MinHeap");
	}
	
	//Nascondo tutti gli elemtni dell'albero
	public void hideTree(){
		//Cerchi
		this.c1.setVisible(false);
		this.c2.setVisible(false);
		this.c3.setVisible(false);
		this.c3.setVisible(false);
		this.c4.setVisible(false);
		this.c5.setVisible(false);
		this.c6.setVisible(false);
		this.c7.setVisible(false);
		this.c8.setVisible(false);
		this.c9.setVisible(false);
		this.c10.setVisible(false);
		this.c11.setVisible(false);
		this.c12.setVisible(false);
		this.c13.setVisible(false);
		this.c14.setVisible(false);
		this.c15.setVisible(false);
		//Scritte (label)
		this.l1.setVisible(false);
		this.l2.setVisible(false);
		this.l3.setVisible(false);
		this.l3.setVisible(false);
		this.l4.setVisible(false);
		this.l5.setVisible(false);
		this.l6.setVisible(false);
		this.l7.setVisible(false);
		this.l8.setVisible(false);
		this.l9.setVisible(false);
		this.l10.setVisible(false);
		this.l11.setVisible(false);
		this.l12.setVisible(false);
		this.l13.setVisible(false);
		this.l14.setVisible(false);
		this.l15.setVisible(false);
		//Linee
		this.f1.setVisible(false);
		this.f2.setVisible(false);
		this.f3.setVisible(false);
		this.f3.setVisible(false);
		this.f4.setVisible(false);
		this.f5.setVisible(false);
		this.f6.setVisible(false);
		this.f7.setVisible(false);
		this.f8.setVisible(false);
		this.f9.setVisible(false);
		this.f10.setVisible(false);
		this.f11.setVisible(false);
		this.f12.setVisible(false);
		this.f13.setVisible(false);
		this.f14.setVisible(false);
	}

	public void drawVector() {
		
		this.vectorHbox.getChildren().clear();	//Prima rimuovo il vettore già disegnato

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
				System.out.println("Please input a number");
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
		//Prima rimuovo l'albero già disegnato
		this.hideTree();
		
		this.nodeVector = new ArrayList<StackPane>();	//Inizializzo la lista di nodi dell'albero
		Integer index = 0;

		//Itero sul vettore e disegno i suoi membri in ordine come albero binario partendo dalla radice
		Iterator<Integer> heapIterator = this.dataVector.iterator();
		while(heapIterator.hasNext()) {
			Integer num = heapIterator.next();

			//TODO Eventuali eventi relativi all'interazione col nodo

			//Aggiungo all'HBox relativo al livello dell'albero a seconda della loro posizione nel vettore
			if(index == 0) {
				c1.setVisible(true);
				l1.setVisible(true);
				l1.setText(num.toString());
			}
			else if(index == 1) {
				c2.setVisible(true);
				l2.setVisible(true);
				f1.setVisible(true);
				l2.setText(num.toString());
			}
			else if(index == 2) {
				c3.setVisible(true);
				l3.setVisible(true);
				f2.setVisible(true);
				l3.setText(num.toString());
			}
			else if(index == 3) {
				c4.setVisible(true);
				l4.setVisible(true);
				f3.setVisible(true);
				l4.setText(num.toString());
			}
			else if(index == 4) {
				c5.setVisible(true);
				l5.setVisible(true);
				f4.setVisible(true);
				l5.setText(num.toString());
			}
			else if(index == 5) {
				c6.setVisible(true);
				l6.setVisible(true);
				f5.setVisible(true);
				l6.setText(num.toString());
			}
			else if(index == 6) {
				c7.setVisible(true);
				l7.setVisible(true);
				f6.setVisible(true);
				l7.setText(num.toString());
			}
			else if(index == 7) {
				c8.setVisible(true);
				l8.setVisible(true);
				f7.setVisible(true);
				l8.setText(num.toString());
			}
			else if(index == 8) {
				c9.setVisible(true);
				l9.setVisible(true);
				f8.setVisible(true);
				l9.setText(num.toString());
			}
			else if(index == 9) {
				c10.setVisible(true);
				l10.setVisible(true);
				f9.setVisible(true);
				l10.setText(num.toString());
			}
			else if(index == 10) {
				c11.setVisible(true);
				l11.setVisible(true);
				f10.setVisible(true);
				l11.setText(num.toString());
			}
			else if(index == 11) {
				c12.setVisible(true);
				l12.setVisible(true);
				f11.setVisible(true);
				l12.setText(num.toString());
			}
			else if(index == 12) {
				c13.setVisible(true);
				l13.setVisible(true);
				f12.setVisible(true);
				l13.setText(num.toString());
			}
			else if(index == 13) {
				c14.setVisible(true);
				l14.setVisible(true);
				f13.setVisible(true);
				l14.setText(num.toString());
			}
			else if(index == 14) {
				c15.setVisible(true);
				l15.setVisible(true);
				f14.setVisible(true);
				l15.setText(num.toString());
			}
			index++;
		}

	}



}
