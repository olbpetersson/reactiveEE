package se.olapetersson.websocket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@ServerEndpoint("/websocket")
public class TwitterSocket {
    Logger LOGGER = Logger.getLogger(TwitterSocket.class.getName());
    private static final Set<Session> peers = Collections.synchronizedSet(
            new HashSet<>());
    @OnOpen
    public void open(Session session) {
        peers.add(session);
        LOGGER.info("opened session with id " + session.getId());
    }

    @OnClose
    public void close(Session session) {
        LOGGER.info("A peer has left us");
        peers.remove(session);
    }

    @OnError
    public void onError(Throwable error) {
        LOGGER.info("Something went bad");
    }

    @OnMessage
    public void handleMessage(String message, Session session){
        LOGGER.info("Received message " + message);
        peers.forEach(peer -> {
            if (session != peer) {
                peer.getAsyncRemote().sendObject(message);
            }
        });
    }


}

