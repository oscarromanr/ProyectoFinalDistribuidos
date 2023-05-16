/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package invernaderogateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.ConnectionFactory;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import entidades.DatosSensor;

/**
 *
 * @author roman
 */
public class Gateway {
    
    public final String QUEUE_NAME = "datos_queue";
    private DatosSensor datosSensor;
    private final JTextArea areatexto;
    public final static int PUERTO_SERVIDOR = 8888;
    public final static String IP_SERVIDOR = "localhost";
    
    public Gateway(JTextArea areatexto) {
        this.areatexto = areatexto;
    }
    
    public void recibirDatos() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            // Código para recibir entidades
            ObjectMapper objectMapper = new ObjectMapper();
            channel.basicConsume(QUEUE_NAME, true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    datosSensor = objectMapper.readValue(message, DatosSensor.class);
                    areatexto.append("El gateway recibió datos: " + datosSensor + "\n");
                    enviarDatos();
                }
            });
        } catch (IOException | TimeoutException  e) {
            e.printStackTrace();
        }
    }
    
    public void enviarDatos(){
        try {
            Socket socket = new Socket(IP_SERVIDOR, PUERTO_SERVIDOR);
            try (DataOutputStream flujoSalida = new DataOutputStream(socket.getOutputStream())){
                String temperatura = formatoBinario(datosSensor.getTemperatura().toString());
                String humedad = formatoBinario(datosSensor.getHumedad().toString());
                String idSensor = formatoBinario(String.valueOf(datosSensor.getIdSensor()));
                flujoSalida.writeUTF("Sensor: "+ idSensor +", Temperatura: " + temperatura + " grados" + ", Humedad: " + humedad + "\n");
            }            
        } catch (IOException ex) {
            Logger.getLogger(Gateway.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void getBits(StringBuilder sb, byte b) {
        for (int i = 0; i < 8; i++) {
            sb.append((b & 128) == 0 ? 0 : 1);
            b <<= 1;
        }
        sb.append(' ');
    }
 
    public static String formatoBinario(String s) {
        byte[] bytes = s.getBytes();
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            getBits(sb, b);
        }
        return sb.toString().trim();
    }
    
}
