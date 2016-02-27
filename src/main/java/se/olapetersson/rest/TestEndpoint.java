package se.olapetersson.rest;

import se.olapetersson.automagic.Example;
import se.olapetersson.websocket.TwitterSocket;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.logging.Logger;

/**
 * Created by ola on 2016-02-10.
 */
@Stateless
@Path("/test")
public class TestEndpoint {

    Logger log = Logger.getLogger(TestEndpoint.class.getName());

    @Inject
    Event<Supplier<String>> event;

    @Inject
    Event<String> stringEvent;

    @Inject
    Event<UnaryOperator> unary;

    @Inject
    Event<Supplier<Integer>> intEvent;

    @Inject
    Example ex;

    @GET
    public void testtest(@Suspended AsyncResponse response){
        String[] strings = {"ett", "tva", "tre"};
        for (String string : strings) {
            stringEvent.fire(string +" : " +new Date().toString());
        }

        response.resume("Ok!");

    }

    @Asynchronous
    public void observer(@Observes String events) {
        log.info("Observed: " +events);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("Finished Observed: " +events);

    }

}
