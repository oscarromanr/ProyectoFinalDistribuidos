/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import daos.SensoresDAO;
import entidades.DatosSensor;
import entidades.Sensor;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.swing.JOptionPane;

/**
 *
 * @author roman
 */
public class CtrlSensores {

    private static volatile CtrlSensores instance;
    
    
    public static CtrlSensores getInstance() 
    {
        CtrlSensores result = instance;
        if (result != null) {
            return result;
        }
        synchronized(CtrlSensores.class) 
        {
            if(instance == null) 
            {
                instance = new CtrlSensores();
            }
        return instance;
        }
    }
    
    public boolean agregar(Sensor sensor) {
        return new SensoresDAO().agregar(sensor);
    }

    public boolean actualizar(Long idSensor, Sensor sensor) {
        return new SensoresDAO().actualizar(idSensor, sensor);

    }

    public boolean eliminar(Long idSensor) {
        return new SensoresDAO().eliminar(idSensor);

    }

    public Sensor consultarPorId(Long idSensor) {
        return new SensoresDAO().consultarPorId(idSensor);

    }

    public List<Sensor> consultarTodos() {
        return new SensoresDAO().consultarTodos();

    }
    
    public void apagarSensores()
    {
        SensorEnvio.getInstance().estado = false;
    }
    
    public void encenderSensores() throws InterruptedException
    {
        SensorEnvio.getInstance().start();
    }
    
}