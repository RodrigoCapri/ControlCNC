/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dist.process;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author rodri
 */
public class AtualPoint {
    
    private static double x; //ponto atual
    private static double y; //ponto atual
    private static double fator_multiplicador = 1;
    
    private static GraphicsContext gc;
    private static Canvas canvas;

    public static double getFator_multiplicador() {
        return fator_multiplicador;
    }

    public static void setFator_multiplicador(double fator_multiplicador) {
        AtualPoint.fator_multiplicador = fator_multiplicador;
    }

    public static GraphicsContext getGc() {
        return gc;
    }

    public static void setGc(GraphicsContext gc) {
        AtualPoint.gc = gc;
    }

    public static Canvas getCanvas() {
        return canvas;
    }

    public static void setCanvas(Canvas canvas) {
        AtualPoint.canvas = canvas;
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
    
    public static double getX(){
        return AtualPoint.x;
    }
    
    public static double getY(){
        return AtualPoint.y;
    }
}