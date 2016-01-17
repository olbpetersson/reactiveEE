package se.olapetersson.websocket;

import se.olapetersson.automagic.TwitterMessage;
import twitter4j.Status;
import twitter4j.Twitter;

import javax.ejb.Asynchronous;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by ola on 2016-01-16.
 */
public class Listener {

    Logger LOGGER = Logger.getLogger(Listener.class.getName());

    @Inject
    HelloWebsocket websocket;

    @Asynchronous
    public void onMessage(@Observes List<Status> messageList) {
        LOGGER.info("Observing scheduledMessage");
        LOGGER.info(messageList.toString());
        websocket.handleMessage(messageList.stream().map(s -> s.getUser().getName() + ":" + s.getText()).collect(Collectors.joining(",")), null);
    }
}