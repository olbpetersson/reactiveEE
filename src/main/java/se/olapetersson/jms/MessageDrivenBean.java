package se.olapetersson.jms;

import se.olapetersson.twitter.CardMessage;
import se.olapetersson.websocket.TwitterSocket;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.logging.Logger;

@MessageDriven(mappedName="jms/exampleQueue")
public class MessageDrivenBean implements MessageListener {

    @Inject
    TwitterSocket websocket;

    Logger logger = Logger.getLogger(MessageDrivenBean.class.getName());

    @Override
    public void onMessage(Message message) {
        logger.info("Thread pool name: " + Thread.currentThread().getThreadGroup());
        logger.info(Thread.currentThread().getName());
        logger.info(String.valueOf(Thread.currentThread().getId()));

        CardMessage cardMessage = new CardMessage();
        cardMessage.setAuthor("Mr Bean");

        try {
            TextMessage textMessage = (TextMessage) message;
            cardMessage.setMessage(textMessage.getText());
        } catch (JMSException e) {
            cardMessage.setMessage("Noooooooo");
            e.printStackTrace();
        }
        websocket.handleMessage(cardMessage);
    }
}
