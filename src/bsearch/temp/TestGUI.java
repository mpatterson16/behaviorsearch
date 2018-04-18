package bsearch.temp;

import bsearch.util.GeneralUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class TestGUI extends Application{
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("SaferDataCollectionTest.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Untitled" + getWindowTitleSuffix());
		Platform.setImplicitExit(false);
		TestController controller = (TestController)loader.getController();

		/*primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent event) {
		    	if (!controller.checkDiscardOkay()){
		    		event.consume();
		    	}
		    	else {
		    		Platform.exit();
		    		System.exit(0);
		    	}
		    }
		});*/
		primaryStage.show();
		Image icon = new Image(GeneralUtils.getResource("icon_behaviorsearch.png").toURI().toString());
		primaryStage.getIcons().add(icon);
		//controller.actionNew();

		} catch (Exception e) {
			e.printStackTrace();
		}
	

	}
	static String getWindowTitleSuffix() {
		return " - BehaviorSearch " + GeneralUtils.getVersionString();
	}

}
