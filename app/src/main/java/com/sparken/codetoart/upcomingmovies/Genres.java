package com.sparken.codetoart.upcomingmovies;

import java.io.Serializable;

/**
 * Created by root on 20/7/17.
 */

public class Genres implements Serializable {

    String id;
    String name;

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
}
