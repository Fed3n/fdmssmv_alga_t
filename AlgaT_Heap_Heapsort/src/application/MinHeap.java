package application;

import java.util.ArrayList;
import java.util.Collections;

public class MinHeap {
	
	private ArrayList<Integer> heapVector;
	
	//Lista di operazioni per completare il MinHeapRestore
	private ArrayList<ArrayList<Integer> > sequentialOperations;
	
	//Restituisce l'intera lista di Vettori
	public ArrayList<ArrayList<Integer> > getFullList(){
		return(this.sequentialOperations);		
	}
	
	//Restituisce la lista di vettori creati ad ogni passo
	public ArrayList<Integer> getVector(Integer indexNumber){
		return(this.sequentialOperations.get(indexNumber));
	}
	
	
	//Minheaprestore applicata a partire dalle foglie (quindi a partire da size/2-1 considerando che partiamo da 0)
	public ArrayList<Integer> minHeapBuild(ArrayList<Integer> vector){
		this.heapVector = new ArrayList<Integer>();
		this.heapVector.addAll(vector);
		System.out.println("Ho creato il primo vettore che contiene " + this.heapVector.toString());
		this.sequentialOperations = new ArrayList<ArrayList<Integer> >();
		System.out.println("Ho creato la lista che contiene " + this.sequentialOperations.toString());
		
		for(Integer index = this.heapVector.size()/2-1; index >= 0; index--) {
			this.sequentialOperations.add(this.heapVector);
			System.out.println("Sono nel for. Adesso la dimensione della lista di Vettori é di " + this.sequentialOperations.size());
			this.minHeapRestore(this.heapVector, index);		
		}
		
		return this.heapVector;
	}
	
	
	//Nei seguenti metodi l'indexing è un po' differente perché il vettore parte da 0 e non da 1 come nella teoria//
	private Integer parent(Integer index) {
		return ((index-1)/2);
	}
	
	private Integer lChild(Integer index) {
		return (index*2+1);
	}
	
	private Integer rChild(Integer index) {
		return (index*2+2);
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//Funzione minheaprestore come da libro
	public void minHeapRestore(ArrayList<Integer> vector, Integer index) {
		Integer min = index;
		if((this.lChild(index) < this.heapVector.size()) && (this.heapVector.get(this.lChild(index)) < this.heapVector.get(min)))
			min = this.lChild(index);
		if((this.rChild(index) < this.heapVector.size()) && (this.heapVector.get(this.rChild(index)) < this.heapVector.get(min)))
			min = this.rChild(index);
		if(index != min) {
			Collections.swap(this.heapVector, index, min);
			this.sequentialOperations.add(this.heapVector);
			System.out.println("Sono nel restore. Adesso la dimensione della lista di Vettori é di " + this.sequentialOperations.size());
			minHeapRestore(this.heapVector, min);			
		}
		
	}

}
