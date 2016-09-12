package se.olapetersson.automagic;

import se.olapetersson.twitter.Tweet;
import se.olapetersson.websocket.TwitterSocket;
import twitter4j.Status;

import javax.ejb.Asynchronous;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by ola on 2016-01-16.
 */
public class Listener implements Serializable{

    Logger LOGGER = Logger.getLogger(Listener.class.getName());

    @Inject
    TwitterSocket webSocket;

    @Asynchronous
    public void onStatusEvent(@Observes Tweet tweet) {
        LOGGER.info("Observed a fired status from " + tweet.getAuthor());
        webSocket.handleMessage(tweet);
    }

    @Asynchronous
    public void onStatusListEvent(@Observes List<Status> messageList) {
        LOGGER.info("Observing scheduledMessage");

        webSocket.handleMessage(messageList.stream().map(Tweet::new)
                .collect(Collectors.toList()), null);
    }

}

