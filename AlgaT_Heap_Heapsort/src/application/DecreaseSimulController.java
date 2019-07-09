package application;

import java.util.ArrayList;
import java.util.Collections;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;

public class DecreaseSimulController extends HeapRestore {
	
	@FXML
	private Button decreaseButton;
	
	@Override
	public void initialize() {
		super.initialize();
		this.decreaseButton.setDisable(true);
		this.removeButton.setDisable(false);
		this.maxMinChoiceBox.setVisible(false);
		this.restoreButton.setVisible(false);
	}
	
	@Override
	public void readyVector(){
		super.readyVector();
		this.currentStatusIndex = 0;
		this.decreaseButton.setDisable(false);
		this.infoText.setText("Selezionare il nodo su cui utilizzare la funzione di decrease, e scrivere nell'area sottostante la nuova prioritá.");
	}
	
	@Override
	public void addToVector(){
		super.addToVector();
		this.decreaseButton.setDisable(true);
		MinHeap heap = new MinHeap();
		ArrayList<Integer> vector = new ArrayList<Integer>();
		vector.addAll(this.dataVector);
		this.dataVector = new ArrayList<Integer>();
		this.dataVector.addAll(heap.minHeapBuild(vector));
		this.drawVector();
	}
	
	public void decrease(){
		try {
			Integer num = Integer.parseInt(this.inputArea.getText());	//Prende l'input dalla inputArea
			this.addButton.setDisable(true);
			this.removeButton.setDisable(true);
			this.readyButton.setDisable(true);
			this.prevButton.setDisable(true);
			this.nextButton.setDisable(true);
			if(num < this.dataVector.get(this.selectedIndex)) {
				this.decreaseButton.setDisable(true);
				generateVector(num);	
			}
			else {
				this.readyButton.setDisable(false);
				this.infoText.setText("Premi Ready per riprovare.");
				this.currentStatusIndex = 0;
				Alert alert = new Alert(AlertType.INFORMATION, "Inserisci un numero inferiore a quello selezionato dall'albero.");
				alert.showAndWait();
			}
		} catch (NumberFormatException e) {
			this.readyButton.setDisable(false);
			this.infoText.setText("Premi Ready per riprovare.");
			this.currentStatusIndex = 0;
			Alert alert = new Alert(AlertType.INFORMATION, "Inserisci un numero intero.");
			alert.showAndWait();
		}
		this.inputArea.clear();
		this.isGenerated = false;
	}
	
	public void generateVector(Integer n) {
		this.lightableIndex.clear();
		this.instructionList.clear();
		
		if(this.selectedIndex != null) {
			ArrayList<ArrayList<Integer>> vector = new ArrayList<ArrayList<Integer>>();
			ArrayList<String> instruction = new ArrayList<String>();
			this.lightableIndex = new ArrayList<ArrayList<Integer>>();
			this.instructionList = new ArrayList<String>();
			ArrayList<Integer> l = new ArrayList<Integer>();
			ArrayList<Integer> lightable = new ArrayList<Integer>();
			instruction.add("Premi Avanti per iniziare la simulazione");
			vector.add(this.dataVector);
			l.addAll(this.dataVector);
			this.lightableIndex.add(null);
			lightable = new ArrayList<Integer>();
			lightable.add(this.selectedIndex);
			this.lightableIndex.add(lightable);
			instruction.add("Decremento la prioritá dell'elemento selezionato da " + l.get(this.selectedIndex) + " a " + n);
			l.set(this.selectedIndex, n);
			vector.add(l);
			this.dataVector = new ArrayList<Integer>();
			this.dataVector.addAll(l);
			Integer i = this.selectedIndex;
			while (i > 0 && this.dataVector.get(i) < this.dataVector.get(super.parent(i))) {
				l = new ArrayList<Integer>();
				l.addAll(this.dataVector);
				lightable = new ArrayList<Integer>();
				lightable.add(i);
				lightable.add(super.parent(i));
				this.lightableIndex.add(lightable);
				instruction.add(this.dataVector.get(i) + " é piú piccolo di " + this.dataVector.get(super.parent(i)) +" quindi vengono scambiati.");
				Collections.swap(this.dataVector, i, super.parent(i));
				Collections.swap(l, i, super.parent(i));
				vector.add(l);
				i = super.parent(i);
			}
			this.statusList = new ArrayList<ArrayList<Integer>>();
			this.statusList.addAll(vector);
			this.instructionList.addAll(instruction);
			this.selectedIndex = null;
			this.nextButton.setDisable(false);
		} else {
			this.currentStatusIndex = 0;
			this.readyButton.setDisable(false);
			Alert alert = new Alert(AlertType.INFORMATION, "Devi premere un nodo o una foglia da decrementarne la prioritá.");
			alert.showAndWait();
		}
	}
	
	public void next(){
		this.currentStatusIndex++;
		this.prevButton.setDisable(false);
		if(this.currentStatusIndex < this.statusList.size()) {
			this.dataVector = new ArrayList<Integer>();
			this.dataVector.addAll(this.statusList.get(this.currentStatusIndex));
			this.drawVector();
			this.drawTree();
		} else {
			this.infoText.setText("Passaggi completati, aggiungi un altro elemento e premi su genera.");
			this.addButton.setDisable(false);
			this.decreaseButton.setDisable(true);
			this.nextButton.setDisable(true);
			this.removeButton.setDisable(false);
			this.readyButton.setDisable(false);
		}
		if(this.currentStatusIndex > 0 && this.currentStatusIndex < this.statusList.size()) {
			ArrayList<Integer> i = new ArrayList<Integer>();
			i = this.lightableIndex.get(this.currentStatusIndex);
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
		if(this.currentStatusIndex == 0) {
			this.dataVector = new ArrayList<Integer>();
			this.dataVector.addAll(this.statusList.get(this.currentStatusIndex));
			this.prevButton.setDisable(true);
			this.addButton.setDisable(true);
			this.removeButton.setDisable(true);
			this.nextButton.setDisable(false);
			this.readyButton.setDisable(true);
			this.decreaseButton.setDisable(true);
			this.drawVector();
			this.drawTree();
		} else {
			this.dataVector = new ArrayList<Integer>();
			this.dataVector.addAll(this.statusList.get(this.currentStatusIndex));
			this.nextButton.setDisable(false);
			this.addButton.setDisable(true);
			this.removeButton.setDisable(true);
			this.readyButton.setDisable(true);
			this.decreaseButton.setDisable(true);
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
