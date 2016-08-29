package se.olapetersson.automagic;

import se.olapetersson.twitter.CardMessage;
import se.olapetersson.websocket.TwitterSocket;
import twitter4j.Status;

import javax.ejb.Asynchronous;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.json.JsonArray;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by ola on 2016-01-16.
 */
public class Listener {

    @Inject
    Event<Supplier<String>> supplierEvent;

    Logger LOGGER = Logger.getLogger(Listener.class.getName());

    @Inject
    TwitterSocket websocket;

    @Asynchronous
    public void onMessage(@Observes List<Status> messageList) {
        LOGGER.info("Observing scheduledMessage");

        websocket.sendCardMessages(messageList.stream().map(CardMessage::new)
                .collect(Collectors.toList()), null);
    }

}

