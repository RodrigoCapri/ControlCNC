/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dist;

import dist.process.AtualPoint;
import dist.process.Reta;
import dist.gcode.ArquivoGCode;
import dist.process.Arco;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author rodri
 */
public class PanelGCode extends JPanel{

    private static final long serialVersionUID = 1L;

    //JTextArea file_commands;
    JLabel lbArquivo;
    JButton btAbrir;
    JButton btFechar;
    
    PlanoCartesiano plano_cartesiano;
    
    public PanelGCode(){
        //Orientação dos componentes na vertical
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        JPanel pn_controle= new  JPanel();
        //Orientação dos componentes na horizontal
        pn_controle.setLayout(new BoxLayout(pn_controle, BoxLayout.X_AXIS));
        
        lbArquivo = new JLabel();
        pn_controle.add(lbArquivo);
        
        btAbrir= new JButton("Abrir arquivo");
        btAbrir.addActionListener((ActionEvent ae) -> {
            
            lbArquivo.setText( ArquivoGCode.openFile() );
            plano_cartesiano.repaint();
            
        });
        pn_controle.add(btAbrir);
        
        btFechar= new JButton("Fechar arquivo");
        btFechar.addActionListener((ActionEvent ae) -> {
            
            ArquivoGCode.closeFile();
            lbArquivo.setText("");
            plano_cartesiano.repaint();
            
        });
        pn_controle.add(btFechar);
        this.add(pn_controle);
        
        //tabbed_pane= new JTabbedPane();
        
        plano_cartesiano = new PlanoCartesiano((Graphics2D g2d) -> {
            
            if (ArquivoGCode.getLinhasComando() != null) {
                //Verifica primeiro se há alguma cadeia de comando aberta
                //Se houver alguma então iniciaremos os procedimentos para desenho
                for (String linha_comando : ArquivoGCode.getLinhasComando()) {
                    double x1;
                    double y1;
                    double i;
                    double j;
                    int comand_g= (ArquivoGCode.charExist(linha_comando, 'G')) ? ArquivoGCode.getValueInt(linha_comando, 'G'):0;
                    switch (comand_g) {
                        case 0:
                            //Movimento rapido
                            x1 = (ArquivoGCode.charExist(linha_comando, 'X')) ? ArquivoGCode.getValueDouble(linha_comando, 'X'):AtualPoint.getX();
                            y1 = (ArquivoGCode.charExist(linha_comando, 'Y')) ? ArquivoGCode.getValueDouble(linha_comando, 'Y'):AtualPoint.getY();
                            Reta.drawReta2(x1, y1);
                            break;
                        case 1:
                            //Movimento preciso
                            x1 = (ArquivoGCode.charExist(linha_comando, 'X')) ? ArquivoGCode.getValueDouble(linha_comando, 'X'):AtualPoint.getX();
                            y1 = (ArquivoGCode.charExist(linha_comando, 'Y')) ? ArquivoGCode.getValueDouble(linha_comando, 'Y'):AtualPoint.getY();
                            Reta.drawReta(x1, y1);
                            break;
                        case 2:
                            //Arco sentido horário
                            x1 = (ArquivoGCode.charExist(linha_comando, 'X')) ? ArquivoGCode.getValueDouble(linha_comando, 'X'):AtualPoint.getX();
                            y1 = (ArquivoGCode.charExist(linha_comando, 'Y')) ? ArquivoGCode.getValueDouble(linha_comando, 'Y'):AtualPoint.getY();
                            i = (ArquivoGCode.charExist(linha_comando, 'I')) ? ArquivoGCode.getValueDouble(linha_comando, 'I'):0;
                            j = (ArquivoGCode.charExist(linha_comando, 'J')) ? ArquivoGCode.getValueDouble(linha_comando, 'J'):0;
                            Arco.drawArc(x1, y1, i, j, Arco.HORARIO);
                            break;
                        case 3:
                            //Arco sentido anti-horário
                            x1 = (ArquivoGCode.charExist(linha_comando, 'X')) ? ArquivoGCode.getValueDouble(linha_comando, 'X'):AtualPoint.getX();
                            y1 = (ArquivoGCode.charExist(linha_comando, 'Y')) ? ArquivoGCode.getValueDouble(linha_comando, 'Y'):AtualPoint.getY();
                            i = (ArquivoGCode.charExist(linha_comando, 'I')) ? ArquivoGCode.getValueDouble(linha_comando, 'I'):0;
                            j = (ArquivoGCode.charExist(linha_comando, 'J')) ? ArquivoGCode.getValueDouble(linha_comando, 'J'):0;
                            Arco.drawArc(x1, y1, i, j, Arco.ANTI_HORARIO);
                            break;
                        case 92: // Zera as coordenadas sem mover os eixos
                            AtualPoint.setPontoAtual(0, 0);
                            break;
                    }
                }
                AtualPoint.zararEixos();
            }
        });
        
        /*tabbed_pane.add("Visual", plano_cartesiano);
        
        JScrollPane scroll= new JScrollPane();
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        file_commands= new JTextArea(25,40);
        file_commands.setFont(new Font(Font.SERIF, Font.BOLD, 14));
        scroll.setViewportView(file_commands);
        //tabbed_pane.add("G-Code", scroll);*/
        
        this.add(plano_cartesiano);
    }
    
}
