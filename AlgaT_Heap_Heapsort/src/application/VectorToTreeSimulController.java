package application;

import java.util.Iterator;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class VectorToTreeSimulController extends HeapSimul2Controller{
	
	@FXML
	private TextArea infoText;
	
	@Override
	public void initialize() {
		super.initialize();
		this.infoText.setEditable(false);
		this.infoText.setWrapText(true);		
	}
	
	@Override
	public void drawVector() {
		this.vectorHbox.getChildren().clear();	//Prima rimuovo il vettore già disegnato
		this.numVector.clear();
		
		
		Integer index = 0;
		//Itero sulla lista e per ogni membro creo uno slot del vettore
		Iterator<Integer> vectorIterator = this.dataVector.iterator();
		while(vectorIterator.hasNext()) {
			final Integer finalIndex = index;	//Da inserire nell'evento dato che richiede variabili final
			
			Integer num = vectorIterator.next();	
			
			StackPane numPane = new StackPane();
			Label numLabel = new Label();
			numLabel.setText(num.toString());
			Rectangle numBox = new Rectangle(30,30);
			numBox.setFill(Color.WHITE);
			numBox.setStroke(Color.BLACK);
					
			numPane.getChildren().add(numBox);
			numPane.getChildren().add(numLabel);
			this.vectorHbox.getChildren().add(numPane);
			this.numVector.add(numPane);
			
			numPane.setOnMouseEntered(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					if(isGenerated == true) {
						numBox.setFill(Color.GREENYELLOW);
						StackPane node = nodeVector.get(finalIndex);
						Circle circle = (Circle)node.getChildren().get(0);	//Il cerchio nello StackPane del nodo dell'albero
						circle.setFill(Color.GREENYELLOW);
						
						//Metto informazioni riguardo al nodo selezionato
						String info = "";
						info = info + "Questo nodo è in posizione " + (finalIndex+1) + " nel vettore.\n";
						//Se non è la radice
						if(finalIndex != 0) {
							info = info + "Il padre di questo nodo è in posizione " + ((finalIndex+1)/2) + " ed è colorato in rosso.\n";
							node = nodeVector.get((finalIndex-1)/2);
							circle = (Circle)node.getChildren().get(0);
							circle.setFill(Color.PALEVIOLETRED);
							StackPane pNode = numVector.get((finalIndex-1)/2);
							Rectangle box = (Rectangle)pNode.getChildren().get(0);
							box.setFill(Color.PALEVIOLETRED);
						}
						//Nel caso sia la radice
						else {
							info = info + "Questo nodo è la radice dell'albero, di conseguenza non ha figli.\n";
						}
						//Evidenzio e inserisco informazioni su eventuali figli
						if(finalIndex < dataVector.size()/2) {
							//Se ha due figli
							if(finalIndex < (dataVector.size()-1)/2) {
								info = info + "I figli di questo nodo sono in posizione " + ((finalIndex+1)*2) + " e " + ((finalIndex+1)*2+1) + ", colorati in blu\n";
								//Primo figlio
								node = nodeVector.get((finalIndex*2)+1);
								circle = (Circle)node.getChildren().get(0);
								circle.setFill(Color.CORNFLOWERBLUE);
								StackPane pNode = numVector.get((finalIndex*2)+1);
								Rectangle box = (Rectangle)pNode.getChildren().get(0);
								box.setFill(Color.CORNFLOWERBLUE);
								//Secondo figlio
								node = nodeVector.get((finalIndex*2)+2);
								circle = (Circle)node.getChildren().get(0);
								circle.setFill(Color.CORNFLOWERBLUE);
								pNode = numVector.get((finalIndex*2)+2);
								box = (Rectangle)pNode.getChildren().get(0);
								box.setFill(Color.CORNFLOWERBLUE);
							}
							//Un solo figlio
							else {
								info = info + "Il figlio di questo nodo è in posizione " + ((finalIndex+1)*2) + ", colorato in blu\n";
								//Primo figlio
								node = nodeVector.get((finalIndex*2)+1);
								circle = (Circle)node.getChildren().get(0);
								circle.setFill(Color.CORNFLOWERBLUE);
								StackPane pNode = numVector.get((finalIndex*2)+1);
								Rectangle box = (Rectangle)pNode.getChildren().get(0);
								box.setFill(Color.CORNFLOWERBLUE);
							}
						}
						
						infoText.setText(info);
					}
				}
			});
			
			numPane.setOnMouseExited(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					if(isGenerated == true) {
						numBox.setFill(Color.WHITE);
						StackPane node = nodeVector.get(finalIndex);
						Circle circle = (Circle)node.getChildren().get(0);	//Il cerchio nello StackPane del nodo dell'albero
						circle.setFill(Color.WHITE);
						
						if(finalIndex != 0) {
							node = nodeVector.get((finalIndex-1)/2);
							circle = (Circle)node.getChildren().get(0);
							circle.setFill(Color.WHITE);
							StackPane pNode = numVector.get((finalIndex-1)/2);
							Rectangle box = (Rectangle)pNode.getChildren().get(0);
							box.setFill(Color.WHITE);
						}
						//Evidenzio e inserisco informazioni su eventuali figli
						if(finalIndex < dataVector.size()/2) {
							//Se ha due figli
							if(finalIndex < (dataVector.size()-1)/2) {
								//Primo figlio
								node = nodeVector.get((finalIndex*2)+1);
								circle = (Circle)node.getChildren().get(0);
								circle.setFill(Color.WHITE);
								StackPane pNode = numVector.get((finalIndex*2)+1);
								Rectangle box = (Rectangle)pNode.getChildren().get(0);
								box.setFill(Color.WHITE);
								//Secondo figlio
								node = nodeVector.get((finalIndex*2)+2);
								circle = (Circle)node.getChildren().get(0);
								circle.setFill(Color.WHITE);
								pNode = numVector.get((finalIndex*2)+2);
								box = (Rectangle)pNode.getChildren().get(0);
								box.setFill(Color.WHITE);
							}
							//Un solo figlio
							else {
								//Primo figlio
								node = nodeVector.get((finalIndex*2)+1);
								circle = (Circle)node.getChildren().get(0);
								circle.setFill(Color.WHITE);
								StackPane pNode = numVector.get((finalIndex*2)+1);
								Rectangle box = (Rectangle)pNode.getChildren().get(0);
								box.setFill(Color.WHITE);
							}
						}
						infoText.clear();
					}
					
				}
			});
			
			index++;
		}
	}
	

}
