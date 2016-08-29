package se.olapetersson.jms;

import se.olapetersson.twitter.CardMessage;
import se.olapetersson.websocket.TwitterSocket;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.ArrayList;
import java.util.List;

@MessageDriven(mappedName="MessageDrivenBean",
    activationConfig =
    {
@ActivationConfigProperty(
        propertyName = "destination",
        propertyValue = "java:jboss/exported/jms/queue/test")
    }
)
public class MessageDrivenBean implements MessageListener {

    @SessionScoped
    List<CardMessage> cardMessages = new ArrayList<>();

    @Inject
    TwitterSocket websocket;

    @Override
    public void onMessage(Message message) {
        try {
            TextMessage textMessage = (TextMessage) message;

            cardMessages.add(0, new CardMessage("Mr Bean", textMessage.getText(), null));
        } catch (JMSException e) {
            cardMessages.add(new CardMessage("Mr Bean", "Noooooooo", null));
            e.printStackTrace();
        }
        websocket.sendCardMessages(cardMessages, null);
    }
}
