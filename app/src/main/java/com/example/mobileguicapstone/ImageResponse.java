package com.example.mobileguicapstone;

//saves the image data into a single object to be put into the DB
public class ImageResponse {
    String date;
    String description;
    String title;
    String hdurl;
    String url;
    String path;
    int id;

    public ImageResponse() {
    }

    public ImageResponse(String date, String description, String title, String hdurl, String url, int id) {
        this.date = date;
        this.description = description;
        this.title = title;
        this.hdurl = hdurl;
        this.url = url;
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

