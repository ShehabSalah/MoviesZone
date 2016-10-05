package com.platformhouse.movieszone.movieszone.data.review;

import java.io.Serializable;

/**
 * Created by Shehab Salah on 8/18/16.
 * This Class is responsible on holding one review of the one movie
 */
public class ReviewColumnHolder implements Serializable {
    private String author;
    private String content;
    private String url;

    public ReviewColumnHolder(String author, String content, String url) {
        this.author = author;
        this.content = content;
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
