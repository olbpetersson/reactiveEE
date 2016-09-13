package se.olapetersson.app.twitter;

import twitter4j.*;
import twitter4j.conf.Configuration;

import javax.ejb.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class TwitterRequester {
    private Twitter twitter;
    public TwitterRequester(){
        Configuration config = TwitterConfigBuilder.getConfig();
        TwitterFactory factory = new TwitterFactory(config);
        twitter = factory.getInstance();

    }

    public List<Tweet> getQueryPosts(String query) {
        List<Status> queryPosts = getQueryPostsSafe(query);
        if (queryPosts.isEmpty()) {
            queryPosts = new ArrayList<>();
        }
        return queryPosts.stream()
            .map(Tweet::new)
            .collect(Collectors.toList());
    }
    private List<Status> getQueryPostsSafe(String query){
        List<Status> tweets = new ArrayList<>();
        try {
            Query tweetQuery = new Query(query);
            tweetQuery.count(9);
            tweetQuery.setSince("2016-08-28");
            QueryResult queryResult = twitter.search(tweetQuery);

            tweets.addAll(queryResult.getTweets());
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return tweets;
    }
    public ResponseList<Status> getHomeTimeLine(){
        try {
            Paging pageinator = new Paging(1);
            pageinator.setCount(50);
            return twitter.getMentionsTimeline(pageinator);

        } catch (TwitterException e) {
            e.printStackTrace();
        }

           return null;
    }
}
