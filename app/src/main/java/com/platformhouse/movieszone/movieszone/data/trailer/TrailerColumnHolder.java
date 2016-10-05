package com.platformhouse.movieszone.movieszone.data.trailer;

import java.io.Serializable;

/**
 * Created by Shehab Salah on 8/18/16.
 * This Class is responsible on holding one Trailer of the one movie
 */
public class TrailerColumnHolder implements Serializable {
    private String key;
    private String name;
    private String size;
    private String type;

    public TrailerColumnHolder(String key, String name, String size, String type) {
        this.key = key;
        this.name = name;
        this.size = size;
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
