
package application;

import gui.utils.Alerts;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

/**
 *
 * @author rodri
 */
public class Main extends Application{
    
    private static Scene mainScene;
    private static Stage stage;
    private static String pathConfig = "config.conf";
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        try{
            
            ScrollPane scrollPane = FXMLLoader.load(this.getClass().getResource("/gui/MainView.fxml"));
            
            scrollPane.setFitToHeight(true);
            scrollPane.setFitToWidth(true);
            
            mainScene = new Scene(scrollPane);
            
            primaryStage.setScene(mainScene);
            primaryStage.setTitle("Controle CNC");
            primaryStage.show();
            
            stage = primaryStage;
            
        }catch(IOException e){
            Alerts.showAlert("Error", null, e.getMessage(), Alert.AlertType.ERROR);
        }
        
    }
    
    public static Scene getMainScene(){
        return mainScene;
    }
    
    public static Stage getParent(){
        return stage;
    }
    
    public static String getPahtConfig(){
        return pathConfig;
    }
    
    public static void main(String args[]){
        launch(args);
    }
    
}
