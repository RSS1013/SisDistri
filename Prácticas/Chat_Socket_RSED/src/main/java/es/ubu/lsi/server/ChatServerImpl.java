package es.ubu.lsi.server;

import es.ubu.lsi.common.ChatMessage;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
* Implementación del servidor de chat.
 * 
 * Gestiona las conexiones de los clientes y el envío de mensajes.
 *  
 * @authors Estíbalitz Diez & Ricardo Sevilla
 * @version 1.0
 */
public class ChatServerImpl implements ChatServer {
    private static final int DEFAULT_PORT = 1500;
    private int clientId = 0;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    private boolean alive;
    
    private Map<Integer, ArrayList<Object>> clients = new HashMap<>();
    private Map<Integer, ArrayList<Object>> clientsBan = new HashMap<>();
    private ServerSocket serverSocket;

    /**
     * Método principal que inicia el servidor.
     * @param args Argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        System.out.println("Servidor en ejecución en el puerto " + DEFAULT_PORT);
        ChatServerImpl server = new ChatServerImpl();
        server.startup();
    }

    /**
     * Inicia el servidor y acepta conexiones de clientes.
     */
    public void startup() {
        alive = true;
        try {
            serverSocket = new ServerSocket(DEFAULT_PORT);
            while (alive) {
                Socket clientSocket = serverSocket.accept();
                clientId++;

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                out.println(clientId);
                String username = in.readLine();
                int userHash = username.hashCode();

                if (clients.containsKey(userHash)) {
                    out.println("Error: Nombre de usuario en uso.");
                    clientSocket.close();
                } else {
                    System.out.println("Cliente conectado: " + username);
                    ArrayList<Object> clientInfo = new ArrayList<>();
                    clientInfo.add(clientSocket);
                    clientInfo.add(username);
                    clientInfo.add(sdf.format(new Date()));
                    clientInfo.add(clientId);
                    clients.put(userHash, clientInfo);

                    ServerThreadForClient clientThread = new ServerThreadForClient(clientSocket, clientId, username);
                    clientThread.start();
                }
            }
        } catch (IOException e) {
            System.out.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }

    /**
     * Envía un mensaje a todos los clientes, excepto al remitente y a los bloqueados.
     * @param msg Mensaje a enviar, encapsulado en un objeto ChatMessage.
     */
    public void broadcast(ChatMessage msg) {
        int senderId = msg.getClientId();
        String sender = getUserById(senderId);
        String message = sender + ": " + msg.getMessage();

        for (Map.Entry<Integer, ArrayList<Object>> entry : clients.entrySet()) {
            if (senderId != (int) entry.getValue().get(3)) { // Evita enviar al remitente
                try {
                    Socket clientSocket = (Socket) entry.getValue().get(0);
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    out.println(message);
                } catch (IOException e) {
                    System.out.println("Error al enviar mensaje a " + entry.getValue().get(1));
                }
            }
        }
    }

    /**
     * Elimina un cliente de la lista de clientes conectados.
     * @param id Identificador del cliente a eliminar.
     */
    public void remove(int id) {
        String username = getUserById(id);
        if (username != null) {
            clients.remove(username.hashCode());
            System.out.println("Cliente eliminado: " + username);
        }
    }

    /**
     * Obtiene el nombre de usuario asociado a un ID de cliente.
     * @param id Identificador del cliente.
     * @return Nombre de usuario correspondiente o "Desconocido" si no se encuentra.
     */
    private String getUserById(int id) {
        for (Map.Entry<Integer, ArrayList<Object>> entry : clients.entrySet()) {
            if ((int) entry.getValue().get(3) == id) {
                return (String) entry.getValue().get(1);
            }
        }
        return "Desconocido"; // Devuelve un nombre por defecto en caso de error
    }

    /**
     * Apaga el servidor cerrando todas las conexiones activas.
     */
    @Override
    public void shutdown() {
        alive = false;
        try {
            for (Map.Entry<Integer, ArrayList<Object>> entry : clients.entrySet()) {
                Socket clientSocket = (Socket) entry.getValue().get(0);
                clientSocket.close();
            }
            if (serverSocket != null) {
                serverSocket.close();
            }
            System.out.println("Servidor apagado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al cerrar el servidor: " + e.getMessage());
        }
    }

    /**
     * Clase interna `ServerThreadForClient` que maneja comunicación con clientes.
     */
    private class ServerThreadForClient extends Thread {
        private Socket socket;
        private int id;
        private String username;
        private BufferedReader in;
        private PrintWriter out;

        /**
         * Constructor de la clase interna para manejar la conexión de un cliente.
         * @param socket Socket de conexión del cliente.
         * @param id Identificador único del cliente.
         * @param username Nombre del usuario.
         */
        public ServerThreadForClient(Socket socket, int id, String username) {
            this.socket = socket;
            this.id = id;
            this.username = username;
            try {
                this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.out = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                System.out.println("Error al crear los flujos de E/S para " + username);
            }
        }
        
        /**
         * Método de ejecución del hilo que gestiona la comunicación con el cliente.
         */
        @Override
        public void run() {
            try {
                while (true) {
                    String input = in.readLine();
                    if (input == null) {
                        break; // Cliente desconectado
                    }

                    if (input.equalsIgnoreCase("logout")) {
                        System.out.println("Cliente " + username + " ha salido del chat.");
                        remove(id);
                        break;
                    } else if (input.startsWith("ban ")) {
                        String[] parts = input.split(" ");
                        if (parts.length > 1) {
                            banUser(id, parts[1]);
                        } else {
                            out.println("Uso incorrecto. Debes escribir: ban <usuario>");
                        }
                    } else if (input.startsWith("unban ")) {
                        String[] parts = input.split(" ");
                        if (parts.length > 1) {
                            unbanUser(id, parts[1]);
                        } else {
                            out.println("Uso incorrecto. Debes escribir: unban <usuario>");
                        }
                    } else {
                        if (!input.trim().isEmpty()) {
                            ChatMessage msg = new ChatMessage(id, ChatMessage.MessageType.TEXT, input);
                            broadcast(msg);
                        } else {
                            out.println("No puedes enviar un mensaje vacío.");
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Cliente desconectado: " + username);
            } finally {
                try {
                    if (in != null) in.close();
                    if (out != null) out.close();
                    if (socket != null) socket.close();
                } catch (IOException e) {
                    System.out.println("Error al cerrar la conexión de " + username);
                }
            }
        }

        /**
         * Bloquea a un usuario y lo mueve a la lista de baneados.
         * @param id ID del cliente que ejecuta la acción.
         * @param bannedUsername Nombre de usuario a bloquear.
         */
        private void banUser(int id, String bannedUsername) {
            int bannedId = getUserId(bannedUsername);
            if (bannedId != -1) {
                clientsBan.put(bannedId, clients.remove(bannedId));
                System.out.println(username + " ha baneado a " + bannedUsername);
            }
        }

        /**
         * Desbloquea a un usuario y lo devuelve a la lista de clientes activos.
         * @param id ID del cliente que ejecuta la acción.
         * @param unbannedUsername Nombre de usuario a desbloquear.
         */
        private void unbanUser(int id, String unbannedUsername) {
            int unbannedId = getUserId(unbannedUsername);
            if (unbannedId != -1) {
                clients.put(unbannedId, clientsBan.remove(unbannedId));
                System.out.println(username + " ha desbaneado a " + unbannedUsername);
            }
        }

        /**
         * Obtiene el ID de un usuario a partir de su nombre.
         * @param username Nombre del usuario.
         * @return ID del usuario o -1 si no se encuentra.
         */
        private int getUserId(String username) {
            for (Map.Entry<Integer, ArrayList<Object>> entry : clients.entrySet()) {
                if (entry.getValue().get(1).equals(username)) {
                    return (int) entry.getValue().get(3);
                }
            }
            return -1;
        }
    }
}
