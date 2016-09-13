/*
package se.olapetersson.app.automagic;

import se.olapetersson.app.twitter.Tweet;
import se.olapetersson.app.websocket.TwitterSocket;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.logging.Logger;

public class Listener implements Serializable{

    Logger logger = Logger.getLogger(Listener.class.getName());

    @Inject
    TwitterSocket webSocket;

    public void onStatusEvent(@Observes Tweet tweet) {
        webSocket.handleMessage(tweet);
    }

    public void persistData(@Observes Tweet tweet) {
        logger.info("persistingData " + tweet.toString());
    }

}

*/
