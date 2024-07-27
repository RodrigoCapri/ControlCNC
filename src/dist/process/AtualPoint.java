/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dist.process;

import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author rodri
 */
public class AtualPoint {
    
    static double x= 0; //ponto atual
    static double y= 0; //ponto atual
    static Graphics2D g2d;
    static JPanel panel;
    static double fator_multiplicador = 1;
    
    public static void setGraphics(Graphics2D g2d, JPanel panel){
        AtualPoint.g2d= g2d;
        AtualPoint.panel= panel;
    }
    
    public static void setFatorMultiplicador(double valor){
        AtualPoint.fator_multiplicador = valor;
    }
    
    public static double getFatorMultiplicador(){
        return AtualPoint.fator_multiplicador;
    }
    
    public static void setPontoAtual(double x, double y){
        AtualPoint.x= x;
        AtualPoint.y= y;
    }
    
    public static void zararEixos(){
        AtualPoint.x= 0;
        AtualPoint.y= 0;
    }
    
    public static void to_string(){
        String str= "=> "+AtualPoint.x+" , "+AtualPoint.y;
        System.out.println(str);
    }
    
    public static Graphics2D getGraphics2D(){
        return AtualPoint.g2d;
    }
    
    public static JPanel getPanel(){
        return AtualPoint.panel;
    }
    
    public static double getX(){
        return AtualPoint.x;
    }
    
    public static double getY(){
        return AtualPoint.y;
    }
}