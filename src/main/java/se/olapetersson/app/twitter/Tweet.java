package se.olapetersson.app.twitter;

import twitter4j.Status;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import java.io.Serializable;
import java.util.List;

/**
 * Created by ola on 2016-08-27.
 */
public class Tweet implements Serializable{
    String author;
    String message;
    String imageURL;

    public Tweet(){

    }

    public Tweet(Status tweet){
        this.author = tweet.getUser().getScreenName();
        this.message = tweet.getText();
        this.imageURL = tweet.getUser().getProfileImageURL();
    }

    public Tweet(String author, String message, String imageURL) {
        this.author = author;
        this.message = message;
        this.imageURL = imageURL;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public static JsonArray listToJsonArray(List<Tweet> statusList){
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        statusList.forEach(cardMessage -> arrayBuilder.add(Json.createObjectBuilder()
                .add("author", cardMessage.getAuthor() != null ? cardMessage.getAuthor() : "")
                .add("message", cardMessage.getMessage() != null ? cardMessage.getMessage() : "")
                .add("imageURL", cardMessage.getImageURL() != null ? cardMessage.getImageURL() : ""))
        );

        return arrayBuilder.build();
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "author='" + author + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
