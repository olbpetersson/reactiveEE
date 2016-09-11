package se.olapetersson.rest;

import se.olapetersson.twitter.CardMessage;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
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

    @Resource
    private ManagedExecutorService executorService;

    @GET
    @Path("/async")
    public void testCompleteableFuture(@Suspended AsyncResponse response) throws ExecutionException, InterruptedException {
        logger.info(executorService.toString());
        CompletableFuture<CardMessage> footure = CompletableFuture.supplyAsync(this::getTweetFromFoo, executorService);
        CompletableFuture<CardMessage> barture = CompletableFuture.supplyAsync(this::getTweetFromBar, executorService);
        footure.thenCombineAsync(barture, (x, y) -> response.resume(combineAndSendTweets(x, y)), executorService);
        logger.info("Letting go of the thread: ");
    }

    @GET
    @Path("/sync")
    public String syncGet() {
        CardMessage tweetFromFoo = getTweetFromFoo();
        CardMessage tweetFromBar = getTweetFromBar();
        return combineAndSendTweets(tweetFromFoo, tweetFromBar);
    }

    private String combineAndSendTweets(CardMessage tweetFromFoo, CardMessage tweetFromBar) {
        logCurrentThread("combine");
        return tweetFromFoo.toString() + " and " +tweetFromBar.toString();
    }

    private void logCurrentThread(String name) {
        logger.info("\nCalled from: " +name +"\nCurrent thread: : " + Thread.currentThread() + "\nCurrent group: " + Thread.currentThread().getThreadGroup() + "\nThread name: " + Thread.currentThread().getName());
    }

    private CardMessage getTweetFromFoo(){
        sleepThread(1000);
        return new CardMessage("Foo", "Hey guys, I'm foo!", null);
    }

    private CardMessage getTweetFromBar(){
        sleepThread(1000);
        return new CardMessage("Bar", "Hey guys, I'm Bar!", null);
    }

    private void sleepThread(long ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public String errorThrower(String s){
        throw new RuntimeException();
    }
}
