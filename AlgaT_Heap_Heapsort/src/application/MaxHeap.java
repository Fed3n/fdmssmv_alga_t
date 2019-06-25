package application;

import java.util.ArrayList;
import java.util.Collections;

public class MaxHeap {
	
	private ArrayList<Integer> heapVector;
	
	//Maxheaprestore applicata a partire dalle foglie (quindi a partire da size/2-1 considerando che partiamo da 0)
	public ArrayList<Integer> maxHeapBuild(ArrayList<Integer> vector){
		this.heapVector = new ArrayList<Integer>();
		this.heapVector.addAll(vector);
		
		for(Integer index = this.heapVector.size()/2-1; index >= 0; index--) {
			this.maxHeapRestore(this.heapVector, index);		
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
	
	//Funzione maxheaprestore come da libro
	public void maxHeapRestore(ArrayList<Integer> vector, Integer index) {
		Integer max = index;
		
		if((this.lChild(index) < this.heapVector.size()) && (this.heapVector.get(this.lChild(index)) > this.heapVector.get(max)))
			max = this.lChild(index);
		if((this.rChild(index) < this.heapVector.size()) && (this.heapVector.get(this.rChild(index)) > this.heapVector.get(max)))
			max = this.rChild(index);
		if(index != max) {
			Collections.swap(this.heapVector, index, max);
			maxHeapRestore(this.heapVector, max);			
		}
		
	}

}
