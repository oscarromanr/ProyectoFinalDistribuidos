/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package invernaderoservidor;

import interfaces.iEventListener;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Fernando
 */
public class EventManager {
    
    private Map<String, List<iEventListener>> listeners = new HashMap<>();
    public boolean activo = false;

    public EventManager(String... operations) {
        for (String operation : operations) {
            this.listeners.put(operation, new ArrayList<>());
        }
    }
    
    public void subscribe(String eventType, iEventListener listener) {
        List<iEventListener> users = listeners.get(eventType);
        users.add(listener);
    }

    public void unsubscribe(String eventType, iEventListener listener) {
        List<iEventListener> users = listeners.get(eventType);
        users.remove(listener);
    }

    public void notify(String mensaje, String eventType) {
        this.activo = true;
            List<iEventListener> users = new CopyOnWriteArrayList(listeners.get(eventType));
            for (iEventListener listener : users) {
                listener.update(mensaje);
            }
        this.activo = false;
    }
}
