package se.olapetersson.twitter;

import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by ola on 2016-01-17.
 */
public class TwitterConfigBuilder {

    public static Configuration getConfig(){
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();

        configurationBuilder.setOAuthConsumerKey("78iHBr1DjIpGflTXkkitw31px")
                .setOAuthConsumerSecret("RwkFJxQKlyilZGgdSNHyxNhCUxfDAyLtzoNPNsrTSAWGUAlXnG")
                .setOAuthAccessToken("31111573-9Sd3FAbL0qSs7RlqsVZPrZ70aVOv0O8Yz1hnJKUcc")
                .setOAuthAccessTokenSecret("x5fKNQxeXIgvZIfGfkhNEAC66uQDm0UekS6GsmwOmZSjJ");

        return configurationBuilder.build();
    }
}
