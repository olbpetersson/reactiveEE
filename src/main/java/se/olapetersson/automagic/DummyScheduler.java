package se.olapetersson.automagic;

import se.olapetersson.twitter.TwitterRequester;
import twitter4j.Status;

import javax.ejb.Asynchronous;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

/**
 * Created by ola on 2016-01-16.
 */

@Singleton
@Startup
public class DummyScheduler {
    Logger LOGGER = Logger.getLogger(DummyScheduler.class.getName());

    @Inject
    Event<List<Status>> event;

    @Inject
    TwitterRequester twitterRequester;

//    @Schedule(second="*/15", minute ="*", hour = "*", persistent = false)/
    @Asynchronous
    public void fireAndForget() {
        LOGGER.info("The schedule method is doing it's magic");

        List<Status> tweetFuture = twitterRequester.getQueryPosts("#testarengrejtackhej");



        event.fire(tweetFuture);

    }

  //  @Schedule(second="*/15", minute ="*", hour = "*", persistent = false)
    @Asynchronous
    public void completeAbleFuture() {
        LOGGER.info("The schedule method is doing it's magic");
        CompletableFuture<List<Status>> tweetFuture = CompletableFuture.supplyAsync(() ->
                twitterRequester.getQueryPosts("#testarengrejtackhej"));


        try {
            event.fire(tweetFuture.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
