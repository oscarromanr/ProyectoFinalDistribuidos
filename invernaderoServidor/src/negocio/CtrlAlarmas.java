/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import daos.AlarmasDAO;
import entidades.Alarma;
import java.util.List;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



/**
 *
 * @author roman
 */
public class CtrlAlarmas {

    private static volatile CtrlAlarmas instance;
    
    
    public static CtrlAlarmas getInstance() 
    {
        CtrlAlarmas result = instance;
        if (result != null) {
            return result;
        }
        synchronized(CtrlAlarmas.class) 
        {
            if(instance == null) 
            {
                instance = new CtrlAlarmas();
            }
        return instance;
        }
    }
    
    public boolean agregar(Alarma alarma) {
        return new AlarmasDAO().agregar(alarma);
    }

    public boolean actualizar(Long idAlarma, Alarma alarma) {
        return new AlarmasDAO().actualizar(idAlarma, alarma);

    }

    public boolean eliminar(Long idAlarma) {
        return new AlarmasDAO().eliminar(idAlarma);

    }

    public Alarma consultarPorId(Long idAlarma) {
        return new AlarmasDAO().consultarPorId(idAlarma);

    }

    public List<Alarma> consultarTodos() {
        return new AlarmasDAO().consultarTodas();

    }
    
    public List<Alarma> consultarPorTipo(String tipo) {
        return new AlarmasDAO().consultarPorTipo(tipo);
    }
    
    public void enviarCorreo(String destinatario, String asunto, String contenido) {
        //Configuración de las propiedades
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp-mail.outlook.com");
        properties.put("mail.smtp.port", "587");

        //Configuración de la autenticación
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("sensoresalarma@hotmail.com", "alarmasensores123456");
            }
        });

        try {
            //Creación del mensaje
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("sensoresalarma@hotmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);
            message.setText(contenido);

            //Envío del mensaje
            Transport.send(message);

            System.out.println("Correo electrónico enviado correctamente.");
        } catch (MessagingException e) {
            System.out.println("Error al enviar el correo electrónico: " + e.getMessage());
        }
    }


    
}