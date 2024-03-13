package ru.dmitriispiridonov.restfulservice.ws;

import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

@RequiredArgsConstructor
public class BackendWebSocketHandler implements WebSocketHandler {

    private final WebSocketSession proxyingSession;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {}

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if (proxyingSession.isOpen()) {
            proxyingSession.sendMessage(message);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {}

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        proxyingSession.close();
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
