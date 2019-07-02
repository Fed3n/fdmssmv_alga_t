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
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
		MainMenuController controller = new MainMenuController();
		loader.setController(controller);
		Parent node = (Parent)loader.load();
		Scene mainScene = new Scene(node);
    	
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
