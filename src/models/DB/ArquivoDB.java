/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.DB;

import gui.utils.Alerts;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map.Entry;
import javafx.scene.control.Alert;
import models.entities.Config;

/**
 *
 * @author rodri
 */
public class ArquivoDB {
    
    public static void save(Config config, String path){
        
        try( ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(new File(path))) ) {
            
            output.writeObject(config);
            output.flush();
            
        } catch (FileNotFoundException ex) {
            Alerts.showAlert("Error", "Erro de arquivo!", ex.getMessage(), Alert.AlertType.ERROR);
        } catch (IOException ex) {
            Alerts.showAlert("Error", "Erro de arquivo!", ex.getMessage(), Alert.AlertType.ERROR);
        }
        
    }
    
    public static Config load(String path){
        
        Config config = null;
        
        File file = new File(path);
        
        if(file.exists()){
            
            try( ObjectInputStream input = new ObjectInputStream(new FileInputStream(file)) ){

                config = (Config) input.readObject();

            } catch (FileNotFoundException ex) {
                Alerts.showAlert("Error", "Erro de arquivo!", ex.getMessage(), Alert.AlertType.ERROR);
            } catch (IOException | ClassNotFoundException ex) {
                Alerts.showAlert("Error", "Erro de arquivo!", ex.getMessage(), Alert.AlertType.ERROR);
            }
            
        }else{
            Entry<String, String> entry = new HashMap.SimpleEntry<>("Arduino Uno (COM3)","COM3");
            config = new Config(entry, 9600, 0, 200f, 200f, 200f, 80f, 60f, 1f);
            
            save(config, path);
            
        }
        
        return config;
    }
    
}
