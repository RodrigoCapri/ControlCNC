
package gui;

import application.Main;
import gui.utils.Alerts;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author rodri
 */
public class MainViewController  implements Initializable{

    @FXML
    private MenuItem menuItemSobre;
    
    @FXML
    private MenuItem menuItemConfig;
    
    public MainViewController(){
    }
    
    @FXML
    public void onMenuItemSobreAction(){
        
        this.loadView("/gui/sobre/Sobre.fxml", x -> {});
        
    }
    
    @FXML
    public void onMenuItemConfigAction(javafx.event.ActionEvent event){
        
        this.createDialogForm("maquina/ViewConfig.fxml", Main.getParent());
        
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) { //Interface Consumer para chamada da expressão lambda passada
	
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource(absoluteName)); //Carrega o cenário da fxml informada
		
		try {
			
			VBox newVBox = loader.load(); //Carrega uma nova VBox
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ( (ScrollPane) mainScene.getRoot() ).getContent(); //Pega o primeiro elemento da view
			
			Node mainMenu = mainVBox.getChildren().get(0); //Pega o menu bar
			mainVBox.getChildren().clear(); //Limpa todos os filhos do mainVBox
			
			mainVBox.getChildren().add(mainMenu); //Adiciona o menu bar
			mainVBox.getChildren().addAll(newVBox.getChildren()); //Adiciona os elementos da tela About
			
			//o getController() vai retornar um controller do tipo da classe que foi passada na expressão lambda, DepartmentListController
			T controller = loader.getController();
			initializingAction.accept(controller); //Executa os comandos passados por expressão lambda no parametro
			
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error loading view!", e.getMessage(), AlertType.ERROR);
		}
		
	}
    
    private void createDialogForm(String absoluteName, Stage parentStage) {
		try {

			FXMLLoader loader = new FXMLLoader(this.getClass().getResource(absoluteName)); // Carrega o cenário da fxml
																							// informada
			Pane pane = loader.load(); // Adiciona o cenario em um Pane

			Stage dialogStage = new Stage(); // Nova cena para aparecer na frente de outra cena
			dialogStage.setTitle("Configurações da máquina"); // Definindo o titulo
			dialogStage.setScene(new Scene(pane)); // Adiciona o Pane na cena
			dialogStage.setResizable(false); // Define como não redimensionavel
			dialogStage.initOwner(parentStage); // Quem é o init pai dessa janela
			// Modality.WINDOW_MODAL -> Enquando você não fechar essa janela, não poderá
			// mexer na tela anterior
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait(); // Mostra a tela enquanto aguarda

		} catch (IOException ex) {
			Alerts.showAlert("IO EXception", "Error load view!", ex.getMessage(), AlertType.ERROR);
		}
	}
    
}
