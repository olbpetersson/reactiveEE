package se.olapetersson.twitter;

import twitter4j.*;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 * Created by ola on 2016-08-31.
 */
//@Startup
@Singleton
public class TwitterStreamer {

    Logger LOGGER = Logger.getLogger(TwitterStreamer.class);

    @Inject
    Event<Tweet> statusEvent;

    private void fireStatusEvent(Tweet tweet) {
        LOGGER.info("Firing statusEvent from " + tweet.getAuthor());
        statusEvent.fire(tweet);
    }

    /**
     * This is where the automagic happens
     *  We are listening on tweets through the streaming api, whenever we get a tweet
     *  @fireStatusEvent is called
     */
    @PostConstruct
    public void startTweetStream(){
        TwitterStream twitterStream = new TwitterStreamFactory(TwitterConfigBuilder.getConfig()).getInstance();

        StatusListener statusListener = new StatusListener() {
            @Override
            public void onStatus(Status status) {
                LOGGER.info("Received a status from " + status.getUser().getScreenName());
                Tweet tweet = new Tweet(status);
                fireStatusEvent(tweet);
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

            }

            @Override
            public void onTrackLimitationNotice(int i) {

            }

            @Override
            public void onScrubGeo(long l, long l1) {

            }

            @Override
            public void onStallWarning(StallWarning stallWarning) {

            }

            @Override
            public void onException(Exception e) {
                e.printStackTrace();
            }
        };

        FilterQuery filter = new FilterQuery();
        String[] keywords = {"#testtestWOW"};
        filter.track(keywords);

        twitterStream.addListener(statusListener);
        twitterStream.filter(filter);
        LOGGER.warn("Started to listen for " + keywords[0]);
    }


}
