/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.iniciar;

import application.Main;
import dist.gcode.ArquivoGCode;
import dist.process.AtualPoint;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
    
    private void drawShapes(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth() , canvas.getHeight());
        
        gc.setStroke(Color.RED);
        gc.setLineWidth(1);
        gc.strokeLine(0, canvas.getHeight()/2, canvas.getWidth(), canvas.getHeight()/2);
        gc.strokeLine(canvas.getWidth()/2, 0, canvas.getWidth()/2, canvas.getHeight());
        
        AtualPoint.setCanvas(canvas);
        AtualPoint.setGc(gc);
        AtualPoint.setPontoAtual(0, 0);
        
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
    
}
