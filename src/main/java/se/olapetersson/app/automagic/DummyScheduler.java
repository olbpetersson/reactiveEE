package se.olapetersson.app.automagic;

import se.olapetersson.app.twitter.Tweet;
import se.olapetersson.app.twitter.TwitterRequester;

import javax.ejb.Asynchronous;
import javax.ejb.Singleton;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.List;

//@Startup
@Singleton
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
                .getQueryPosts("#JFokus");

        //Fire the event
        event.fire(tweetFuture);

    }

}
