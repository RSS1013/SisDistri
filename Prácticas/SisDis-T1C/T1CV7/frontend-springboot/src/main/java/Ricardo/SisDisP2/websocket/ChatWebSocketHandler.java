package Ricardo.SisDisP2.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private static final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String username = session.getPrincipal() != null ? session.getPrincipal().getName() : "Anónimo";
        String payload = message.getPayload();

        TextMessage outgoingMessage = new TextMessage(username + ": " + payload);
        for (WebSocketSession s : sessions) {
            if (s.isOpen()) {
                s.sendMessage(outgoingMessage);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }
}
