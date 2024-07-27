/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.iniciar;

import application.Main;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import models.DB.ArquivoDB;
import models.entities.Config;

/**
 *
 * @author rodri
 */
public class IniciarViewController implements Initializable{

    @FXML
    private Label lbPortaCom;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        Config config = ArquivoDB.load(Main.getPahtConfig());
        
        lbPortaCom.setText(config.getPortaComm().getKey());
        
        
    }
    
}
