/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;
import java.util.Date;

/**
 *
 * @author roman
 */
public class DatosSensor {
    
    private Integer id;
    private Long idSensor;
    private Double temperatura;
    private Double humedad;
    private Date fecha;

    public DatosSensor() {
    }

    public DatosSensor(Long idSensor, Double temperatura, Double humedad, Date fecha) {
        this.idSensor = idSensor;
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Long getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(Long idSensor) {
        this.idSensor = idSensor;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Double temperatura) {
        this.temperatura = temperatura;
    }

    public Double getHumedad() {
        return humedad;
    }

    public void setHumedad(Double humedad) {
        this.humedad = humedad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "DatosSensor{" + "id=" + id + ", idSensor=" + idSensor + ", temperatura=" + temperatura + ", humedad=" + humedad + ", fecha=" + fecha + '}';
    }
    
}
