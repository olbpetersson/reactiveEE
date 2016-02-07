package se.olapetersson.jms;

import se.olapetersson.websocket.TwitterSocket;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(mappedName="java:jboss/exported/jms/queue/test", activationConfig =
        {
            @ActivationConfigProperty( propertyName = "destination", propertyValue = "java:jboss/exported/jms/queue/test")
        }
)
public class MessageDrivenBean implements MessageListener {

    @Inject
    TwitterSocket websocket;

    @Override
    public void onMessage(Message message) {
       TextMessage textMessage = (TextMessage) message;
        try {
            websocket.handleMessage(textMessage.getText(), null);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
