package se.olapetersson.rest;

import se.olapetersson.jms.MessageReceiver;
import se.olapetersson.jms.MessageSender;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

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

    @GET
    @Path("/{msg}")
    public String postMessage(@PathParam("msg") String msg){
        messageSender.sendMessage(msg);

        return "ok!";
    }
}
