package com.example.mobileguicapstone;

//saves the image data into a single object to be put into the DB
public class ImageResponse {
    String date;
    String explanation;
    String title;
    String hdurl;
    String url;
    String path;

    public ImageResponse() {
    }

    public ImageResponse(String date, String explanation, String title, String hdurl, String url) {
        this.date = date;
        this.explanation = explanation;
        this.title = title;
        this.hdurl = hdurl;
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHdurl() {
        return hdurl;
    }

    public void setHdurl(String hdurl) {
        this.hdurl = hdurl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

