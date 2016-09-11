package se.olapetersson.automagic;

import se.olapetersson.twitter.CardMessage;
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
    TwitterSocket websocket;

    @Asynchronous
    public void onStatusEvent(@Observes Status status) {
        LOGGER.info("Observed a fired status from " +status.getUser().getScreenName());
        CardMessage statusCard = new CardMessage(status);
        websocket.handleMessage(statusCard);
    }

    @Asynchronous
    public void onStatusListEvent(@Observes List<Status> messageList) {
        LOGGER.info("Observing scheduledMessage");

        websocket.handleMessage(messageList.stream().map(CardMessage::new)
                .collect(Collectors.toList()), null);
    }

}

