
package models.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author User
 */
public class UsbControl {
    
    private static BufferedReader bufferRead;
    private static OutputStream outStream;
    
    private static SerialPort port;
    
    public UsbControl(){
    }
    
    public static void connect(String com, int baunds){
        
        System.out.print("Abrindo porta "+com+" ... ");

        port = SerialPort.getCommPort(com);
        
        port.setComPortParameters(baunds, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 1000, 0);
        
        port.openPort();

        outStream = port.getOutputStream();

        System.out.println("Conexao iniciada");
         
    }
    
    public static void addReadEvent(ReadEventSerial readEvent){
        
            bufferRead = new BufferedReader(new InputStreamReader(port.getInputStream()));
            
            port.addDataListener(new SerialPortDataListener(){
                
                @Override
                public int getListeningEvents() {
                    return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
                }

                @Override
                public void serialEvent(SerialPortEvent spe) {
                    
                    try {
                        
                        if(bufferRead.ready()){
                            
                           String data = bufferRead.readLine();
                           readEvent.readEvent(data);
                           
                        }
                        
                    } catch (IOException ex) {
                        Logger.getLogger(UsbControl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                
            });
            
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(UsbControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void print(String data){
        
        try {
            
            outStream.write(data.getBytes());
            outStream.flush();
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    
    public static void close(){
        
        if( port != null){
            
            try {
                
                bufferRead.close();
                outStream.close();
                port.closePort();
                
                port = null;
                
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            
            System.out.println("Fechando porta");
        }
        
    }
    
    public static boolean isPortOpen(){
        return port != null;
    }
    
    public static List<String> getListPort(){
        
        List<String> nomes = new ArrayList<>();
        
        SerialPort portas[] = SerialPort.getCommPorts();
        
        for (SerialPort porta : portas) {
            nomes.add(porta.getSystemPortName());
        }
        
        return nomes;
    }
    
    public static List<String> getListPortName(){
        
        List<String> nomes = new ArrayList<>();
        
        SerialPort portas[] = SerialPort.getCommPorts();
        
        for (SerialPort porta : portas) {
            nomes.add(porta.getDescriptivePortName());
        }
        
        return nomes;
    }
    
    public static Map<String, String> getPortasCom(){
        
        Map<String, String> map = new HashMap<>();
        
        SerialPort portas[] = SerialPort.getCommPorts();
        
        for(SerialPort porta : portas){
            map.put(porta.getDescriptivePortName(), porta.getPortDescription());
        }
        
        return map;
    }
}
