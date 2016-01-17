package se.olapetersson.automagic;

import se.olapetersson.twitter.TwitterRequester;
import twitter4j.ResponseList;
import twitter4j.Status;

import javax.ejb.Asynchronous;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
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

    @Schedule( minute = "*/1", hour = "*", persistent = false)
    @Asynchronous
    public void dummy() {
        LOGGER.info("The schedule method is doing it's magic");
        List<Status> javaForumTweets = twitterRequester.getJavaForumPosts();
        /*List<TwitterMessage> twitterMessageList = new ArrayList<>();
        if(homeLine != null) {
            homeLine.forEach(msg -> twitterMessageList.add(new TwitterMessage(msg.getUser().getName() + " : " + msg.getText())));
        } else {
            LOGGER.info("Homeline was 0 :<");
        }*/
        event.fire(javaForumTweets);
    }
}
