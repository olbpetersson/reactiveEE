package se.olapetersson.twitter;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import javax.ejb.Singleton;
import java.util.List;

@Singleton
public class TwitterRequester {
    private Twitter twitter;
    public TwitterRequester(){
        ConfigurationBuilder configBuilder = TwitterConfigBuilder.getConfig();
        TwitterFactory factory = new TwitterFactory(configBuilder.build());
        twitter = factory.getInstance();

    }

    public List<Status> getQueryPosts(String query){
        try {
            Query tweetQuery = new Query(query);
            tweetQuery.count(10);
            tweetQuery.setSince("2015-01-01");
            QueryResult tweets = twitter.search(tweetQuery);

            return tweets.getTweets();
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return null;
    }
    public ResponseList<Status> getHomeTimeLine(){
        try {
            Paging pageinator = new Paging(1);
            pageinator.setCount(50);
            ResponseList<Status> responseList = twitter.getMentionsTimeline(pageinator);

            return responseList;
        } catch (TwitterException e) {
            e.printStackTrace();
        }

           return null;
    }
}
