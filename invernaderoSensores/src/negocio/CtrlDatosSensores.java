/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import entidades.DatosSensor;
import entidades.Sensor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

/**
 *
 * @author roman
 */
public class CtrlDatosSensores {
    
    private double temperaturaMaxima = 50;
    private double temperaturaMinima = 20;
    private double humedadMinima = 10;
    private double humedadMaxima = 100;
    private Random random = new Random();
    private static volatile CtrlDatosSensores instance;
    
    public static CtrlDatosSensores getInstance() 
    {
        CtrlDatosSensores result = instance;
        if (result != null) {
            return result;
        }
        synchronized(CtrlDatosSensores.class) 
        {
            if(instance == null) 
            {
                instance = new CtrlDatosSensores();
            }
        return instance;
        }
    }
    
    public List<DatosSensor> generarDatos(List<Sensor> listaSensores) {
        
        
        int i = 0;
        List<DatosSensor> listaDatosSensores = new ArrayList<>();
        while(i < listaSensores.size())
        {
            Calendar fecha = new GregorianCalendar();
            double temperatura = temperaturaMinima + (temperaturaMaxima - temperaturaMinima) * random.nextDouble();
            double humedad = humedadMinima + (humedadMaxima - humedadMinima) * random.nextDouble();
            DatosSensor datosSensor = new DatosSensor(listaSensores.get(i).getId(), temperatura, humedad, fecha.getTime());
            listaDatosSensores.add(datosSensor);
            i++;
        }
        
        return listaDatosSensores;
    }
}
