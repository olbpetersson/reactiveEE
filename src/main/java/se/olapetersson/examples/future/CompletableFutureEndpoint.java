package se.olapetersson.examples.future;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

/**
 * Created by ola on 2016-01-28.
 */
@Path("future")
public class CompletableFutureEndpoint {
    Logger logger = Logger.getLogger(CompletableFutureEndpoint.class.getName());

    @GET
    @Path("/sync")
    public String syncGet() throws InterruptedException {
        String tweetFromFoo = getTweetFromFoo();
        String tweetFromBar = getTweetFromBar();
        return combineStrings(tweetFromFoo, tweetFromBar);
    }

    @Resource
    private ManagedExecutorService executorService;


    @GET
    @Path("/async")
    public String asyncGet(@Suspended AsyncResponse response) {

        return "not implemented yet :(";
    }

















    private String getTweetFromFoo() {
        /**
         * @author The Summer Intern
         *
         * @not Ola
         */
        sleepThread(500);
        return "I'm Foo";
    }

    private String getTweetFromBar() {
        /**
         * @author The Summer Intern
         *
         * @not Ola
         */
        sleepThread(500);
        return "I'm bar";
    }

    private String combineStrings(String tweetFromFoo, String tweetFromBar) {
        return tweetFromFoo + " and " +tweetFromBar;
    }

    private void sleepThread(long ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
