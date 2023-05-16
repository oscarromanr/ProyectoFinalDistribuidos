/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import conexion.ConexionBD;
import entidades.Alarma;
import interfaces.IAlarmas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fernando
 */
public class AlarmasDAO extends ConexionBD implements IAlarmas {

    @Override
    public boolean agregar(Alarma alarma) {
        try (Connection conn = getConexion();
             PreparedStatement pst = conn.prepareStatement("INSERT INTO alarmas (limite, tipo) VALUES (?, ?)")) {
            pst.setDouble(1, alarma.getLimite());
            pst.setString(2, alarma.getTipo());
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
    public boolean actualizar(Long idAlarma, Alarma alarma) {
        try (Connection conn = getConexion();
             PreparedStatement pst = conn.prepareStatement("UPDATE alarmas SET limite = ?, tipo = ? WHERE id_alarma = ?")) {
            pst.setDouble(1, alarma.getLimite());
            pst.setString(2, alarma.getTipo());
            pst.setLong(3, idAlarma);
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
    public boolean eliminar(Long idAlarma) {
        try (Connection conn = getConexion();
             PreparedStatement pst = conn.prepareStatement("DELETE FROM alarmas WHERE id_alarma = ?")) {
            pst.setLong(1, idAlarma);
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
    public Alarma consultarPorId(Long idAlarma) {
        try (Connection conn = getConexion();
             PreparedStatement pst = conn.prepareStatement("SELECT * FROM alarmas WHERE id_alarma = ?")) {
            pst.setLong(1, idAlarma);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Alarma alarma = new Alarma();
                alarma.setId(rs.getLong("id_alarma"));
                alarma.setLimite(rs.getDouble("limite"));
                alarma.setTipo(rs.getString("tipo"));
                return alarma;
            }
            return null;
        } catch (SQLException e) {
            System.out.println("Error en: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Alarma> consultarTodas() {
        List<Alarma> alarmas = new ArrayList<>();
        try (Connection conn = getConexion();
             PreparedStatement pst = conn.prepareStatement("SELECT * FROM alarmas");
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Alarma alarma = new Alarma();
                alarma.setId(rs.getLong("id_alarma"));
                alarma.setLimite(rs.getDouble("limite"));
                alarma.setTipo(rs.getString("tipo"));
                alarmas.add(alarma);
            }
        } catch (SQLException e) {
            System.out.println("Error en: " + e.getMessage());
        }
        return alarmas;
    }

    @Override
    public List<Alarma> consultarPorTipo(String tipo) {
        List<Alarma> alarmas = new ArrayList<>();
        try (Connection conn = getConexion();
             PreparedStatement pst = conn.prepareStatement("SELECT * FROM alarmas WHERE tipo = ?");) {
            pst.setString(1, tipo);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Alarma alarma = new Alarma();
                alarma.setId(rs.getLong("id_alarma"));
                alarma.setLimite(rs.getDouble("limite"));
                alarma.setTipo(rs.getString("tipo"));
                alarmas.add(alarma);
            }
        } catch (SQLException e) {
            System.out.println("Error en: " + e.getMessage());
        }
        return alarmas;
    }
    
}
