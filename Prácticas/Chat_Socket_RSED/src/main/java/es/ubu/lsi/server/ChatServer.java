package es.ubu.lsi.server;

import java.rmi.RemoteException;
import es.ubu.lsi.common.*;

/**
 * Interfaz que define las operaciones principales de un servidor de chat.
 * Permite iniciar el servidor, enviar mensajes a todos los clientes, apagar el servidor y eliminar clientes.
 * 
 * @authors Estíbalitz Diez & Ricardo Sevilla
 */
public interface ChatServer {
    
    /** 
     * Inicia el servidor de chat.
     */
    public void startup();
    
    /**
     * Envía un mensaje a todos los clientes conectados.
     * 
     * @param message El mensaje a enviar.
     * @throws RemoteException Si ocurre un error en la comunicación remota.
     */
    public void broadcast(ChatMessage message) throws RemoteException;
    
    /**
     * Apaga el servidor de chat.
     */
    public void shutdown();
    
    /**
     * Elimina un cliente del servidor de chat.
     * 
     * @param id Identificador del cliente a eliminar.
     * @throws RemoteException Si ocurre un error en la comunicación remota.
     */
    public void remove(int id) throws RemoteException;
}

