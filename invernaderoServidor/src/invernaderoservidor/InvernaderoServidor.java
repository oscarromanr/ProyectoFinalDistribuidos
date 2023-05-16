/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package invernaderoservidor;

import guis.frmGraficaDatosTemperatura;
import guis.frmServidor;
import negocio.ConversorBinario;
import negocio.CtrlAlarmas;

/**
 *
 * @author Fernando
 */
public class InvernaderoServidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new frmServidor().setVisible(true);
        //CtrlAlarmas.getInstance().enviarCorreo("rob8meza@hotmail.com", "HOLAA", "funciono");
    }
    
}
