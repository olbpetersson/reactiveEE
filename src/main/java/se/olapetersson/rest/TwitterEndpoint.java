package se.olapetersson.rest;

import se.olapetersson.twitter.TwitterRequester;
import twitter4j.Status;

import javax.inject.Inject;
import javax.ws.rs.Encoded;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by ola on 2016-01-16.
 */
@Path("/twitter")
public class TwitterEndpoint {
    Logger logger = Logger.getLogger(TwitterEndpoint.class.getName());

    @Inject
    TwitterRequester twitterRequester;

    @GET
    public String getJavaforumTweets(){
        logger.info("Got a request for some tweets");

        return twitterRequester.getQueryPosts("#javaforum").stream().
                map(s -> s.getUser().getName() + ":" + s.getText()).collect(Collectors.joining("|"));

    }


    @Path("/home")
    @GET
    @Encoded
    public String getHomeLineTweets(){
        logger.info("Got a request for some tweets");

        return twitterRequester.getHomeTimeLine().stream().map(Status::getText).collect(Collectors.joining("|"));

    }

}
