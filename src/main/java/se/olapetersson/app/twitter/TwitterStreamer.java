/*
package se.olapetersson.app.twitter;

import twitter4j.*;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.enterprise.event.Event;
import javax.inject.Inject;

//@Startup
@Singleton
public class TwitterStreamer {

    Logger logger = Logger.getLogger(TwitterStreamer.class);

    @Inject
    Event<Tweet> tweetEvent;

    private void fireStatusEvent(Tweet tweet) {
        tweetEvent.fire(tweet);
    }

    @PostConstruct
    public void startTweetStreamListener(){
        TwitterStream twitterStream = new TwitterStreamFactory(TwitterConfigBuilder.getConfig()).getInstance();

        StatusListener statusListener = new StatusListener() {
            @Override
            public void onStatus(Status status) {
                logger.info("Received a status from " + status.getUser().getScreenName());
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
        logger.warn("Started to listen for " + keywords[0]);
    }


}
*/
