package se.olapetersson.app.rest;

import se.olapetersson.app.jms.MessageReceiver;
import se.olapetersson.app.jms.MessageSender;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/message")
public class CreateMessageEndpoint {

    @Inject
    MessageSender messageSender;

    @Inject
    MessageReceiver messageReceiver;

    @GET
    public String hello(){
        return messageReceiver.receiveMessage();
    }

    @POST
    public String postMessage(String msg){
        messageSender.sendMessage(msg);

        return "ok!";
    }
}
