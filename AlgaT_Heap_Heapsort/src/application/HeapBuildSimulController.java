package application;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class HeapBuildSimulController extends HeapRestoreSimulController {
	
	@FXML
	private Button manualButton;
	
	@Override
	public void initialize(){
		 super.initialize();
		 this.defaultMessage = "Scegli dalla tendina sottostante la simulazione che vuoi effettuare, poi fai click su Build per far partire la procedura!";
		 this.infoText.setText("Premi su Random per creare un vettore composto da numeri casuali.\nOppure aggiungi manualmente numeri premendo Add.");
	}
	
	public void manualAdd(){
		this.readyButton.setDisable(false);
		this.removeButton.setDisable(false);
		//Controlla che non vada oltre la dimensione massima
		if (this.dataVector.size() < this.MAX_VECTOR_SIZE) {
			
			try {
				Integer num = Integer.parseInt(this.inputArea.getText());	//Prende l'input dalla inputArea
				this.dataVector.add(num);									//Aggiunge al campo del vettore
				this.drawVector();											//Aggiorna la grafica del vettore
				this.maxMinChoiceBox.setDisable(false);
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
	
	public ArrayList<ArrayList<Integer>> stepByStepMaxHeapBuild(ArrayList<Integer> vector) {
		ArrayList<ArrayList<Integer>> statusList = new ArrayList<ArrayList<Integer>>();
		
		ArrayList<Integer> v = new ArrayList<Integer>();
		v.addAll(vector);
		statusList.add(v);
		
		this.instructionList.add("Prima di tutto si applica maxHeapRestore su tutti i nodi non foglia.");
		this.lightableIndex.add(null);
		
		
		//MaxHeapRestore su tutte le foglie
		for(Integer index = (vector.size()/2-1); index >= 0; index--) {
			
			//Append della lista di status ottenuta tramite heapRestore
			ArrayList<ArrayList<Integer>> sL = new ArrayList<ArrayList<Integer>>();
			
			sL = this.stepByStepMaxRestore(vector, index);
			statusList.addAll(sL);
		}		
		
		ArrayList<Integer> vv = new ArrayList<Integer>();
		vv.addAll(vector);
		statusList.add(vv);
		this.instructionList.add("L'operazione heapBuild termina e ora il vettore � un heap.");
		this.lightableIndex.add(null);
		
		return statusList;
	}
	
	public ArrayList<ArrayList<Integer>> stepByStepMinHeapBuild(ArrayList<Integer> vector) {
		ArrayList<ArrayList<Integer>> statusList = new ArrayList<ArrayList<Integer>>();
		
		ArrayList<Integer> v = new ArrayList<Integer>();
		v.addAll(vector);
		statusList.add(v);
		
		this.instructionList.add("Prima di tutto si applica minHeapRestore su tutti i nodi non foglia.");
		this.lightableIndex.add(null);
		
		
		//MaxHeapRestore su tutte le foglie
		for(Integer index = (vector.size()/2-1); index >= 0; index--) {
	
			//Append della lista di status ottenuta tramite heapRestore
			ArrayList<ArrayList<Integer>> sL = new ArrayList<ArrayList<Integer>>();
			
			sL = this.stepByStepMinRestore(vector, index);
			statusList.addAll(sL);
		}		
		
		ArrayList<Integer> vv = new ArrayList<Integer>();
		vv.addAll(vector);
		statusList.add(vv);
		this.instructionList.add("L'operazione heapBuild termina e ora il vettore � un heap.");
		this.lightableIndex.add(null);
		
		return statusList;
	}
	
	@Override
	//Pulsante build, genera la successione di step-by-step
		public void generateSteps() {
			if((this.maxMinChoiceBox.getValue() != null)) {
				this.maxMinChoiceBox.setDisable(true);
				ArrayList<ArrayList<Integer>> vector = new ArrayList<ArrayList<Integer>>();
				
				this.instructionList.clear();
				this.lightableIndex.clear();
				
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
				this.infoText.setText(this.instructionList.get(this.currentStatusIndex));
				this.drawVector();
				this.drawTree();
				this.nextButton.setDisable(false);
				this.selectable = false;
				}
				
			else  {
				Alert alert = new Alert(AlertType.WARNING, "Devi selezionare un elemento dal menu a tenda.");
				alert.showAndWait();
				this.maxMinChoiceBox.setDisable(false);
			}
		}
}
