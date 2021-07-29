package com.improgrammer.retrofitapi.modal;

public class Articles {
    private String title,description,urlToImage,url,content;
    public Articles(String title, String description, String urlToImage, String url,String content) {
        this.title = title;
        this.description = description;
        this.urlToImage = urlToImage;
        this.url = url;
        this.content=content;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlToimg() {
        return urlToImage;
    }

    public void setUrlToimg(String urlToimg) {
        this.urlToImage = urlToimg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
