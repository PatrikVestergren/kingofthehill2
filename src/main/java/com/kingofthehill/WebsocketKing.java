package com.kingofthehill;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kingofthehill.repository.model.CurrentRacer;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/WSking")
public class WebsocketKing {

    private static Set<Session> allSessions = new CopyOnWriteArraySet<>();
    private final static ObjectMapper mapper = new ObjectMapper();

    @OnOpen
    public void open(Session session) {
        System.out.println("WebsocketKing.open");
        allSessions = session.getOpenSessions();
    }

    @OnClose
    public void close(Session session) {
        System.out.println("WebsocketKing.close");
    }

    @OnError
    public void onError(Throwable error) {
        System.out.println("WebsocketKing.onError");
    }

    @OnMessage
    public void handleMessage(String message, Session session) {
        System.out.println("WebsocketKing.handleMessage");
    }


    public synchronized static void sendMessage(CurrentRacer data) throws JsonProcessingException {
        String json = mapper.writeValueAsString(data);
        allSessions.forEach(s -> {
            try {
                s.getBasicRemote().sendText(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
