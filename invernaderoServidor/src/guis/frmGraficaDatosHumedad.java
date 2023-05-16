/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package guis;

import entidades.Alarma;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import interfaces.iEventListener;
import java.util.List;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import negocio.ConversorBinario;
import negocio.CtrlAlarmas;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.DefaultXYDataset;

/**
 *
 * @author Fernando
 */
public class frmGraficaDatosHumedad extends javax.swing.JFrame implements iEventListener{
    
    private JPanel panel;
    DefaultXYDataset dataset;
    
    public frmGraficaDatosHumedad() {
        dataset = new DefaultXYDataset();
        setTitle("Grafica de humedad");
        setSize(750,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(false);
        init();
    }
    
    private void promedio()
    {
        //double[] xDataPromedioAgregar = new double[dataset.getItemCount(0)+1];
        //double[] yDataPromedioAgregar = new double[dataset.getItemCount(0)+1];
        int i = 0; int pro = -1;
        double[] xDataPromedioAgregar = new double[dataset.getItemCount(0)];
        double[] yDataPromedioAgregar = new double[dataset.getItemCount(0)];
        while(i < dataset.getSeriesCount())
        {
            
            if(dataset.getSeriesKey(i) != "Promedio")
            {
                int j = 0;
                while(j < dataset.getItemCount(i))
                {
                    double a = yDataPromedioAgregar[j];
                    double b = dataset.getYValue(i, j);
                    //double c = dataset.getSeriesCount();
                    yDataPromedioAgregar[j] = (a + b);
                    xDataPromedioAgregar[j] = j+1;
                    j++;
                }
            }
            else
            {
                pro = i;
            }
            i++;
        }
        if(pro != -1)
        {
            int h = 0;
            while (h < dataset.getItemCount(pro)) {
                yDataPromedioAgregar[h] = yDataPromedioAgregar[h] / (dataset.getSeriesCount() - 1);
                h++;
            }
        }
        double[][] dataSeriePromedioAgregar = {xDataPromedioAgregar, yDataPromedioAgregar};
        dataset.addSeries("Promedio", dataSeriePromedioAgregar);
    }
    
    private void anadirDatos(String temperatura, String idSensor)
    {
        double humedadDouble = Double.parseDouble(temperatura);
        List<Alarma> alarmas = CtrlAlarmas.getInstance().consultarPorTipo("Humedad");
        int u = 0;
        while(u < alarmas.size())
        {
            if(alarmas.get(u).getLimite() <= humedadDouble)
            {
                CtrlAlarmas.getInstance().enviarCorreo("rob8meza@hotmail.com", "Alarma activada", "La alarma con el id: "+alarmas.get(u).getId()+" ha sonado, "+alarmas.get(u).getTipo()+": "+humedadDouble+", Sensor: "+idSensor);
                System.out.println("La alarma con el id: "+alarmas.get(u).getId()+" ha sonado, "+alarmas.get(u).getTipo()+": "+humedadDouble+", Sensor: "+idSensor);
            }
            u++;
        }
        int i = 0;
        while(i < dataset.getSeriesCount())
        {
            Comparable nombreSerie = dataset.getSeriesKey(i);
            if(nombreSerie.equals(idSensor))
            {
                double[] xDataAgregar = new double[dataset.getItemCount(i)+1];
                double[] yDataAgregar = new double[dataset.getItemCount(i)+1];
                int j = 0;
                while(j < dataset.getItemCount(i))
                {
                    xDataAgregar[j] = dataset.getXValue(i, j);
                    yDataAgregar[j] = dataset.getYValue(i, j);
                    j++;
                }
                xDataAgregar[j] = (dataset.getItemCount(i)+1);
                yDataAgregar[j] = humedadDouble;
                
                double[][] dataSerieAgregar = {xDataAgregar, yDataAgregar};
                dataset.addSeries(nombreSerie, dataSerieAgregar);
                promedio();
                //actualizarGrafica();
                return;
            }
            i++;
        }
        double[] xDataAgregar = new double[1];
        double[] yDataAgregar = new double[1];
        xDataAgregar[0] = 1.0;
        yDataAgregar[0] = humedadDouble;
        double[][] dataSerieAgregar = {xDataAgregar, yDataAgregar};
        dataset.addSeries(idSensor, dataSerieAgregar);
        promedio();
    }
    
    private void init() {
        panel = new JPanel();
        getContentPane().add(panel);

        JFreeChart chartTemperatura = ChartFactory.createXYLineChart(
            "Medidas de humedad", // Título del gráfico
            "Fecha", // Etiqueta del eje X
            "Humedad", // Etiqueta del eje Y
            dataset // Dataset con los datos
        );
  
        ChartPanel chartPanelTemperatura = new ChartPanel(chartTemperatura);         
        
        panel.add(chartPanelTemperatura);
        panel.revalidate();
        panel.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmGraficaDatosHumedad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmGraficaDatosHumedad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmGraficaDatosHumedad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmGraficaDatosHumedad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmGraficaDatosHumedad().setVisible(true);
            }
        });
    }

    @Override
    public void update(String mensaje) {
        String palabraBuscadaTemperatura = "Temperatura: ";
        String palabraBuscadaHumedad = "Humedad: ";
        String palabraBuscadaGrados = " grados";
        String palabraBuscadaSensor = "Sensor: ";

        int indiceInicioPalabraSensor = mensaje.indexOf(palabraBuscadaSensor);
        int indiceInicioPalabraTemperatura = mensaje.indexOf(palabraBuscadaTemperatura);
        int indiceInicioPalabraHumedad = mensaje.indexOf(palabraBuscadaHumedad);
        int indiceInicioPalabraGrados = mensaje.indexOf(palabraBuscadaGrados);

        String idSensor = "0";
        if (indiceInicioPalabraSensor != -1) {
            int indiceFinPalabraSensor = indiceInicioPalabraSensor + palabraBuscadaSensor.length();
            String sensorBinario = mensaje.substring(indiceFinPalabraSensor, (indiceInicioPalabraTemperatura - 2));
            idSensor = ConversorBinario.getInstance().revertirFormatoBinario(sensorBinario);
        } else {
            System.out.println("La palabra '" + palabraBuscadaTemperatura + "' no se encuentra en la cadena.");
        }
        
        String humedad = "0";
        //"Temperatura: 00110010 00110100 00101110 00110011 00111001 00111000 00111001 00110001 00110111 00111000 00110100 00110010 00110011 00111001 00110010 00110011 00110011 grados, Humedad: 00110111 00110100 00101110 00110001 00110000 00110001 00110101 00111000 00111001 00110101 00111001 00111001 00110111 00110100 00110010 00111001 00110100 "
        if (indiceInicioPalabraHumedad != -1) {
            int indiceFinPalabraHumedad = indiceInicioPalabraHumedad + palabraBuscadaHumedad.length();
            String humedadBinario = mensaje.substring(indiceFinPalabraHumedad, mensaje.length()-1);
            humedad = ConversorBinario.getInstance().revertirFormatoBinario(humedadBinario);
        } else {
            System.out.println("La palabra '" + palabraBuscadaHumedad + "' no se encuentra en la cadena.");
        }
        anadirDatos(humedad, idSensor);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
