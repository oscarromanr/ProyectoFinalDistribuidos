/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import entidades.Alarma;
import java.util.List;

/**
 *
 * @author roman
 */
public interface IAlarmas {
    public boolean agregar(Alarma alarma);
    public boolean actualizar(Long idAlarma, Alarma alarma);
    public boolean eliminar(Long idAlarma);
    public Alarma consultarPorId(Long idAlarma);
    public List<Alarma> consultarTodas();
    public List<Alarma> consultarPorTipo(String tipo);
}
