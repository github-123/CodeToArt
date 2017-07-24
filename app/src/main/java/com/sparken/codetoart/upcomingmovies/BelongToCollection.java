package com.sparken.codetoart.upcomingmovies;

import java.io.Serializable;

/**
 * Created by root on 20/7/17.
 */

public class BelongToCollection implements Serializable {

    String id;
    String name;
    String poster_path;
    String backdrop_path;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }
}
