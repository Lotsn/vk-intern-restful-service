package ru.dmitriispiridonov.restfulservice.ws;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

@RequiredArgsConstructor
public class ProxyingWebSocketHandler implements WebSocketHandler {

    private final Map<String, WebSocketSession> webSocketSessionList = new ConcurrentHashMap<>();
    private final WebSocketClient webSocketClient = new StandardWebSocketClient();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        WebSocketSession backendSession = webSocketClient
                .execute(new BackendWebSocketHandler(session), "ws://echo.websocket.org")
                .join();
        webSocketSessionList.put(session.getId(), backendSession);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if (!webSocketSessionList.containsKey(session.getId())) {
            return;
        }

        WebSocketSession backendSession = webSocketSessionList.get(session.getId());
        if (backendSession.isOpen()) {
            backendSession.sendMessage(message);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {}

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        WebSocketSession backendSession = webSocketSessionList.remove(session.getId());
        if (backendSession != null) {
            backendSession.close();
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
