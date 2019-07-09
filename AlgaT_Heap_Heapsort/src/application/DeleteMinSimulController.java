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

public class DeleteMinSimulController extends HeapRestore {
	
	@FXML
	private Button manualButton;
	
	@Override
	public void initialize(){
		 super.initialize();
		 this.restoreButton.setVisible(false);
		 this.maxMinChoiceBox.setVisible(false);
		 this.infoText.appendText("\nOppure aggiungi manualmente numeri premendo Add.");
	}
	
	@Override
	public void addToVector(){
		MinHeap heap = new MinHeap();
		this.dataVector = heap.minHeapBuild(this.randomVector());
		this.readyButton.setDisable(false);
		this.drawVector();
		this.drawTree();
	}
	
	public void manualAdd(){
		this.readyButton.setDisable(false);
		this.removeButton.setDisable(false);
		//Controlla che non vada oltre la dimensione massima
		if (this.dataVector.size() < this.MAX_VECTOR_SIZE) {
			
			try {
				Integer num = Integer.parseInt(this.inputArea.getText());	//Prende l'input dalla inputArea
				MinHeap heap = new MinHeap();
				this.dataVector.add(num);					
				this.dataVector = heap.minHeapBuild(this.dataVector);
				this.drawVector();											
			} catch (NumberFormatException e) {
				Alert alert = new Alert(AlertType.INFORMATION, "Inserisci un numero intero.");
				alert.showAndWait();
			}
			this.inputArea.clear();		
			this.isGenerated = false;
		}
		else {
			Alert alert = new Alert(AlertType.INFORMATION, "Dimensione massima del vettore raggiunta.");
			alert.showAndWait();
		}
	}
	
	@Override
	public void nextStatus(){
		this.currentStatusIndex++;
		this.prevButton.setDisable(false);
		this.manualButton.setDisable(true);
		Integer size = this.statusList.size();
		if(this.currentStatusIndex < this.statusList.size()) {
			Integer k = this.statusList.size();
			this.dataVector = new ArrayList<Integer>();
			this.dataVector.addAll(this.statusList.get(this.currentStatusIndex));
			drawVector();
			this.drawTree();
		} else {
			this.infoText.setText("Passaggi completati. Genera una nuova PriorityQueue o avvia di nuovo la funzione deleteMin() per continuare.");
			this.addButton.setDisable(false);
			this.nextButton.setDisable(true);
			this.removeButton.setDisable(false);
			this.manualButton.setDisable(false);
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
	
	@Override
	public void prevStatus(){
		this.currentStatusIndex--;
		if(this.currentStatusIndex == 0) {
			this.dataVector = new ArrayList<Integer>();
			this.dataVector.addAll(this.statusList.get(this.currentStatusIndex));
			this.prevButton.setDisable(true);
			this.addButton.setDisable(true);
			this.removeButton.setDisable(true);
			this.nextButton.setDisable(false);
			this.manualButton.setDisable(true);
			this.drawVector();
			this.drawTree();
		} else {
			this.dataVector = new ArrayList<Integer>();
			this.dataVector.addAll(this.statusList.get(this.currentStatusIndex));
			this.nextButton.setDisable(false);
			this.addButton.setDisable(true);
			this.removeButton.setDisable(true);
			this.manualButton.setDisable(true);
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
	
	@Override
	public void readyVector(){
		if(this.dataVector.size() >= 1) {
			this.currentStatusIndex = 0;
			this.manualButton.setDisable(true);
			this.addButton.setDisable(true);
			this.removeMin();
		}
		else {
			this.infoText.setText("Crea prima una Priority List inserento manualmente valori o generandonene una in modo casuale.");
			this.removeButton.setDisable(true);
			this.addButton.setDisable(false);
			this.readyButton.setDisable(false);
			this.nextButton.setDisable(true);
			this.prevButton.setDisable(true);
		}
	}
	
	public void removeMin() {
		this.lightableIndex.clear();
		
		this.lightableIndex = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> lightable = new ArrayList<Integer>();
		ArrayList<String> instruction = new ArrayList<String>();
		ArrayList<ArrayList<Integer>> sL = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> v = new ArrayList<Integer>();
		ArrayList<Integer> l;
		ArrayList<Integer> o;
		ArrayList<Integer> p;
		if(this.dataVector.size() >= 1) {
			v.addAll(this.dataVector);
			sL.add(v);
			this.lightableIndex.add(null);
			instruction.add("Interazione iniziata. Premere avanti per visualizzare i passaggi per il completamento dell'operazione.");
			//Sostituisco l'ultimo elemento con il primo
			l = new ArrayList<Integer>();
			l.addAll(v);
			lightable = new ArrayList<Integer>();
			lightable.add(0);
			lightable.add(l.size()-1);
			this.lightableIndex.add(lightable);
			instruction.add("L'elemento con prioritá piú bassa viene scambiato con l'ultima foglia ammucchiata a sinistra. Scambio " +
						l.get(0) + " con " + l.get(l.size()-1) + ".");
			Collections.swap(l, 0, l.size()-1);
			sL.add(l);
			o = new ArrayList<Integer>();
			o.addAll(l);
			instruction.add("Rimuovo l'elemento " + o.get(l.size()-1) );
			this.lightableIndex.add(null);
			o.remove(l.size()-1);
			sL.add(o);
			p = new ArrayList<Integer>();
			p.addAll(o);
			
			//Pulisco la instructionList dai precedenti messaggi
			this.instructionList.clear();
			ArrayList<ArrayList<Integer>> l2 = new ArrayList<ArrayList<Integer>>();
			l2.addAll(this.lightableIndex);
			this.lightableIndex.clear();
			ArrayList<ArrayList<Integer>> vector = new ArrayList<ArrayList<Integer>>();
			vector = this.stepByStepMinRestore(p, 0);
			l2.addAll(this.lightableIndex);
			this.lightableIndex.clear();
			this.lightableIndex.addAll(l2);
			sL.addAll(vector);
			instruction.addAll(this.instructionList);
			this.instructionList.clear();
			this.instructionList.addAll(instruction);
			this.statusList = new ArrayList<ArrayList<Integer>>();
			this.statusList.addAll(sL);
			this.nextButton.setDisable(false);
			this.prevButton.setDisable(true);
		}
	}
}
