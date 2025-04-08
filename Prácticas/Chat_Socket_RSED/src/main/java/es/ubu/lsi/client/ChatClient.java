package es.ubu.lsi.client;

import es.ubu.lsi.common.ChatMessage;

/**
 * Interfaz que define las operaciones básicas de un cliente de chat.
 * Permite arrancar el cliente, enviar mensajes y cerrar la conexión.
 * 
 *@authors Estíbalitz Diez & Ricardo Sevilla
 */
public interface ChatClient {

    /**
     * Inicia el cliente de chat.
     * 
     * @return true si el cliente se inicia correctamente, false si ocurre un error.
     */
    public boolean start();
    
    /**
     * Envía un mensaje desde el cliente.
     * 
     * @param msg el mensaje a enviar.
     */
    public void sendMessage(ChatMessage msg);
    
    /**
     * Cierra la conexión del cliente de chat.
     */
    public void disconnect();
}

