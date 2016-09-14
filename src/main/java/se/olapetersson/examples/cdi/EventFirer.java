package se.olapetersson.examples.cdi;

import se.olapetersson.app.twitter.Tweet;
import se.olapetersson.app.twitter.TwitterConfigBuilder;
import twitter4j.*;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

//@Startup
@Singleton
public class EventFirer {

    Logger logger = Logger.getLogger(EventFirer.class);

    private void fireStatusEvent(Tweet tweet) {
        /** Too future Ola:
        * Don't forget to startUp this bean
        * */
    }

    /**
     *  Whenever we get notified about a tweet we call fireStatusEvent(tweet)
     *  above ^
     */
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
        String[] keywords = {"#JavaOne"};
        filter.track(keywords);

        twitterStream.addListener(statusListener);
        twitterStream.filter(filter);
        logger.warn("Started to listen for " + keywords[0]);
    }


}
