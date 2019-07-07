package application;

import java.util.ArrayList;
import java.util.Collections;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DeleteSimulController extends HeapRestoreSimulController {
	
	@Override
	public void initialize() {
		super.initialize();
		this.infoText.setEditable(false);
		this.infoText.setWrapText(true);	
		this.currentStatusIndex = 0;
		this.selectedIndex = 0;
		this.maxMinChoiceBox.setValue("MinHeap");
		this.maxMinChoiceBox.setDisable(true);
		this.infoText.setText("Premi su genera per creare un vettore composto da numeri casuali.");
		this.instructionList = new ArrayList<String>();
		this.lightableIndex = new ArrayList<ArrayList<Integer>>();
	}
	
	@Override
	public void addToVector(){
		MinHeap heap = new MinHeap();
		this.dataVector = heap.minHeapBuild(this.randomVector());
		this.drawVector();
		this.drawTree();
	}
	
	public void removeMin() {
		ArrayList<ArrayList<Integer>> sL = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> v = new ArrayList<Integer>();
		ArrayList<Integer> l;
		ArrayList<Integer> o;
		ArrayList<Integer> p;
		System.out.println("Vettore al momento: " + this.dataVector.toString());
		if(this.dataVector.size() >= 1) {
			v.addAll(this.dataVector);
			System.out.println("Vector: " + v.toString());
			sL.add(v);
			//Sostituisco l'ultimo elemento con il primo
			l = new ArrayList<Integer>();
			l.addAll(v);
			Collections.swap(l, 0, l.size()-1);
			System.out.println("Vector scambiato: " + l.toString());
			sL.add(l);
			o = new ArrayList<Integer>();
			o.addAll(l);
			o.remove(l.size()-1);
			System.out.println("Vector con elemento eliminato: " + o.toString());
			sL.add(o);
			System.out.println(sL.toString());
			p = new ArrayList<Integer>();
			p.addAll(o);
			
			//Pulisco la instructionList dai precedenti messaggi
			this.instructionList.clear();
			this.lightableIndex.clear();
			
			ArrayList<ArrayList<Integer>> vector = new ArrayList<ArrayList<Integer>>();
			vector = this.stepByStepMinRestore(p, 0);
			sL.addAll(vector);
			System.out.println("Tutti i passaggi: " + sL.toString());
			this.dataVector = new ArrayList<Integer>();
			this.dataVector.addAll(sL.get(sL.size()-1));
			this.drawVector();
			this.drawTree();
			this.nextButton.setDisable(false);
			this.prevButton.setDisable(false);
		}
	}
}
