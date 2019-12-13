package com.mangrovehotel.com;

public class PostHolder {

    String setTmSet;
    String current_date;
    String current_time;
    String image;
    String message;

    public PostHolder() {

    }

    public PostHolder(String current_date, String current_time, String image, String message) {
        this.current_date = current_date;
        this.current_time = current_time;
        this.image = image;
        this.message = message;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }

    public String getCurrent_time() {
        return current_time;
    }

    public void setCurrent_time(String current_time) {
        this.current_time = current_time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
