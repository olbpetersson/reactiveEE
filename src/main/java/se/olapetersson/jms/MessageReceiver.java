package se.olapetersson.jms;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import java.util.logging.Logger;

/**
 * Created by ola on 2016-01-17.
 */
@Stateless
public class MessageReceiver {

    Logger LOGGER = Logger.getLogger(MessageReceiver.class.getName());

    @Inject
    private JMSContext context;

    @Resource(mappedName="java:jboss/exported/jms/queue/test")
    Queue myQueue;

    public String receiveMessage() {
        LOGGER.info("Popped message from queue");
        String message = context.createConsumer(myQueue).receiveBody(String.class, 1000);
        return "Received " + message;
    }
}
