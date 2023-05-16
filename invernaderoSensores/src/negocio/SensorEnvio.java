/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import entidades.DatosSensor;
import entidades.Sensor;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fernando
 */
public class SensorEnvio extends Thread{
    
    public boolean estado;
    private static volatile SensorEnvio instance;
    
    
    public static SensorEnvio getInstance() 
    {
        SensorEnvio result = instance;
        if (result != null) {
            return result;
        }
        synchronized(SensorEnvio.class) 
        {
            if(instance == null) 
            {
                instance = new SensorEnvio();
            }
        return instance;
        }
    }
    
    @Override
    public void run() 
    {
        this.estado = true;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare("datos_queue", false, false, false, null);
            
            
            // Código para enviar entidades
            while(estado)
            {
                List<Sensor> listaSensores = CtrlSensores.getInstance().consultarTodos();
                List<DatosSensor> listaDatosSensores = CtrlDatosSensores.getInstance().generarDatos(listaSensores);
                int i = 0;
                while(i < listaDatosSensores.size())
                {
                    ObjectMapper mapper = new ObjectMapper();
                    String message = mapper.writeValueAsString(listaDatosSensores.get(i));
                    channel.basicPublish("", "datos_queue", null, message.getBytes());
                    //JOptionPane.showMessageDialog(this, "El sensor envío al gateway las medidas.", "Información", JOptionPane.INFORMATION_MESSAGE);
                    //this.borrarDatos();
                    i++;
                }
                TimeUnit.MILLISECONDS.sleep(5000);
            }
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException ex) {
            Logger.getLogger(SensorEnvio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
