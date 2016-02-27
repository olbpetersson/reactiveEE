package se.olapetersson.automagic;

import se.olapetersson.websocket.TwitterSocket;
import twitter4j.Status;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.List;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by ola on 2016-01-16.
 */
public class Listener {

    @Inject
    Event<Supplier<String>> supplierEvent;

    Logger LOGGER = Logger.getLogger(Listener.class.getName());

    @Inject
    TwitterSocket websocket;

    @Asynchronous
    public void onMessage(@Observes List<Status> messageList) {
        LOGGER.info("Observing scheduledMessage");
        websocket.handleMessage(messageList.stream()
                .map(s -> s.getUser().getName()
                    + ":" + s.getText()).collect(Collectors.joining("|")), null);
    }








































    public void testSupplier(@Observes Supplier<String> supplier){
        String s = supplier.get();
        LOGGER.info("hello");
        LOGGER.info(" this is the supplya! " + s);
    }

    public void testString(@Observes String s){
        LOGGER.info(s + " in observer");
        Supplier<String> test = () -> "yo";
        //Fungerar inte heller
        supplierEvent.fire(test);
    }

    public void testInteger(@Observes Supplier<Integer> badname) {
        LOGGER.info("in the observer of intsupplier");
        LOGGER.info(String.valueOf(badname));

    }

    public void asdf(@Observes UnaryOperator<String> unaryOperator) {
        LOGGER.info("in the asdf observes methodkjdglhrshk");
        unaryOperator.apply(this.getClass().getName() + "-- asdf");
    }


    public void secondObserver(@Observes UnaryOperator unaryOperator) {
        LOGGER.info((String) unaryOperator.apply(this.getClass().getName()));

    }
}

