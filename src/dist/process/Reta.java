/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dist.process;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author rodri
 */
public class Reta extends AtualPoint{
    
    public static void drawReta(double x, double y, double large, Color cor){
        GraphicsContext gc = getGc();
        
        double compense_x= getCanvas().getWidth() / 2;
        double compense_y= getCanvas().getHeight() / 2;
        
        double a = ( (AtualPoint.getX()*AtualPoint.getFatorMultiplicador()) +compense_x );
        double b = ( ( (AtualPoint.getY()*AtualPoint.getFatorMultiplicador()) *-1)+compense_y );
        double a2 = ( (x*AtualPoint.getFatorMultiplicador()) +compense_x );
        double b2 = ( ( (y*AtualPoint.getFatorMultiplicador()) *-1)+compense_y );
        
        gc.setStroke(cor);
        gc.setLineWidth(large);
        gc.strokeLine(a, b, a2, b2);
        
        setPontoAtual(x, y);
        
    }
}