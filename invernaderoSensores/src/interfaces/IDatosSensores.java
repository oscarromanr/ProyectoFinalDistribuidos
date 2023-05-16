/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import entidades.DatosSensor;
import java.util.List;

/**
 *
 * @author roman
 */
public interface IDatosSensores {
    public boolean agregar(DatosSensor sensor);
    public boolean actualizar(Long id, DatosSensor sensor);
    public boolean eliminar(Long id);
    public DatosSensor consultarPorId(Long id);
    public List<DatosSensor> consultarTodos();
}
