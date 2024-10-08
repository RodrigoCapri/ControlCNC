/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.maquina;

import application.Main;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.DB.ArquivoDB;
import models.entities.Config;
import models.services.UsbControl;

/**
 *
 * @author rodri
 */
public class ViewConfigController implements Initializable{

    @FXML
    private ComboBox<String> cbPortasComm;
    private ObservableList<String> obsListPortasComm;
    
    @FXML
    private Label lbPortaCom;
    
    @FXML
    private ComboBox<Integer> cbBaunds;
    private ObservableList<Integer> obsListBaunds;
    
    @FXML
    private ComboBox<Integer> cbMotorMode;
    private ObservableList<Integer> obsListMotorMode;
    
    @FXML
    private TextField tfMotorX;
    @FXML
    private TextField tfMotorY;
    @FXML
    private TextField tfMotorZ;
    
    @FXML
    private TextField tfServoMax;
    @FXML
    private TextField tfServoMin;
    @FXML
    private TextField tfServoIncrement;
    
    @FXML
    private Button btSalvar;
    @FXML
    private Button btEditar;
    @FXML
    private Button btCancelar;
    
    private Map<String, String> map;
    
    public ViewConfigController(){
        
    }
    public void onBtSalvarAction(){
        
        btSalvar.setDisable(true);
        btCancelar.setDisable(true);
        btEditar.setDisable(false);
        
        //Nova instancia de configuração
        Config config = new Config();
  
        //Seta a porta COM (Nome do dispositivo, nome da porta)
        String nameP = cbPortasComm.getSelectionModel().getSelectedItem();
        String com = lbPortaCom.getText();
        Entry<String, String> entry = new HashMap.SimpleEntry<>(nameP , com);
        config.setPortaComm(entry);
        
        //Seta os baunds
        int baunds = cbBaunds.getSelectionModel().getSelectedItem();
        config.setBaunds(baunds);
        
        //Seta o motor mode
        int motorMode = cbMotorMode.getSelectionModel().getSelectedIndex();
        config.setMotorMode(motorMode);
        
        // Steps/mm dos motores de passo
        float stepX = Float.parseFloat(tfMotorX.getText()); //eixo X
        config.setStepEixoX(stepX);
        float stepY = Float.parseFloat(tfMotorY.getText()); //eixo Y
        config.setStepEixoY(stepY);
        float stepZ = Float.parseFloat(tfMotorZ.getText()); //eixo Z
        config.setStepEixoZ(stepZ);
        
        //Configuração do servo motor
        float posMax = Float.parseFloat(tfServoMax.getText());
        config.setServoPosMax(posMax);
        float posMin = Float.parseFloat(tfServoMin.getText());
        config.setServoPosMin(posMin);
        float incremento = Float.parseFloat(tfServoIncrement.getText());
        config.setServoIncrement(incremento);
        
        //Salva as novas configurações
        ArquivoDB.save(config, Main.getPahtConfig());
        
        this.setEditableComponents(false);
        
    }
    
    public void onBtEditarAction(){
        
        btSalvar.setDisable(false);
        btCancelar.setDisable(false);
        btEditar.setDisable(true);
        
        this.setEditableComponents(true);
        
    }
    
    public void onBtCancelarAction(){
        
        btSalvar.setDisable(true);
        btCancelar.setDisable(true);
        btEditar.setDisable(false);
        
        this.setEditableComponents(false);
        
    }
    
    public void onCbPortasComAction(){
        
        String nameP = cbPortasComm.getSelectionModel().getSelectedItem();
        
        lbPortaCom.setText(map.get(nameP));
        
    }
    
    private void setEditableComponents(boolean status){
        
        cbPortasComm.setDisable(!status);
        cbBaunds.setDisable(!status);
        cbMotorMode.setDisable(!status);
        
        tfMotorX.setDisable(!status);
        tfMotorY.setDisable(!status);
        tfMotorZ.setDisable(!status);
        tfServoIncrement.setDisable(!status);
        tfServoMax.setDisable(!status);
        tfServoMin.setDisable(!status);
        
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        Locale.setDefault(Locale.US);
        
        Config config = ArquivoDB.load(Main.getPahtConfig());
        
        //Carrega a lista de baunds no ComboBox
        obsListBaunds = FXCollections.observableArrayList(Arrays.asList(9600, 19200, 31250, 19200, 31250, 38400, 57600, 74880, 115200));
        cbBaunds.setItems(obsListBaunds);
        cbBaunds.setValue(config.getBaunds());
        
        //Carrega a lista de portas comm disponiveis no ComboBox
        //Map contendo o nome do dispositivo da porta e o nome da porta
        map = UsbControl.getPortasCom();
        
        //Teste
        //map.put("Teste 1", "COM99");
        //map.put("Teste 2", "COM55");
        
        //Array para armazenar a lista de portas disponíveis
        List<String> list = new ArrayList<>(); 
        for (Map.Entry<String, String> entry : map.entrySet()) {
            list.add(entry.getKey());
        }
        obsListPortasComm = FXCollections.observableArrayList(list);
        cbPortasComm.setItems(obsListPortasComm);
        cbPortasComm.setValue(config.getPortaComm().getKey());
        lbPortaCom.setText(config.getPortaComm().getValue());
        
        //Carrega a lista de modos dos motores no ComboBox
        obsListMotorMode = FXCollections.observableArrayList(Arrays.asList(0, 1, 2, 3));
        cbMotorMode.setItems(obsListMotorMode);
        cbMotorMode.setValue(config.getMotorMode());
        
        tfMotorX.setText( String.format("%.2f", config.getStepEixoX()) );
        tfMotorY.setText( String.format("%.2f", config.getStepEixoY()) );
        tfMotorZ.setText( String.format("%.2f", config.getStepEixoZ()) );
        
        tfServoMax.setText( String.format("%.2f", config.getServoPosMax()) );
        tfServoMin.setText( String.format("%.2f", config.getServoPosMin()) );
        tfServoIncrement.setText( String.format("%.2f", config.getServoIncrement()) );
        
        //Desabilita os componentes necessários
        btSalvar.setDisable(true);
        btCancelar.setDisable(true);
        btEditar.setDisable(false);
        
        this.setEditableComponents(false);
        
    }
    
}
