/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dist.gcode;

import application.Main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;

/**
 *
 * @author rodri
 */
public class ArquivoGCode {
    
    private static ArrayList<String> linhas_comando = null;
    private static int num_linhas= 0;
    private static File path_file = null;
    
    //Aqui permite abrir um gcode com uma caixa de dialogo ja pronta
    public static String openFile(){
        
        FileChooser file_chooser = new FileChooser();
        file_chooser.setTitle("Abrir arquivo...");
        file_chooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Arquivos gcode *.nc", "nc"));
        path_file = file_chooser.showOpenDialog(Main.getParent());
        
        if(path_file != null){
            
            linhas_comando= new ArrayList<>();
            
            try (BufferedReader buf_reader = new BufferedReader(new FileReader(path_file))) {
                while(true){
                    String linha= buf_reader.readLine();
                    if(linha != null){
                        if( !( charExist(linha, '(') || charExist(linha, ')') || charExist(linha, ';')) ){
                            linhas_comando.add(linha);
                            num_linhas++;
                        }
                    }else
                        break;
                }
            } catch (IOException ex) {
                Logger.getLogger(ArquivoGCode.class.getName()).log(Level.SEVERE, null, ex);
            }
            return path_file.getAbsolutePath();
            
        }else{
            return null;
        }
    }
    
    public static void openFile(String path){
        linhas_comando= new ArrayList<>();

        path_file = new File(path);
        
        try (BufferedReader buf_reader = new BufferedReader(new FileReader(path_file))) {
            while(true){
                String linha= buf_reader.readLine();
                if(linha != null){
                    if( !( charExist(linha, '(') || charExist(linha, ')') || charExist(linha, ';')) ){
                        linhas_comando.add(linha);
                        num_linhas++;
                    }
                }else
                    break;
            }
        } catch (IOException ex) {
            Logger.getLogger(ArquivoGCode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void closeFile(){
        path_file= null;
        linhas_comando= null;
        num_linhas= 0;
    }
    //Retorna um array contendo as linhas de codigos contido num arquivo gcode
    public static ArrayList<String> getLinhasComando(){
        return linhas_comando;
    }
    
    //Aqui verifica se na linha de comando existe um caracter especifico
    public static boolean charExist(String cmd, char c){
        boolean result= false;
        for(int i=0; i < cmd.length(); i++){
            if(cmd.charAt(i) == c){
                result= true;
                break;
            }
        }
        return result;
    }
    
    //Aqui pega o valor em double a partir de uma letra
    public static double getValueDouble(String cmd, char c){
        int count= 0;
        String valor= "";
        
        //Corre pela string até a posição do caractere especificado
        for(int i=0; i<cmd.length(); i++, count++){
            if(cmd.charAt(i) == c){
                count++;
                if(cmd.charAt(count) == ':' || cmd.charAt(count) == ' ')
                    count++;
                break;
            }
        }
        
        //Partindo da posição encontrada do caractere especificado então inicia-se
        //o processo de captura de numeros até encontrar um espaço
        for(int i=count; i<cmd.length(); i++){
            if(cmd.charAt(i) == ' '){
                break;
            }else{
                valor+= cmd.charAt(i);
            }
        }
        
        return Double.parseDouble(valor);
    }
    //Faz o mesmo que o método acima, mas retorna inteiro
    public static int getValueInt(String cmd, char c){
        int count= 0;
        String valor= "";
        
        //Corre pela string até a posição do caractere especificado
        for(int i=0; i<cmd.length(); i++, count++){
            if(cmd.charAt(i) == c){
                count++;
                if(cmd.charAt(count) == ':' || cmd.charAt(count) == ' ')
                    count++;
                break;
            }
        }
        
        //Partindo da posição encontrada do caractere especificado então inicia-se
        //o processo de captura de numeros até encontrar um espaço
        for(int i=count; i<cmd.length(); i++){
            if(cmd.charAt(i) == ' '){
                break;
            }else{
                valor+= cmd.charAt(i);
            }
        }
        
        return Integer.parseInt(valor);
    }
    
    public static int getNumLinhas(){
        return num_linhas;
    }
    
    public static boolean isFileOpen(){
        return (path_file != null);
    }
    
    public static String getPathFile(){
        return path_file.getAbsolutePath();
    }
    
    public static String getLinhaComando(int i){
        return linhas_comando.get(i);
    }
}
