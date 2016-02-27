package se.olapetersson.websocket;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ola on 2016-02-25.
 */
@ServerEndpoint("/hellowebsocket")
public class HelloWebsocket {

    public static final Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());
    @OnOpen
    public void onOpen(Session session) {
        peers.add(session);
        session.getAsyncRemote().sendText("You are connected!");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        session.getAsyncRemote().sendText("pong");
        peers.forEach( peer ->
        {
            if (peer != session) {
                peer.getAsyncRemote().sendText(message);
            }
        });
    }
}
