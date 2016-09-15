package se.olapetersson.app.websocket;

import se.olapetersson.app.twitter.Tweet;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Logger;

@ServerEndpoint("/websocket")
public class MyWebSocket implements Serializable {

    Logger logger = Logger.getLogger(MyWebSocket.class.getName());

    private static final Set<Session> peers = Collections.synchronizedSet(
            new HashSet<>());

    private static final List<Tweet> tweets = new ArrayList<>();

    @OnOpen
    public void open(Session session) {
        peers.add(session);
        Tweet tweet = new Tweet();
        tweet.setAuthor("THE SERVER");
        tweet.setMessage("Welcome! We will provide you with data " +
                "as soon as we have it available");
        List<Tweet> tweets = Arrays.asList(tweet);
        session.getAsyncRemote().sendText(Tweet.listToJsonArray(tweets).toString());

        logger.info("opened session with id " + session.getId());
    }

    @OnClose
    public void close(Session session) {
        logger.info("A peer has left us");
        peers.remove(session);
    }

    @OnError
    public void onError(Throwable error) {
        logger.warning(error.getMessage());
        logger.info("Something went bad");
    }

    @OnMessage
    public void send(String message, Session session){
        logger.info("Received message " + message);
        peers.forEach(peer -> {
            if (session != peer) {
                peer.getAsyncRemote().sendObject(message);
            }
        });
    }

    /*
     * There are a lot of method signatures here to "hide" a bit of the clutterly stuff
     * when doing a presentation
     */
    public void send(Tweet message) {
        send(message, null);
    }

    public void send(Tweet tweet, Session session) {
        send(Arrays.asList(tweet), session);
    }

    public void send(List<Tweet> tweets, Session session){
        MyWebSocket.tweets.addAll(0, tweets);
        send(Tweet.listToJsonArray(MyWebSocket.tweets).toString(), session);
    }

    public void resetTweets() {
        tweets.removeAll(tweets);
    }

    public void send(String author, String message) {
        send(new Tweet(author, message, null));
    }

}
