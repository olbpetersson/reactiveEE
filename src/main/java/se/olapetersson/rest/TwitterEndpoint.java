package se.olapetersson.rest;

import se.olapetersson.twitter.Tweet;
import se.olapetersson.twitter.TwitterRequester;
import se.olapetersson.websocket.TwitterSocket;
import twitter4j.Status;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.Encoded;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by ola on 2016-01-16.
 */
@Path("/twitter")
public class TwitterEndpoint {
    Logger logger = Logger.getLogger(TwitterEndpoint.class.getName());

    @Inject
    Event<Tweet> tweetEvent;

    @Inject
    TwitterSocket twitterSocket;

    @Inject
    TwitterRequester twitterRequester;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Encoded
    public List<Tweet> test(){
        return twitterRequester.getQueryPosts("#javaOne");
    }


    @Path("/home")
    @GET
    @Encoded
    public String getHomeLineTweets(){
        logger.info("Got a request for some tweets");

        return twitterRequester.getHomeTimeLine().stream().map(Status::getText).collect(Collectors.joining("|"));

    }


    @Path("/event")
    @GET
    public String fireEvent(){
        Tweet tweet = new Tweet();
        tweet.setAuthor("test");
        tweet.setMessage(System.currentTimeMillis() + " message");
        System.out.println("firing event");
        tweetEvent.fire(tweet);
        return String.valueOf(System.currentTimeMillis());
    }

    @Path("/clear")
    @GET
    public String clearTweets(){
        twitterSocket.resetTweets();
        return "Removed all tweets from WebSocket session";
    }

}
