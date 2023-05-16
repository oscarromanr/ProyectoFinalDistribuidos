/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import conexion.ConexionBD;
import entidades.Sensor;
import interfaces.ISensores;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author roman
 */
public class SensoresDAO extends ConexionBD implements ISensores {

    @Override
    public boolean agregar(Sensor sensor) {
        try (Connection conn = getConexion();
             PreparedStatement pst = conn.prepareStatement("INSERT INTO sensores (nombre, marca) VALUES (?, ?)")) {
            pst.setString(1, sensor.getNombre());
            pst.setString(2, sensor.getMarca());
            int filasAfectadas = pst.executeUpdate();
            if (filasAfectadas == 0) {
                return false; // no se agregó ninguna fila
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Error en: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizar(Long idSensor, Sensor sensor) {
        try (Connection conn = getConexion();
             PreparedStatement pst = conn.prepareStatement("UPDATE sensores SET nombre = ?, marca = ? WHERE id_sensor = ?")) {
            pst.setString(1, sensor.getNombre());
            pst.setString(2, sensor.getMarca());
            pst.setLong(3, idSensor);
            int filasAfectadas = pst.executeUpdate();
            if (filasAfectadas == 0) {
                return false; // no se actualizó ninguna fila
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Error en: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(Long idSensor) {
        try (Connection conn = getConexion();
             PreparedStatement pst = conn.prepareStatement("DELETE FROM sensores WHERE id_sensor = ?")) {
            pst.setLong(1, idSensor);
            int filasAfectadas = pst.executeUpdate();
            if (filasAfectadas == 0) {
                return false; // no se eliminó ninguna fila
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Error en: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Sensor consultarPorId(Long idSensor) {
        try (Connection conn = getConexion();
             PreparedStatement pst = conn.prepareStatement("SELECT * FROM sensores WHERE id_sensor = ?")) {
            pst.setLong(1, idSensor);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Sensor sensor = new Sensor();
                sensor.setId(rs.getLong("id_sensor"));
                sensor.setNombre(rs.getString("nombre"));
                sensor.setMarca(rs.getString("marca"));
                return sensor;
            }
            return null;
        } catch (SQLException e) {
            System.out.println("Error en: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Sensor> consultarTodos() {
        List<Sensor> sensores = new ArrayList<>();
        try (Connection conn = getConexion();
             PreparedStatement pst = conn.prepareStatement("SELECT * FROM sensores");
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Sensor sensor = new Sensor();
                sensor.setId(rs.getLong("id_sensor"));
                sensor.setNombre(rs.getString("nombre"));
                sensor.setMarca(rs.getString("marca"));
                sensores.add(sensor);
            }
        } catch (SQLException e) {
            System.out.println("Error en: " + e.getMessage());
        }
        return sensores;
    }
}