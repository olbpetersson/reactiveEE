package se.olapetersson.twitter;

import twitter4j.*;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
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
    Event<Status> statusEvent;

    private void fireStatusEvent(Status status) {
        LOGGER.info("Firing statusEvent from " +status.getUser().getScreenName());
        statusEvent.fire(status);
    }

    @PostConstruct
    public void startTweetStream(){
        TwitterStream twitterStream = new TwitterStreamFactory(TwitterConfigBuilder.getConfig()).getInstance();

        StatusListener statusListener = new StatusListener() {
            @Override
            public void onStatus(Status status) {
                LOGGER.info("Received a status from " + status.getUser().getScreenName());
                fireStatusEvent(status);
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
