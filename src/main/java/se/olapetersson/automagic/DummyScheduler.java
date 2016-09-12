package se.olapetersson.automagic;

import se.olapetersson.twitter.Tweet;
import se.olapetersson.twitter.TwitterRequester;

import javax.ejb.Asynchronous;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by ola on 2016-01-16.
 */

@Singleton
@Startup
public class DummyScheduler {

    @Inject
    TwitterRequester twitterRequester;

    //Event
    @Inject
    Event<List<Tweet>> event;

    //@Schedule(second="*/20", minute ="*", hour = "*", persistent = false)
    @Asynchronous
    public void fireAndForget() {
        //Get tweets
        List<Tweet> tweetFuture = twitterRequester
                .getQueryPosts("#JavaOne");

        //Fire the event
        event.fire(tweetFuture);

    }

}
