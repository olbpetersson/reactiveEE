package se.olapetersson.automagic;

/**
 * Created by ola on 2016-01-16.
 */
public class TwitterMessage {

    public String getMessage() {
        return message;
    }

    public TwitterMessage(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;
}
