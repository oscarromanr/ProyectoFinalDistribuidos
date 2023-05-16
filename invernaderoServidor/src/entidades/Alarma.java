/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

/**
 *
 * @author Fernando
 */
public class Alarma {
    
    private Long id;
    private double limite;
    private String tipo;

    public Alarma() {
    }

    public Alarma(Long id, double limite, String tipo) {
        this.id = id;
        this.limite = limite;
        this.tipo = tipo;
    }

    public Alarma(double limite, String tipo) {
        this.limite = limite;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    
    
}
