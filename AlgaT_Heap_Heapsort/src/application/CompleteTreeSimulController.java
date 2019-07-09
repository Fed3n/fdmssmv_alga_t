package application;


import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


public class CompleteTreeSimulController extends HeapSimul{
	
	@FXML
	private TextArea infoText;
	
	@Override
	public void initialize() {
		super.initialize();
		this.removeButton.setDisable(true);
		this.infoText.setEditable(false);
		this.infoText.setWrapText(true);
		this.infoText.setText("Inserisci un numero e premi 'Add' per generare un heap manualmente.\nOppure premi 'Random Heap' per generare un Heap in mono casuale.");
		this.maxMinChoiceBox.setValue("MaxHeap");
		this.generateButton.setDisable(true);
	}
	
	//Calcolo altezza dell'albero con log2(n)
	public Integer calculateTreeHeight() {
		return (int)(Math.log10(this.dataVector.size())/Math.log10(2));	//Cambio di base per logaritmo
	}
	
	public void resetScreen() {
		this.infoText.setText("");
		
		for(Integer index = 0; index < this.dataVector.size(); index++) {
			if((this.nodeVector.get(index).getChildren().size() > 0)) {		//Controlla se il pane del nodo dell'albero è disegnato o meno
				Circle circle = (Circle)this.nodeVector.get(index).getChildren().get(0);
				circle.setFill(Color.WHITE);
			}
			if(!(this.numVector.size() < index)) {
				Rectangle rect = (Rectangle)this.numVector.get(index).getChildren().get(0);
				rect.setFill(Color.WHITE);
			}
		}
		
	}
	
	public void highlightCompleteness() {
		String info = "";
		
		if(this.dataVector.size() > 1) {
			//Prima mostro l'altezza dell'heap
			info = info + "L'altezza di questo albero è " + this.calculateTreeHeight() + ".\n";
		
			//Evidenzio tutte le foglie dell'heap sia nell'albero che nel vettore
			for(Integer index = this.dataVector.size()/2; index < this.dataVector.size(); index++) {
				Circle circle = (Circle)this.nodeVector.get(index).getChildren().get(0);
				circle.setFill(Color.GREENYELLOW);
				Rectangle rect = (Rectangle)this.numVector.get(index).getChildren().get(0);
				rect.setFill(Color.GREENYELLOW);			
			}
		
			if(this.dataVector.size() > 3) {
				info = info + "Le foglie hanno massima profondità " + this.calculateTreeHeight() + " o " + (this.calculateTreeHeight()-1) + ", evidenziate in verde.\n";
			}
			else {
				info = info + "Le foglie hanno massima profondità " + this.calculateTreeHeight() + ", evidenziate in verde.\n";
			}
		
			//Controllo se ci sono nodi interni da grado 1
			if(this.dataVector.size() % 2 == 0) {
				Circle circle = (Circle)this.nodeVector.get((this.dataVector.size()/2)-1).getChildren().get(0);
				circle.setFill(Color.PALEVIOLETRED);
				Rectangle rect = (Rectangle)this.numVector.get((this.dataVector.size()/2)-1).getChildren().get(0);
				rect.setFill(Color.PALEVIOLETRED);
			
				info = info + "C'è un solo nodo interno di grado 1, evidenziato in rosso.\n";
			}
		}
		else if (this.dataVector.size() == 0) {
			info = info + "L'albero é vuoto. Per generare un nuovo Heap scrivi un numero e premi Add";
		}
		else {
			info = info + "Questo albero ha solo la radice.\n";			
		}
		
		this.infoText.setText(info);
	}
	
	
	@Override
	public void addToVector() {
		super.addToVector();
		if(this.dataVector.size() >= 1) {
			this.removeButton.setDisable(false);
			this.resetScreen();
			this.infoText.setText("Aggiungi un altro elemento oppure premi Make heap per interagire.");
		}
		this.generateButton.setDisable(false);
	}
	
	@Override
	public void removeFromVector() {
		super.removeFromVector();
		this.resetScreen();
		if(this.dataVector.size() >= 1 ) {
			this.infoText.setText("Trasforma in heap prima di interagire!");
			this.generateButton.setDisable(false);
		} else {
			this.infoText.setText("Inserisci un numero e premi 'Add'\noppure genera un vettore casuale.");
			this.addButton.setDisable(false);
			this.removeButton.setDisable(true);
			this.generateButton.setDisable(true);
			this.drawTree();
		}
	}
	
	public void createRandom(){
		this.dataVector = super.randomVector();
		this.drawVector();
		this.drawTree();
		this.removeButton.setDisable(false);
		this.generateButton.setDisable(false);
		this.infoText.setText("Premi su Make Heap per generare e rappresentare l'heap.");
	}
	
	@Override
	public void generateHeap() {
		super.generateHeap();
		this.addButton.setDisable(false);
		this.generateButton.setDisable(true);
		if(this.dataVector.size() >= 1) this.removeButton.setDisable(false);
		else this.removeButton.setDisable(true);
		if(this.isGenerated == true)
			this.highlightCompleteness();
	}
}
