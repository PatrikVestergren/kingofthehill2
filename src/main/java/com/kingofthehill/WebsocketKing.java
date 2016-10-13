package com.kingofthehill;

import javax.websocket.EncodeException;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint("/king")
public class WebsocketKing {

    private static Set<Session> allSessions = new HashSet<>();

    @OnOpen
    public void init(Session session) {
        allSessions = session.getOpenSessions();
    }

    public static void sendMessage(String data) {
        allSessions.forEach(s -> {
            try {
                System.out.println("gonna send message");
                s.getBasicRemote().sendObject(data);
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        });
    }
}
