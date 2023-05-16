/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package invernaderoservidor;

import guis.frmGraficaDatosHumedad;
import guis.frmGraficaDatosTemperatura;
import guis.frmTablaDatosHumedad;
import guis.frmTablaDatosTemperatura;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import listeners.DatosRecibidosListener;

/**
 *
 * @author roman
 */
public class Servidor extends Thread{
    private final JTextArea texto;
    public final static int PUERTO = 8888;
    public frmGraficaDatosTemperatura graficaTemperatura;
    public frmGraficaDatosHumedad graficaHumedad;
    public frmTablaDatosTemperatura tablaTemperatura;
    public frmTablaDatosHumedad tablaHumedad;
    
    public Servidor(JTextArea texto)
    {
        this.texto = texto;
        this.graficaTemperatura = new frmGraficaDatosTemperatura();
        this.graficaHumedad = new frmGraficaDatosHumedad();
        this.tablaTemperatura = new frmTablaDatosTemperatura();
        this.tablaHumedad = new frmTablaDatosHumedad();
    }
    
    @Override
    public void run(){
        try {
            //List<iEventListener> subscribers = initializeSubscribers(); // Lista de suscriptores
            EventManager eventManager = new EventManager("datosRecibidos");
            eventManager.subscribe("datosRecibidos", graficaTemperatura);
            eventManager.subscribe("datosRecibidos", graficaHumedad);
            eventManager.subscribe("datosRecibidos", tablaTemperatura);
            eventManager.subscribe("datosRecibidos", tablaHumedad);
            ServerSocket serverSocket = new ServerSocket(PUERTO);
            texto.append("Esperando la conexión con el gateway... \n");
            while(true) {
                try (Socket socket = serverSocket.accept()) {
                    DataInputStream flujoEntrada = new DataInputStream(socket.getInputStream());
                    String mensaje = flujoEntrada.readUTF();
                    texto.append("Mensaje recibido: " + mensaje);
                    eventManager.notify(mensaje, "datosRecibidos");
                }
            }            
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
//    private List<iEventListener> initializeSubscribers()
//    {
//        List<iEventListener> subscribers;
//        return subscribers;
//    }
    
    
}
