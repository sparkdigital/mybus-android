package com.mybus.fcm;

/**
 * Created by Julian Gonzalez <jgonzalez@devspark.com>
 */
public class NotificationData {

    public static final String TEXT = "TEXT";

    private Long id;
    private String title;
    private String textMessage;

    public NotificationData() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    public NotificationData(long id, String title, String textMessage) {
        this.id = id;
        this.title = title;
        this.textMessage = textMessage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }
}
