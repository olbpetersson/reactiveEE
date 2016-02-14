package se.olapetersson.jms;

import se.olapetersson.websocket.TwitterSocket;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(mappedName="java:jboss/exported/jms/queue/test",
        activationConfig =
        {
            @ActivationConfigProperty( propertyName = "destination",
                    propertyValue = "java:jboss/exported/jms/queue/test")
        }
)
public class MessageDrivenBean implements MessageListener {

    @SessionScoped
    StringBuilder msg = new StringBuilder();

    @Inject
    TwitterSocket websocket;

    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;
            msg.append("From MDB: " + textMessage.getText() + "|");
        } catch (JMSException e) {
            msg.append("From MDB: error|");
            e.printStackTrace();
        }
        websocket.handleMessage(msg.toString(), null);
    }
}
