/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.iniciar;

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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import models.DB.ArquivoDB;
import models.entities.Config;
import models.services.UsbControl;

/**
 *
 * @author rodri
 */
public class IniciarViewController implements Initializable{

    @FXML
    private Label lbPortaCom;
    
    @FXML
    private Canvas canvas;
    
    @FXML
    private ListView<String> listViewGCode;
    private ObservableList<String> obsListViewGCode;
    
    @FXML
    private ListView<String> listViewCmds;
    private ObservableList<String> obsListViewCmds;
    
    @FXML
    private Button btConectar;
    
    @FXML
    private Button btArquivo;
    
    @FXML
    private TextField tfArquivo;
    
    @FXML
    private TextField tfComando;
    
    @FXML
    private Button btEnviarComando;
    
    @FXML
    public void onBtConectarAction(){
        
        Config config = ArquivoDB.load(Main.getPahtConfig());
        
        if( btConectar.getText().equals("Conectar") ){
            
            UsbControl.connect(config.getPortaComm().getValue(), config.getBaunds());
            UsbControl.addReadEvent( data -> {
                
                obsListViewCmds = FXCollections.observableArrayList(listViewCmds.getItems());
                obsListViewCmds.add("<-- "+data);
                
                listViewCmds.setItems(obsListViewCmds);
                
            });
            
            btConectar.setText("Desconectar");
            
        }else{
            UsbControl.close();
            btConectar.setText("Conectar");
        }
        
    }
    
    @FXML
    public void onBtArquivoAction(){
        
        if(btArquivo.getText().equals("Abrir")){
            
            //Abrir um arquivo gcode e imprimir na tela
            //Sem iniciar uma conexão com Arduino
            ArquivoGCode.openFile();

            if(ArquivoGCode.isFileOpen()){
            
                tfArquivo.setText(ArquivoGCode.getNameFile());
                
                obsListViewGCode = FXCollections.observableArrayList(listViewGCode.getItems());
            
                for(String str : ArquivoGCode.getLinhasComando()){
                    obsListViewGCode.add(str);
                }
            
                listViewGCode.setItems(obsListViewGCode);

                this.drawShapes();
                
                btArquivo.setText("Fechar");
            }
            
        }else{
            
            ArquivoGCode.closeFile();
            
            tfArquivo.setText("");
            
            this.drawShapes();
            
            obsListViewGCode = FXCollections.observableArrayList();
            listViewGCode.setItems(obsListViewGCode);
            
            btArquivo.setText("Abrir");
            
        }
        
    }
    
    @FXML
    public void onBtEnviarComandoAction(){
        
        String cmd = tfComando.getText();
        
        obsListViewCmds = FXCollections.observableArrayList(listViewCmds.getItems());
        obsListViewCmds.add("--> "+cmd);
        listViewCmds.setItems(obsListViewCmds);
        
        UsbControl.print(cmd);
        
    }
    
    private void drawShapes(){
        
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth() , canvas.getHeight());
        
        gc.setStroke(Color.RED);
        gc.setLineWidth(1);
        gc.strokeLine(0, canvas.getHeight()/2, canvas.getWidth(), canvas.getHeight()/2);
        gc.strokeLine(canvas.getWidth()/2, 0, canvas.getWidth()/2, canvas.getHeight());
        
        AtualPoint.setCanvas(canvas);
        AtualPoint.setGc(gc);
        AtualPoint.setPontoAtual(0, 0);
        
        ////
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
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        Config config = ArquivoDB.load(Main.getPahtConfig());
        lbPortaCom.setText(config.getPortaComm().getKey());
        
        btConectar.setText( (UsbControl.isPortOpen()) ? "Desconectar" : "Conectar" );
        
        if(ArquivoGCode.isFileOpen()){
            
            obsListViewGCode = FXCollections.observableArrayList(listViewGCode.getItems());
            
            for(String str : ArquivoGCode.getLinhasComando()){
                obsListViewGCode.add(str);
            }
            
            listViewGCode.setItems(obsListViewGCode);
            
        }
        
        drawShapes();
        
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
                Alerts.showAlert("IO Exception", "Error loading view!", e.getMessage(), Alert.AlertType.ERROR);
        }

    }
    
}
