
package gui;

import application.Main;
import dist.gcode.ArquivoGCode;
import dist.process.Arco;
import dist.process.AtualPoint;
import dist.process.Reta;
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
import javafx.scene.paint.Color;
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
    
    @FXML
    private MenuItem menuItemIniciar;
    
    @FXML
    private MenuItem menuItemAbrir;
    
    @FXML
    private MenuItem menuItemFechar;
    
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
    
    @FXML
    public void onMenuItemIniciarAction(){
        
        //Inicia uma conexão com Arduino
        //E imprime na tela algum arquivo gcode que já esteja aberto
        this.loadView("/gui/iniciar/IniciarView.fxml", x -> {
            this.drawGCode();
        });
        
    }
    
    @FXML
    public void onMenuItemAbrirAction(){
        
        //Abrir um arquivo gcode e imprimir na tela
        //Sem iniciar uma conexão com Arduino
        ArquivoGCode.openFile();
        
        this.loadView("/gui/iniciar/IniciarView.fxml", x -> {
            this.drawGCode();
        });
        
    }
    
    @FXML
    public void onMenuItemFecharAction(){
        
        //Se algum arquivo estiver aberto ele fecha o arquivo e repinta a tela
        if(ArquivoGCode.isFileOpen()){
            
            ArquivoGCode.closeFile();
            this.loadView("/gui/iniciar/IniciarView.fxml", x -> {});
            
        }
        
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    private void drawGCode(){
        
        if(ArquivoGCode.isFileOpen()){
            
            for(String cmd : ArquivoGCode.getLinhasComando()){
                
                double x1;
                double y1;
                double i;
                double j;
                int comand_g= (ArquivoGCode.charExist(cmd, 'G')) ? ArquivoGCode.getValueInt(cmd, 'G'):0;
                switch (comand_g) {
                    case 0:
                        //Movimento rapido
                        x1 = (ArquivoGCode.charExist(cmd, 'X')) ? ArquivoGCode.getValueDouble(cmd, 'X'):AtualPoint.getX();
                        y1 = (ArquivoGCode.charExist(cmd, 'Y')) ? ArquivoGCode.getValueDouble(cmd, 'Y'):AtualPoint.getY();
                        Reta.drawReta(x1, y1, 1, Color.CYAN);
                        break;
                    case 1:
                        //Movimento preciso
                        x1 = (ArquivoGCode.charExist(cmd, 'X')) ? ArquivoGCode.getValueDouble(cmd, 'X'):AtualPoint.getX();
                        y1 = (ArquivoGCode.charExist(cmd, 'Y')) ? ArquivoGCode.getValueDouble(cmd, 'Y'):AtualPoint.getY();
                        Reta.drawReta(x1, y1, 1, Color.LAWNGREEN);
                        break;
                    case 2:
                        //Arco sentido horário
                        x1 = (ArquivoGCode.charExist(cmd, 'X')) ? ArquivoGCode.getValueDouble(cmd, 'X'):AtualPoint.getX();
                        y1 = (ArquivoGCode.charExist(cmd, 'Y')) ? ArquivoGCode.getValueDouble(cmd, 'Y'):AtualPoint.getY();
                        i = (ArquivoGCode.charExist(cmd, 'I')) ? ArquivoGCode.getValueDouble(cmd, 'I'):0;
                        j = (ArquivoGCode.charExist(cmd, 'J')) ? ArquivoGCode.getValueDouble(cmd, 'J'):0;
                        Arco.drawArc(x1, y1, i, j, Arco.HORARIO);
                        break;
                    case 3:
                        //Arco sentido anti-horário
                        x1 = (ArquivoGCode.charExist(cmd, 'X')) ? ArquivoGCode.getValueDouble(cmd, 'X'):AtualPoint.getX();
                        y1 = (ArquivoGCode.charExist(cmd, 'Y')) ? ArquivoGCode.getValueDouble(cmd, 'Y'):AtualPoint.getY();
                        i = (ArquivoGCode.charExist(cmd, 'I')) ? ArquivoGCode.getValueDouble(cmd, 'I'):0;
                        j = (ArquivoGCode.charExist(cmd, 'J')) ? ArquivoGCode.getValueDouble(cmd, 'J'):0;
                        Arco.drawArc(x1, y1, i, j, Arco.ANTI_HORARIO);
                        break;
                    case 92: // Zera as coordenadas sem mover os eixos
                        AtualPoint.setPontoAtual(0, 0);
                        break;
                }
            }
            
            AtualPoint.zararEixos();
            
        }
        
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
