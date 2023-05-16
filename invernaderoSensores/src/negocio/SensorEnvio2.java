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
public class SensorEnvio2 extends Thread{
    
    private boolean estado;
    private final ConnectionFactory factory;
    private final ObjectMapper mapper;
    private final String exchangeName;
    private static volatile SensorEnvio2 instance;
    
    public SensorEnvio2()
    {
        this.estado = true;
        this.factory = new ConnectionFactory();
        this.factory.setHost("localhost");
        this.mapper = new ObjectMapper();
        this.exchangeName = "datos_exchange";
    }
    
    public static SensorEnvio2 getInstance() 
    {
        SensorEnvio2 result = instance;
        if (result != null) {
            return result;
        }
        synchronized(SensorEnvio2.class) 
        {
            if(instance == null) 
            {
                instance = new SensorEnvio2();
            }
        return instance;
        }
    }
    
    @Override
    public void run() {
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(exchangeName, "fanout");

            while (estado) {
                List<Sensor> listaSensores = CtrlSensores.getInstance().consultarTodos();
                List<DatosSensor> listaDatosSensores = CtrlDatosSensores.getInstance().generarDatos(listaSensores);

                for (DatosSensor datosSensor : listaDatosSensores) {
                    String message = mapper.writeValueAsString(datosSensor);
                    channel.basicPublish(exchangeName, "", null, message.getBytes());
                }

                TimeUnit.MILLISECONDS.sleep(5000);
            }

            channel.close();
            connection.close();
        } catch (IOException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}
