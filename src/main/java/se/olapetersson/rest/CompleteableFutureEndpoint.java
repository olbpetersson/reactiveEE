package se.olapetersson.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

/**
 * Created by ola on 2016-01-28.
 */
@Path("completeable")
public class CompleteableFutureEndpoint {
    Logger logger = Logger.getLogger(CompleteableFutureEndpoint.class.getName());

    public String dummyThreadSleeper(String id){
        try {
            logger.info("started " + id);
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            logger.info("finished " + id);
            return " finished " + id;
        }
    }

    @Path("/async")
    @GET
    public String completeableTest() throws InterruptedException {
        CompletableFuture<String> test = CompletableFuture.supplyAsync(() ->
                dummyThreadSleeper("first"));

        CompletableFuture<String> test2 = CompletableFuture.supplyAsync(() ->
                dummyThreadSleeper("second"));

        Thread.sleep(2000);
        logger.info("Passed the initialization of the completeable futures");

        try {
            return test.get() + test2.get();
        } catch (ExecutionException|InterruptedException e) {
            e.printStackTrace();
            return "something went bad :<";
        }

    }

    @Path("/sync")
    @GET
    public String nonCompleteableTest() throws InterruptedException {
        String first = dummyThreadSleeper("first");
        String second = dummyThreadSleeper("second");
        Thread.sleep(2000);
        logger.info("Passed the initialization of the completeable futures");
        return first + second;

    }
}
