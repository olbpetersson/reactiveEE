package se.olapetersson.jms;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import java.util.logging.Logger;

@Stateless
public class MessageSender {

    Logger LOGGER = Logger.getLogger(MessageSender.class.getName());

    @Inject
    JMSContext context;

    @Resource(mappedName="java:jboss/exported/jms/queue/test")
    Queue queue;

    public void sendMessage(String message) {
        LOGGER.info("putting cardMessages on queue: " + message);
        context.createProducer().send(queue, message);
    }
}