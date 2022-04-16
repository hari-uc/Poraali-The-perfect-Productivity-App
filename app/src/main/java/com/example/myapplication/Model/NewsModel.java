package com.example.myapplication.Model;

public class NewsModel {

    private final String title;
    private final String img;
    private final String url;
//    private final String url;


    public NewsModel(String title, String img, String url) {
        this.title = title;
        this.img = img;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getImg() {
        return img;
    }

    public String getUrl() {
        return url;
    }
}
