package se.olapetersson.rest;

import se.olapetersson.twitter.CardMessage;
import se.olapetersson.twitter.TwitterRequester;
import twitter4j.Status;

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
    TwitterRequester twitterRequester;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Encoded
    public List<CardMessage> test(){
        return twitterRequester.getQueryPosts("#java").stream()
                .map(CardMessage::new)
                .collect(Collectors.toList());
    }


    @Path("/home")
    @GET
    @Encoded
    public String getHomeLineTweets(){
        logger.info("Got a request for some tweets");

        return twitterRequester.getHomeTimeLine().stream().map(Status::getText).collect(Collectors.joining("|"));

    }

}
