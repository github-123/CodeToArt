package com.sparken.codetoart.upcomingmovies;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by root on 20/7/17.
 */

public class MovieImagesModel implements Serializable {

    String id;
    ArrayList<Backdrops> backdropsList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Backdrops> getBackdropsList() {
        return backdropsList;
    }

    public void setBackdropsList(ArrayList<Backdrops> backdropsList) {
        this.backdropsList = backdropsList;
    }
}
