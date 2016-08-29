/*
package se.olapetersson.websocket;

import javax.enterprise.context.SessionScoped;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

*/
/**
 * Created by ola on 2016-06-18.
 *//*

@ServerEndpoint("/hellowebsocket")
public class HelloWebsocket {

    public static final Set<Session> peers =
            Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void onOpen(Session session) {
        peers.add(session);
        session.getAsyncRemote().sendText("Server: you are connected");
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        peers.forEach(peer ->
        {
            if (peer != session) {
                peer.getAsyncRemote().sendText(message);
            }
        });
    }
}
*/
