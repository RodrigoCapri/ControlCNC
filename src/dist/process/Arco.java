package dist.process;

import javafx.scene.paint.Color;

public class Arco {
	
    public static final int HORARIO = 1;
    public static final int ANTI_HORARIO = 0;
    private static final double CONST = 0.01; //0.002
    
    public static void drawArc(double x, double y, double i, double j, int dir) {
        
        double pontoA[] = { AtualPoint.getX(), AtualPoint.getY() }; //Inicio do arco
        double pontoB[] = { x, y }; //Final do arco
        double pontoC[] = { (AtualPoint.getX()+i), (AtualPoint.getY()+j) }; //Centro do arco
        
        //O raio é dado pela distancia do ponto A ao C
        //Faz a diferença dos dois pontos
        double vetor[] = {
            (pontoC[0]-pontoA[0]),
            (pontoC[1]-pontoA[1])
        };
        
        //Obtém o raio fazndo o modulo do vetor resultante da diferença de dois pontos
        double raio = Math.sqrt( Math.pow(vetor[0], 2)+ Math.pow(vetor[1], 2) );
        
        //Converte a coordenada em seu respectivo grau na cricunferência
        //A base para a conversão é posicionar o centro em zero
        double gInit = Arco.toGraus( (pontoA[0]-pontoC[0]) , (pontoA[1]-pontoC[1]) ); //Pega o grau do ponto A(inicio)
        double gEnd = Arco.toGraus( (pontoB[0]-pontoC[0]) , (pontoB[1]-pontoC[1]) ); //Pega o grau do ponto B(final)
        
        switch(dir){
            
            case Arco.HORARIO:
                
                    if ( gInit <= gEnd ){
                        gInit += 360;
                    }
                    
                    while(gInit >= gEnd){

                        double[] p = Arco.toCoordenada(gInit, raio, pontoC);
                        Reta.drawReta( p[0] , p[1], 0.5, Color.MAGENTA );
                        gInit -= Arco.CONST;
                        
                    }
                    
                break;
            case Arco.ANTI_HORARIO: //G3
                
                    if ( gInit >= gEnd ){
                        gEnd += 360;
                    }
                    
                    while(gInit < gEnd){

                        double[] p = Arco.toCoordenada(gInit, raio, pontoC);
                        Reta.drawReta( p[0] , p[1], 0.5, Color.YELLOW );
                        gInit += Arco.CONST;
                        
                    }
                    
                break;
                
        }
        //Desenha mais uma reta até o ponto final do comando
        //para garantir a precisão do movimento
        AtualPoint.setPontoAtual(x, y);
    }
    
    //Pega o grau da coordenada usando a conversão de coordenadas
    //coordenada cartesiana para coordenada cilindrica
    private static double toGraus(double x, double y){
        /*
            Para converter R3 em Cilindrica devemos trabalhar com a Tangente.
            Mas nesse caso trabalharemos com o Cosseno, pois assim, não teremos
            problemas de dividir algum valor por zero.
        */
        
        //Primeiro converte a coordenada cartesiana em cilindrica
        //para isso, devemos calcular o módulo do vetor
        double r = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        
        //Fazemos então r|x para obter o angulo em radianos e depois converte para graus
        double grad = Math.toDegrees( Math.acos(x/r) );
        
        /*
            A coordenada cilindrica é constituida pelo raio e o ângulo
        e o eixo Z que, nesse caso não entra no calculo.
            
            O resultado sempre retorna de 0º a 180º, presicamos que retorne
        de 0º a 360º.
            Então quando o Y é menor que zero, subtraimos de 360 o grau calculado.
        */
        return ( y < 0 ) ? (360-grad) : grad;
    }
	
    //Converte os graus em coordenadas de uma circunferencia
    //Passando os valores do grau, raio e coordenada central
    private static double[] toCoordenada(double grad, double raio, double[] pontoC){
        
        //Converter o grau em radianos
        double tetha = Math.toRadians(grad);

        //Pega os valores de Seno e Cosseno do angulo
        double cos = Math.cos(tetha);
        double sin = Math.sin(tetha);

        //Faz cos*raio, depois soma a coordenada central
        double x = (cos * raio) + pontoC[0];
        double y = (sin * raio) + pontoC[1];
        
        return new double[] {x, y};
    }
}