package se.olapetersson.rest;

import se.olapetersson.websocket.TwitterSocket;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

/**
 * Created by ola on 2016-01-28.
 */
@Path("future")
public class CompleteableFutureEndpoint {
    Logger logger = Logger.getLogger(CompleteableFutureEndpoint.class.getName());

    @Inject
    TwitterSocket twitterSocket;

    @RequestScoped
    StringBuilder msg = new StringBuilder();

    @GET
    public void testCompleteableFuture(@Suspended AsyncResponse response) throws ExecutionException, InterruptedException {

        CompletableFuture seperateThread = CompletableFuture.supplyAsync(this::completeableTest);
        nonCompleteableTest();

        response.resume(seperateThread.getNow("tofast"));
    }

    public String completeableTest()  {

        CompletableFuture<String> test = CompletableFuture.supplyAsync(() ->
                    dummyThreadSleeper("first"));

        CompletableFuture<String> test2 = CompletableFuture.supplyAsync(() ->
                    dummyThreadSleeper("second"));

        try {
            msg.append("Async: "+ test.get() + test2.get() + "|");
        } catch (ExecutionException|InterruptedException e) {
            e.printStackTrace();
            msg.append("ouch: something went bad|");
        }

        twitterSocket.handleMessage(msg.toString(), null);
        return msg.toString();
    }

    public String nonCompleteableTest() {
        String first = dummyThreadSleeper("first");
        String second = dummyThreadSleeper("second");

        msg.append("Sync: " + first + second + "|");
        twitterSocket.handleMessage(msg.toString(), null);
        return msg.toString();

    }

    public String errorThrower(String s){
        throw new RuntimeException();
    }
    public String dummyThreadSleeper(String id){
        try {
            logger.info("started " + id);
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            logger.info(" finished " + id);
            return " finished " + id;

        }

    }
}
