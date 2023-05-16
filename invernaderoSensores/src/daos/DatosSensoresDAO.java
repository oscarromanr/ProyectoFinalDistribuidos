/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import conexion.ConexionBD;
import entidades.DatosSensor;
import interfaces.IDatosSensores;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author roman
 */
public class DatosSensoresDAO extends ConexionBD implements IDatosSensores {

    @Override
    public boolean agregar(DatosSensor datosSensor) {
        try (Connection conn = getConexion(); PreparedStatement pst = conn.prepareStatement("INSERT INTO datos_sensores (id_sensor, temperatura, humedad, fecha) VALUES (?, ?, ?, ?)")) {
            pst.setLong(1, datosSensor.getIdSensor());
            pst.setDouble(2, datosSensor.getTemperatura());
            pst.setDouble(3, datosSensor.getHumedad());
            pst.setTimestamp(4, new Timestamp(datosSensor.getFecha().getTime()));
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
    public boolean actualizar(Long id, DatosSensor datosSensor) {
        try (Connection conn = getConexion(); PreparedStatement pst = conn.prepareStatement("UPDATE datos_sensores SET id_sensor = ?, temperatura = ?, humedad = ?, fecha = ? WHERE id = ?")) {
            pst.setLong(1, datosSensor.getIdSensor());
            pst.setDouble(2, datosSensor.getTemperatura());
            pst.setDouble(3, datosSensor.getHumedad());
            pst.setTimestamp(4, new Timestamp(datosSensor.getFecha().getTime()));
            pst.setLong(5, id);
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
    public boolean eliminar(Long id) {
        try (Connection conn = getConexion(); PreparedStatement pst = conn.prepareStatement("DELETE FROM datos_sensores WHERE id = ?")) {
            pst.setLong(1, id);
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
    public DatosSensor consultarPorId(Long id) {
        try (Connection conn = getConexion(); PreparedStatement pst = conn.prepareStatement("SELECT * FROM datos_sensores WHERE id = ?")) {
            pst.setLong(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                DatosSensor datosSensor = new DatosSensor();
                datosSensor.setId(rs.getInt("id"));
                datosSensor.setIdSensor(rs.getLong("id_sensor"));
                datosSensor.setTemperatura(rs.getDouble("temperatura"));
                datosSensor.setHumedad(rs.getDouble("humedad"));
                datosSensor.setFecha(rs.getTimestamp("fecha"));
                return datosSensor;
            }
            return null;
        } catch (SQLException e) {
            System.out.println("Error en: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<DatosSensor> consultarTodos() {
        List<DatosSensor> datosSensores = new ArrayList<>();
        try (Connection conn = getConexion(); PreparedStatement pst = conn.prepareStatement("SELECT * FROM datos_sensores"); ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                DatosSensor datosSensor = new DatosSensor();
                datosSensor.setId(rs.getInt("id"));
                datosSensor.setIdSensor(rs.getLong("id_sensor"));
                datosSensor.setTemperatura(rs.getDouble("temperatura"));
                datosSensor.setHumedad(rs.getDouble("humedad"));
                datosSensor.setFecha(rs.getTimestamp("fecha"));
                datosSensores.add(datosSensor);
            }
        } catch (SQLException e) {
            System.out.println("Error en: " + e.getMessage());
        }
        return datosSensores;
    }

}
