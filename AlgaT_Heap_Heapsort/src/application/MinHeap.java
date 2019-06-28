package application;

import java.util.ArrayList;
import java.util.Collections;

public class MinHeap {
	
	private ArrayList<Integer> heapVector;
	
	//Minheaprestore applicata a partire dalle foglie (quindi a partire da size/2-1 considerando che partiamo da 0)
	public ArrayList<Integer> minHeapBuild(ArrayList<Integer> vector){
		this.heapVector = new ArrayList<Integer>();
		this.heapVector.addAll(vector);
		
		for(Integer index = this.heapVector.size()/2-1; index >= 0; index--) {
			this.minHeapRestore(this.heapVector, index);		
		}
		
		return this.heapVector;
	}
	
	
	//Nei seguenti metodi l'indexing � un po' differente perch� il vettore parte da 0 e non da 1 come nella teoria//
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
			minHeapRestore(this.heapVector, min);			
		}
		
	}

}