package se.olapetersson.websocket;

import se.olapetersson.twitter.Tweet;
import twitter4j.Status;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Logger;

@ServerEndpoint("/websocket")
public class TwitterSocket implements Serializable {

    Logger logger = Logger.getLogger(TwitterSocket.class.getName());

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
    public void handleMessage(String message, Session session){
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
    public void handleMessage(Tweet message) {
        handleMessage(message, null);
    }

    public void handleMessage(Tweet tweet, Session session) {
        handleMessage(Arrays.asList(tweet), session);
    }

    public void handleMessage(List<Tweet> tweets, Session session){
        TwitterSocket.tweets.addAll(0, tweets);
        handleMessage(Tweet.listToJsonArray(TwitterSocket.tweets).toString(), session);
    }

    public void resetTweets() {
        tweets.removeAll(tweets);
    }

    public void handleMessage(String author, String message) {
        handleMessage(new Tweet(author, message, null));
    }

}
