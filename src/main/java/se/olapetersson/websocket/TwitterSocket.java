package se.olapetersson.websocket;

import se.olapetersson.twitter.CardMessage;

import javax.json.JsonArray;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@ServerEndpoint("/websocket")
public class TwitterSocket implements Serializable {

    Logger LOGGER = Logger.getLogger(TwitterSocket.class.getName());

    private static final Set<Session> peers = Collections.synchronizedSet(
            new HashSet<>());

    private static final List<CardMessage> cards = new ArrayList<>();

    @OnOpen
    public void open(Session session) {
        peers.add(session);
        CardMessage cardMessage = new CardMessage();
        cardMessage.setAuthor("THE SERVER");
        cardMessage.setMessage("Welcome! We will provide you with data " +
                "as soon as we have it available");
        List<CardMessage> cardMessages = Arrays.asList(cardMessage);
        session.getAsyncRemote().sendText(CardMessage.listToJsonArray(cardMessages).toString());

        LOGGER.info("opened session with id " + session.getId());
    }

    @OnClose
    public void close(Session session) {
        LOGGER.info("A peer has left us");
        peers.remove(session);
    }

    @OnError
    public void onError(Throwable error) {
        LOGGER.warning(error.getMessage());
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

    public void handleMessage(CardMessage message) {
        handleMessage(message, null);
    }

    public void handleMessage(CardMessage cardMessage, Session session) {
        handleMessage(Arrays.asList(cardMessage), session);
    }

    public void handleMessage(List<CardMessage> cardMessages, Session session){
        //This methods does a bit too much but is kept this way to cause a simpler api for presentations
        cards.addAll(0, cardMessages);
        handleMessage(CardMessage.listToJsonArray(cards).toString(), session);
    }

    public void resetCards() {
        cards.removeAll(cards);
    }
}
