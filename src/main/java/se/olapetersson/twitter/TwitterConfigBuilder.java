package se.olapetersson.twitter;

import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by ola on 2016-01-17.
 */
public class TwitterConfigBuilder {

    public static Configuration getConfig(){
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();

        configurationBuilder.setOAuthConsumerKey("")
                .setOAuthConsumerSecret("")
                .setOAuthAccessToken("")
                .setOAuthAccessTokenSecret("");

        return configurationBuilder.build();
    }
}
