package es.ubu.lsi.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import es.ubu.lsi.common.*;
import es.ubu.lsi.common.ChatMessage.MessageType;

/**
 * Implementación del cliente de chat.
 * Permite la conexión, envío y recepción de mensajes en el sistema de chat.
 * 
 * @author Ricardo Sevilla
 * @author Estibalitz Diez
 */
public class ChatClientImpl implements ChatClient {
    private final String LOGOUT = "logout";
    private final String HELP = "help";
    private String server;
    private String username;
    private int port;
    private boolean carryOn = true;
    private static int id = 0;
    private static Socket socket = null;

    /**
     * Constructor del cliente de chat.
     * 
     * @param server   Dirección del servidor.
     * @param port     Puerto de conexión.
     * @param username Nombre de usuario.
     */
    public ChatClientImpl(String server, int port, String username) {
        this.server = server;
        this.port = port;
        this.username = username;
    }

    /**
     * Método principal para iniciar el cliente de chat.
     * 
     * @param args Argumentos de la línea de comandos (opcional: nombre de usuario).
     */
    public static void main(String[] args) {
        String server = "localhost";
        String username = args.length > 0 ? args[0] : "Usuario";
        ChatClientImpl cliente = new ChatClientImpl(server, 1500, username);
        cliente.start();
    }

    /**
     * Inicia la conexión con el servidor y gestiona la entrada del usuario.
     * 
     * @return true si la conexión es exitosa, false en caso contrario.
     */
    public boolean start() {
        BufferedReader in = null;
        PrintWriter out = null;
        int attempts = 3;

        while (attempts > 0) {
            try {
                socket = new Socket(server, port);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                out = new PrintWriter(socket.getOutputStream(), true);
                id = Integer.parseInt(in.readLine());
                out.println(username);
                System.out.println("[INFO] " + username + " patrocina el mensaje: Conectado al servidor " + server + ":" + port);
                break;
            } catch (IOException e) {
                System.err.println("[ERROR] No se pudo conectar. Reintentando en 2 segundos...");
                attempts--;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }

        if (attempts == 0) {
            System.err.println("[ERROR] No se pudo conectar al servidor tras múltiples intentos.");
            return false;
        }

        Thread listener = new Thread(new ChatClientListener(in));
        listener.start();

        try {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            while (carryOn) {
                String userInput = stdIn.readLine();
                if (userInput.equalsIgnoreCase(LOGOUT)) {
                    sendMessage(new ChatMessage(id, ChatMessage.MessageType.LOGOUT, userInput));
                    disconnect();
                } else if (userInput.equalsIgnoreCase(HELP)) {
                    showHelp();
                } else {
                    sendMessage(new ChatMessage(id, ChatMessage.MessageType.TEXT, "[" + username + "] " + userInput));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Envía un mensaje al servidor.
     * 
     * @param msg Objeto ChatMessage a enviar.
     */
    public void sendMessage(ChatMessage msg) {
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(msg.getMessage());
            System.out.println("[INFO] " + username + " patrocina el mensaje: " + msg.getMessage());
        } catch (IOException e) {
            System.err.println("[ERROR] No se pudo enviar el mensaje.");
        }
    }

    /**
     * Desconecta al cliente del servidor.
     */
    public void disconnect() {
        carryOn = false;
        System.out.println("[INFO] " + username + " patrocina el mensaje: Desconectado del servidor.");
    }

    /**
     * Muestra los comandos de ayuda disponibles.
     */
    private void showHelp() {
        System.out.println("\n[Comandos disponibles]");
        System.out.println(" - logout : Cierra la sesión.");
        System.out.println(" - help : Muestra esta ayuda.");
        System.out.println(" - @usuario mensaje : Envía un mensaje privado.");
        System.out.println("\n");
    }

    /**
     * Clase interna para escuchar mensajes del servidor en un hilo separado.
     */
    private class ChatClientListener implements Runnable {
        private BufferedReader in;

        /**
         * Constructor del listener del cliente.
         * 
         * @param in Buffer de entrada para leer mensajes.
         */
        public ChatClientListener(BufferedReader in) {
            this.in = in;
        }

        /**
         * Ejecuta el listener para recibir mensajes del servidor.
         */
        public void run() {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
                    System.out.println("[" + timestamp + "] " + message);
                }
            } catch (IOException e) {
                System.err.println("[ERROR] Se perdió la conexión con el servidor.");
            }
            System.out.println("[INFO] Sesión finalizada.");
        }
    }
}
