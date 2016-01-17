package se.olapetersson.twitter;

import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by ola on 2016-01-17.
 */
public class TwitterConfigBuilder {

    public static ConfigurationBuilder getConfig(){
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();

        configurationBuilder.setOAuthConsumerKey("**")
                .setOAuthConsumerSecret("**")
                .setOAuthAccessToken("**")
                .setOAuthAccessTokenSecret("**");

        return configurationBuilder;
    }
}
