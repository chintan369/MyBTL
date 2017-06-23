package com.agraeta.user.btl;

/**
 * Created by Nivida new on 27-Sep-16.
 */
public class Bean_Notification {

    int id=0;
    String title="";
    String message="";
    String time="";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
