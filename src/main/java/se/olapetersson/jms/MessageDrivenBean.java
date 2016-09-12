package se.olapetersson.jms;

import se.olapetersson.util.LoggerUtil;
import se.olapetersson.websocket.TwitterSocket;

import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(mappedName="jms/exampleQueue")
public class MessageDrivenBean implements MessageListener {

    @Inject
    TwitterSocket webSocket;


    @Override
    public void onMessage(Message message) {
        LoggerUtil.logCurrentThread("MDB", Thread.currentThread());

        try {
            TextMessage textMessage = (TextMessage) message;
            webSocket.handleMessage("Mr. Bean", textMessage.getText());
        } catch (JMSException e) {
            webSocket.handleMessage("Mr Bean", "Something went horribly bad :(");
            e.printStackTrace();
        }

    }
}
