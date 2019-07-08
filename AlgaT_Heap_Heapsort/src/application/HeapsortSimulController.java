package application;

import java.util.ArrayList;
import java.util.Collections;


public class HeapsortSimulController extends HeapRestoreSimulController {
	
	public ArrayList<ArrayList<Integer>> stepByStepHeapsort(ArrayList<Integer> vector) {
		ArrayList<ArrayList<Integer>> statusList = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> l = new ArrayList<Integer>();
		ArrayList<Integer> v = new ArrayList<Integer>();
		v.addAll(vector);
		statusList.add(v);
		this.instructionList.add("Ordiniamo il vettore in input tramite heapSort.");
		this.lightableIndex.add(null);
		
		//Rendo il vettore un max heap con heapBuild, non mostro i passaggi per non allungare troppo
		MaxHeap heap = new MaxHeap();
		vector = heap.maxHeapBuild(vector);
		v = new ArrayList<Integer>();
		v.addAll(vector);
		statusList.add(v);
		this.instructionList.add("Uso maxHeapBuild sul vettore per ottenere un max heap.");
		this.lightableIndex.add(null);
		
		//Mi serve un vettore di appoggio per gli elementi già ordinati
		ArrayList<Integer> ordered = new ArrayList<Integer>();
		
		for(Integer index = vector.size()-1; index > 0; index--) {
			Collections.swap(vector, index, 0);
			
			v = new ArrayList<Integer>();
			v.addAll(vector);
			v.addAll(ordered);
			statusList.add(v);
			this.instructionList.add("Scambio il primo elemento con l'ultimo nel vettore (da cui escludo gli elementi già ordinati) e applico maxHeapRestore sul nuovo primo.");
			l = new ArrayList<Integer>();
			l.add(0);
			l.add(vector.size()-1);
			this.lightableIndex.add(l);
			
			//Prendo l'ultimo elemento, già ordinato, lo rimuovo e lo aggiungo alla lista degli elementi ordinati
			Integer tmp = vector.get(index);
			//Devo per forza castare l'indice ad int sennò cerca un oggetto Integer == index invece di rimuovere l'indice corrispondente
			vector.remove((int)index);
			ordered.add(0, tmp);
			
			ArrayList<ArrayList<Integer>> resultVector = new ArrayList<ArrayList<Integer>>();
			//maxHeapRestore sul vettore meno l'ultimo elemento
			resultVector = this.stepByStepMaxRestore(vector, 0, ordered);
			statusList.addAll(resultVector);
		}		
		statusList.add(statusList.get(statusList.size()-1));
		this.instructionList.add("Heapsort termina e il vettore è ordinato.");
		this.lightableIndex.add(null);
		
		return statusList;
	}
	
	//Versione alternativa in cui prendo in input la lista di elementi cancellati dalla prima per continuare a rappresentarli
	//La funzione crea la lista di status del vettore con una maxHeapRestore step-by-step
		public ArrayList<ArrayList<Integer>> stepByStepMaxRestore(ArrayList<Integer> vector, Integer index, ArrayList<Integer> ordered) {
			ArrayList<ArrayList<Integer>> statusList = new ArrayList<ArrayList<Integer>>();
			ArrayList<Integer> l = new ArrayList<Integer>();
			Boolean finished = false;
			
			//Per come funzionano gli oggetti in java devo creare un nuovo vettore ogni volta che aggiorno la lista di vettori
			ArrayList<Integer> v = new ArrayList<Integer>();
			v.addAll(vector);
			v.addAll(ordered);
			statusList.add(v);
			this.instructionList.add("Applichiamo maxHeapRestore sul nodo in posizione " + (index+1));
			l.add(index);
			this.lightableIndex.add(l);
			
			while(!finished) {
				Integer max = index;
				
				if(!(index >= vector.size()/2)){	
					//Figlio sinistro > figlio destro, prima controlla che esista un figlio destro
					if((this.rChild(index) >= vector.size()) || vector.get(this.lChild(index)) >= vector.get(this.rChild(index))){
						//Se il figlio più grande è maggiore del padre, si scambiano
						if(vector.get(index) < vector.get(this.lChild(index))) {
							this.instructionList.add("Il figlio sinistro " + vector.get(this.lChild(index)) + " è maggiore del padre " + vector.get(index) + ", quindi vengono scambiati.");
							l = new ArrayList<Integer>();
							l.add(index);
							l.add(this.lChild(index));
							this.lightableIndex.add(l);
							Collections.swap(vector, index, this.lChild(index));
							max = this.lChild(index);
						}				
					}
				
					else {
						//Se il figlio più grande è maggiore del padre, si scambiano
						if(vector.get(index) < vector.get(this.rChild(index))) {
							this.instructionList.add("Il figlio destro " + vector.get(this.rChild(index)) + " è maggiore del padre " + vector.get(index) + ", quindi vengono scambiati.");
							l = new ArrayList<Integer>();
							l.add(index);
							l.add(this.rChild(index));
							this.lightableIndex.add(l);
							Collections.swap(vector, index, this.rChild(index));
							max = this.rChild(index);
						}
					}	
				}		
				
				//Se non c'è stato da scambiare l'operazione è terminata
				if(index == max) {
					finished = true;
					v = new ArrayList<Integer>();
					v.addAll(vector);
					v.addAll(ordered);
					statusList.add(v);
					
					this.instructionList.add("Non c'è nulla da scambiare. maxHeapRestore termina.");
					this.lightableIndex.add(null);
					//TODO Evidenza che non c'è più da scambiare
					}
				//L'operazione procede sull'indice
				else {
					v = new ArrayList<Integer>();
					v.addAll(vector);
					v.addAll(ordered);
					statusList.add(v);
					index = max;
				}	
				this.printVector(statusList);
				System.out.println("@@@@@@@@@@@@@@@@");
			}
			return statusList;
		}
	
	@Override
	//Pulsante build, genera la successione di step-by-step
		public void generateSteps() {

			ArrayList<ArrayList<Integer>> vector = new ArrayList<ArrayList<Integer>>();
			
			this.instructionList.clear();
			
			vector = this.stepByStepHeapsort(this.dataVector);
			//Assegno il vettore di status generato al suo campo
			this.statusList = vector;
			
			//Poi resetto la schermata dell'interazione
			this.currentStatusIndex = 0;
			this.selectedIndex = null;
			this.dataVector = this.statusList.get(this.currentStatusIndex);
			this.infoText.setText(this.instructionList.get(this.currentStatusIndex));
			this.drawVector();
			this.drawTree();
			this.nextButton.setDisable(false);
			this.selectable = false;
				
			System.out.println("Dimensione stringhe: " + this.instructionList.size());
			System.out.println("Dimensione vettori: " + this.statusList.size());
				

		}
	
	

}
