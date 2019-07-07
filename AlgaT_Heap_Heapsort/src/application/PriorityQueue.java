package application;

import java.util.ArrayList;
import java.util.Collections;

public class PriorityQueue {
	
	private ArrayList<Integer> H;	//Elemento con prioritá
	
	private String tipology; 		//MinHeap | MaxHeap
	
	//Costruttore, ove n = alla capacitá massima della coda
	public PriorityQueue() {
		this.H = new ArrayList<Integer>();
	}
	
	//Funzione che ritorna la radice, puó essere o il Max o il Min della PriorityQueue
	Integer root(){
		if(this.H.size() >= 1) return(this.H.get(0));
		else return 0;
	}
	
	Integer insert(Integer x){
		if(this.tipology.equalsIgnoreCase("MinHeap")){
			this.H.add(x);
			Integer i = this.H.size();
			while(i>1 && this.H.get(i/2) > this.H.get(i)){
				Collections.swap(this.H, i, (i/2));
				i = i/2;
			}
			return this.H.get(i);
		} else if(this.tipology.equalsIgnoreCase("MaxHeap")){
			this.H.add(x);
			Integer i = this.H.size();
			while(i>1 && this.H.get(i/2) < this.H.get(i)){
				Collections.swap(this.H, i, (i/2));
				i = i/2;
			}
			return this.H.get(i);
		} else return 0;
	}
	
}
