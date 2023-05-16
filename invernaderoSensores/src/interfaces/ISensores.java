/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import entidades.Sensor;
import java.util.List;

/**
 *
 * @author roman
 */
public interface ISensores {
    public boolean agregar(Sensor sensor);
    public boolean actualizar(Long idSensor, Sensor sensor);
    public boolean eliminar(Long idSensor);
    public Sensor consultarPorId(Long idSensor);
    public List<Sensor> consultarTodos();
}
