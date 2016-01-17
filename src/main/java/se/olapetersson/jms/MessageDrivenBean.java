package se.olapetersson.jms;

import se.olapetersson.websocket.HelloWebsocket;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(mappedName="java:jboss/exported/jms/queue/test", activationConfig =
        {
            @ActivationConfigProperty( propertyName = "destination", propertyValue = "java:jboss/exported/jms/queue/test")
        }
)
public class MessageDrivenBean implements MessageListener {

    @Inject
    HelloWebsocket websocket;

    @Override
    public void onMessage(Message message) {
        websocket.handleMessage("This message was delivered to you from an MDB", null);
    }
}
