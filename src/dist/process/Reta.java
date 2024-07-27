/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dist.process;

import java.awt.Color;
import java.awt.geom.Line2D;

/**
 *
 * @author rodri
 */
public class Reta extends AtualPoint{
    
    public static void drawReta(double x, double y){
        AtualPoint.g2d.setColor(Color.GREEN);
        double compense_x= AtualPoint.panel.getWidth() / 2;
        double compense_y= AtualPoint.panel.getHeight() / 2;
        
        double a = ( (AtualPoint.x*AtualPoint.getFatorMultiplicador()) +compense_x );
        double b = ( ( (AtualPoint.y*AtualPoint.getFatorMultiplicador()) *-1)+compense_y );
        double a2 = ( (x*AtualPoint.getFatorMultiplicador()) +compense_x );
        double b2 = ( ( (y*AtualPoint.getFatorMultiplicador()) *-1)+compense_y );
        
        AtualPoint.g2d.draw(new Line2D.Double( a, b, a2, b2) );
        AtualPoint.setPontoAtual(x, y);
    }
    
    public static void drawReta2(double x, double y){
        AtualPoint.g2d.setColor(Color.CYAN);
        double compense_x= AtualPoint.panel.getWidth() / 2;
        double compense_y= AtualPoint.panel.getHeight() / 2;
        
        double a = ( (AtualPoint.x*AtualPoint.getFatorMultiplicador()) +compense_x );
        double b = ( ( (AtualPoint.y*AtualPoint.getFatorMultiplicador()) *-1)+compense_y );
        double a2 = ( (x*AtualPoint.getFatorMultiplicador()) +compense_x );
        double b2 = ( ( (y*AtualPoint.getFatorMultiplicador()) *-1)+compense_y );
        
        AtualPoint.g2d.draw(new Line2D.Double( a, b, a2, b2) );
        AtualPoint.setPontoAtual(x, y);
    }
    
    public static void drawRetaG2(double x, double y){
        AtualPoint.g2d.setColor(Color.orange);
        double compense_x= AtualPoint.panel.getWidth() / 2;
        double compense_y= AtualPoint.panel.getHeight() / 2;
        
        double a = ( (AtualPoint.x*AtualPoint.getFatorMultiplicador()) +compense_x );
        double b = ( ( (AtualPoint.y*AtualPoint.getFatorMultiplicador()) *-1)+compense_y );
        double a2 = ( (x*AtualPoint.getFatorMultiplicador()) +compense_x );
        double b2 = ( ( (y*AtualPoint.getFatorMultiplicador()) *-1)+compense_y );
        
        AtualPoint.g2d.draw(new Line2D.Double( a, b, a2, b2) );
        AtualPoint.setPontoAtual(x, y);
    }
    
    public static void drawRetaG3(double x, double y){
        AtualPoint.g2d.setColor(Color.LIGHT_GRAY);
        double compense_x= AtualPoint.panel.getWidth() / 2;
        double compense_y= AtualPoint.panel.getHeight() / 2;
        
        double a = ( (AtualPoint.x*AtualPoint.getFatorMultiplicador()) +compense_x );
        double b = ( ( (AtualPoint.y*AtualPoint.getFatorMultiplicador()) *-1)+compense_y );
        double a2 = ( (x*AtualPoint.getFatorMultiplicador()) +compense_x );
        double b2 = ( ( (y*AtualPoint.getFatorMultiplicador()) *-1)+compense_y );
        
        AtualPoint.g2d.draw(new Line2D.Double( a, b, a2, b2) );
        AtualPoint.setPontoAtual(x, y);
    }
}