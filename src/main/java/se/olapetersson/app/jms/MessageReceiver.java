package se.olapetersson.app.jms;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;

@Stateless
public class MessageReceiver {

    @Inject
    private JMSContext context;

    @Resource(mappedName="jms/exampleQueue")
    Queue myQueue;

    public String receiveMessage() {
        String message = context.createConsumer(myQueue).receiveBody(String.class, 1000);
        return "Received " + message;
    }
}
