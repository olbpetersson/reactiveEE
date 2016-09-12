package se.olapetersson.rest;

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
public class CompletableFutureEndpoint {
    Logger logger = Logger.getLogger(CompletableFutureEndpoint.class.getName());

    @Resource
    private ManagedExecutorService executorService;

    @GET
    @Path("/sync")
    public String syncGet() {
        String tweetFromFoo = getTweetFromFoo();
        String tweetFromBar = getTweetFromBar();
        return combineAndSendTweets(tweetFromFoo, tweetFromBar);
    }

    @GET
    @Path("/async")
    public void asyncGet(@Suspended AsyncResponse response) throws ExecutionException, InterruptedException {
        CompletableFuture<String> footure = CompletableFuture.supplyAsync(this::getTweetFromFoo, executorService);
        CompletableFuture<String> barture = CompletableFuture.supplyAsync(this::getTweetFromBar, executorService);
        footure.thenCombineAsync(barture, (x, y) -> response.resume(combineAndSendTweets(x, y)), executorService);
    }

    private String combineAndSendTweets(String tweetFromFoo, String tweetFromBar) {
        logCurrentThread("combine");
        return tweetFromFoo + " and " +tweetFromBar;
    }

    private void logCurrentThread(String name) {
        logger.info("\nCalled from: " +name +"\nCurrent thread: : " + Thread.currentThread() + "\nCurrent group: " + Thread.currentThread().getThreadGroup() + "\nThread name: " + Thread.currentThread().getName());
    }

    private String getTweetFromFoo(){
        /**
         * @author The Summer Intern
         *
         * @not Ola
         */
        sleepThread(500);
        return "I'm Foo";
    }

    private String getTweetFromBar(){
        /**
         * @author The Summer Intern
         *
         * @not Ola
         */
        sleepThread(500);
        return "I'm bar";
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
