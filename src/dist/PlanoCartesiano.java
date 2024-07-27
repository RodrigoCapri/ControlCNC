package dist;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import dist.gcode.PaintComponentGCode;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import dist.process.AtualPoint;

/**
 *
 * @author rodri
 */
public class PlanoCartesiano extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private PaintComponentGCode commands_gcode;
    
    public PlanoCartesiano(PaintComponentGCode commands_gcode){
        this.commands_gcode= commands_gcode;
        AtualPoint.setPontoAtual(0, 0);
    }
    
    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2d= (Graphics2D) g;
        AtualPoint.setGraphics(g2d, this);
        //Desenha Fundo
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        g2d.setColor(Color.red);
        g2d.drawLine(0, this.getHeight()/2, this.getWidth(), this.getHeight()/2);
        g2d.drawLine(this.getWidth()/2, 0, this.getWidth()/2, this.getHeight());
        
        commands_gcode.printGCcode(g2d);
    }
    
}