package application;

import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class HeapBuildSimulController extends HeapRestoreSimulController {
	
	public ArrayList<ArrayList<Integer>> stepByStepMaxHeapBuild(ArrayList<Integer> vector) {
		ArrayList<ArrayList<Integer>> statusList = new ArrayList<ArrayList<Integer>>();
		
		ArrayList<Integer> v = new ArrayList<Integer>();
		v.addAll(vector);
		statusList.add(v);
		System.out.println("Aggiungo " + v.toString());
		
		this.instructionList.add("Prima di tutto si applica maxHeapRestore su tutti i nodi non foglia.");
		
		
		//MaxHeapRestore su tutte le foglie
		for(Integer index = (vector.size()/2-1); index >= 0; index--) {
			
			//Append della lista di status ottenuta tramite heapRestore
			ArrayList<ArrayList<Integer>> sL = new ArrayList<ArrayList<Integer>>();
			
			System.out.println("Vettore prima: " + vector.toString());
			sL = this.stepByStepMaxRestore(vector, index);
			System.out.println("Vettore dopo: " + vector.toString());
			statusList.addAll(sL);
		}		
		
		ArrayList<Integer> vv = new ArrayList<Integer>();
		vv.addAll(vector);
		statusList.add(vv);
		this.instructionList.add("L'operazione heapBuild termina e ora il vettore è un heap.");
		
		return statusList;
	}
	
	public ArrayList<ArrayList<Integer>> stepByStepMinHeapBuild(ArrayList<Integer> vector) {
		ArrayList<ArrayList<Integer>> statusList = new ArrayList<ArrayList<Integer>>();
		
		ArrayList<Integer> v = new ArrayList<Integer>();
		v.addAll(vector);
		statusList.add(v);
		System.out.println("Aggiungo " + v.toString());
		
		this.instructionList.add("Prima di tutto si applica minHeapRestore su tutti i nodi non foglia.");
		
		
		//MaxHeapRestore su tutte le foglie
		for(Integer index = (vector.size()/2-1); index >= 0; index--) {
	
			//Append della lista di status ottenuta tramite heapRestore
			ArrayList<ArrayList<Integer>> sL = new ArrayList<ArrayList<Integer>>();
			
			System.out.println("Vettore prima: " + vector.toString());
			sL = this.stepByStepMinRestore(vector, index);
			System.out.println("Vettore dopo: " + vector.toString());
			statusList.addAll(sL);
		}		
		
		ArrayList<Integer> vv = new ArrayList<Integer>();
		vv.addAll(vector);
		statusList.add(vv);
		this.instructionList.add("L'operazione heapBuild termina e ora il vettore è un heap.");
		
		return statusList;
	}
	
	@Override
	//Pulsante build, genera la successione di step-by-step
		public void generateSteps() {
			if((this.maxMinChoiceBox.getValue() != null)) {
				ArrayList<ArrayList<Integer>> vector = new ArrayList<ArrayList<Integer>>();
				
				this.instructionList.clear();
				
				if(this.maxMinChoiceBox.getValue().contentEquals("MaxHeap")) {
					vector = this.stepByStepMaxHeapBuild(this.dataVector);
				}
				else {
					vector = this.stepByStepMinHeapBuild(this.dataVector);
				}
				//Assegno il vettore di status generato al suo campo
				this.statusList = vector;
				this.printVector(vector);	
					
				//Poi resetto la schermata dell'interazione
				this.currentStatusIndex = 0;
				this.selectedIndex = null;
				this.dataVector = this.statusList.get(this.currentStatusIndex);
				this.infoText.setText("Premi Avanti per proseguire con la simulazione.");
				this.drawVector();
				this.drawTree();
				this.nextButton.setDisable(false);
				this.selectable = false;
					
				System.out.println(this.instructionList.size());
				System.out.println(this.statusList.size());
				}
				
			else  {
				Alert alert = new Alert(AlertType.WARNING, "Devi selezionare un elemento dal menu a tenda.");
				alert.showAndWait();
			}
		}
	
}
