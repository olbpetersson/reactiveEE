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
    TwitterRequester twitterRequester;

    //Event
    @Inject
    Event<List<Status>> event;

    //@Schedule(second="*/20", minute ="*", hour = "*", persistent = false)
    @Asynchronous
    public void fireAndForget() {
        //Get tweets
        List<Status> tweetFuture = twitterRequester
                .getQueryPosts("#JavaOne");

        //Fire the event
        event.fire(tweetFuture);

    }

}
