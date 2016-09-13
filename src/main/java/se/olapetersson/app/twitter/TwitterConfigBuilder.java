package se.olapetersson.app.twitter;

import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class TwitterConfigBuilder {

    private static final Logger logger = Logger.getLogger(TwitterConfigBuilder.class.getName());

    public static Configuration getConfig(){
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();

        InputStream inputStream  = TwitterConfigBuilder.class.getClassLoader().getResourceAsStream("twitter4j.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.warning("unable to read in properties for twitter auth");
            e.printStackTrace();
        }

        configurationBuilder.setOAuthConsumerKey(properties.getProperty("oauth.consumerKey"))
                .setOAuthConsumerSecret(properties.getProperty("oauth.consumerSecret"))
                .setOAuthAccessToken(properties.getProperty("oauth.accessToken"))
                .setOAuthAccessTokenSecret(properties.getProperty("oauth.accessTokenSecret"));

        return configurationBuilder.build();
    }
}
