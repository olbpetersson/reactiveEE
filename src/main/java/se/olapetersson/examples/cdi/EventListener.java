package se.olapetersson.examples.cdi;

import se.olapetersson.app.twitter.Tweet;
import se.olapetersson.app.websocket.TwitterSocket;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.logging.Logger;

public class EventListener implements Serializable{

    Logger logger = Logger.getLogger(EventListener.class.getName());


}

