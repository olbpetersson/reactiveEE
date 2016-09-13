package se.olapetersson.app.jms;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import java.util.logging.Logger;

@Stateless
public class MessageSender {

    Logger logger = Logger.getLogger(MessageSender.class.getName());

    @Inject
    JMSContext context;

    @Resource(mappedName="jms/exampleQueue")
    Queue queue;

    public void sendMessage(String message) {
        logger.info("putting cardMessages on queue: " + message);
        context.createProducer().send(queue, message);
    }
}
