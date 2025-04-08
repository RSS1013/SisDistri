package es.ubu.lsi.common;

import java.io.Serializable;

/**
 * Clase ChatMessage que representa un mensaje enviado y recibido en el chat.
 * Contiene el identificador del cliente, el tipo de mensaje y, opcionalmente, un texto.
 * 
 * @authors Estíbalitz Diez & Ricardo Sevilla
 * @version 1.0
 */
public class ChatMessage implements Serializable {
    private static final long serialVersionUID = 1L; // Para la serialización

    private int clientId;   // Identificador del cliente que envía el mensaje
    private MessageType type;  // Tipo de mensaje (TEXT, LOGOUT, SHUTDOWN)
    private String message;       // Texto del mensaje (opcional)

    /**
     * Constructor de ChatMessage.
     * 
     * @param id Identificador del cliente que envía el mensaje.
     * @param type Tipo de mensaje.
     * @param text Contenido del mensaje (puede ser null si no es de tipo texto).
     */
    public ChatMessage(int id, MessageType type, String text) {
        this.clientId = id;
        this.type = type;
        this.message = text;
        System.out.println("[INFO] Cliente " + id + " patrocina el mensaje: " + (text != null ? text : "[Mensaje vacío]"));
    }

    /**
     * Obtiene el identificador del cliente que envió el mensaje.
     * 
     * @return Identificador del cliente.
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * Obtiene el tipo de mensaje.
     * 
     * @return Tipo de mensaje.
     */
    public MessageType getType() {
        return type;
    }

    /**
     * Obtiene el contenido del mensaje (solo si es de tipo TEXT).
     * 
     * @return Texto del mensaje o null si no es un mensaje de texto.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Representación en cadena del mensaje.
     * 
     * @return Una cadena con la información del mensaje.
     */
    @Override
    public String toString() {
        return "[" + clientId + "] " + (message != null ? message : "[Mensaje sin contenido]");
    }

    /**
     * Enumeración interna MessageType que define los tipos de mensajes en el chat.
     */
    public enum MessageType {
        TEXT,    // Mensaje de tipo texto
        LOGOUT,  // Mensaje para cerrar sesión
        SHUTDOWN // Mensaje para apagar el servidor (no se usa en esta práctica)
    }
}

