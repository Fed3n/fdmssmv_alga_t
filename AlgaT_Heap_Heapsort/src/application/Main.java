package application;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
		Scene mainScene = new Scene(root);
		
		primaryStage.setTitle("AlgaT");
		primaryStage.setScene(mainScene);
		primaryStage.sizeToScene();
		primaryStage.setOnCloseRequest(closeAll -> Platform.exit());
		primaryStage.show();      
		} 
	
	public static void main(String[] args) {
		launch(args);
	}
}
