package application;

import java.util.ArrayList;
import java.util.Collections;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class QueueDecreaseSimulController extends HeapRestoreSimulController {
	
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
		this.decreaseButton.setDisable(false);
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
			System.out.println("Please input a number");
		}
		this.inputArea.clear();
		this.isGenerated = false;
	}
	
	public void generateVector(Integer n) {
		System.out.println("Vettore prima di tutto: " + this.dataVector.toString());
		if(this.selectedIndex != null) {
			ArrayList<ArrayList<Integer>> vector = new ArrayList<ArrayList<Integer>>();
			ArrayList<Integer> l = new ArrayList<Integer>();
			vector.add(this.dataVector);
			l.addAll(this.dataVector);
			l.set(this.selectedIndex, n);
			vector.add(l);
			this.dataVector = new ArrayList<Integer>();
			this.dataVector.addAll(l);
			System.out.println("StatusList con due passaggi, base e scambio: " + vector.toString());
			Integer i = this.selectedIndex;
			while (i > 0 && this.dataVector.get(i) < this.dataVector.get(super.parent(i))) {
				l = new ArrayList<Integer>();
				l.addAll(this.dataVector);
				Collections.swap(this.dataVector, i, super.parent(i));
				Collections.swap(l, i, super.parent(i));
				vector.add(l);
				System.out.println(l.toString() + " aggiunto a statuslist");
				i = super.parent(i);
			}
			this.statusList = new ArrayList<ArrayList<Integer>>();
			this.statusList.addAll(vector);
			System.out.println("Status list completa: " + this.statusList.toString());
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
			this.decreaseButton.setDisable(true);
			this.nextButton.setDisable(true);
			this.removeButton.setDisable(false);
			this.readyButton.setDisable(false);
		}
	}
	
	public void prev() {
		this.currentStatusIndex--;
		System.out.println(this.statusList.toString());
		System.out.println("Index: " + this.currentStatusIndex);
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
	}
	
}
