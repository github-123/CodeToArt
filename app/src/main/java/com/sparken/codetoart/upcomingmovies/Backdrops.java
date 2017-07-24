package com.sparken.codetoart.upcomingmovies;

import java.io.Serializable;

/**
 * Created by root on 20/7/17.
 */

public class Backdrops implements Serializable {

    String aspect_ratio;
    String file_path;
    String height;
    String iso_639_1;
    String vote_average;
    String vote_count;
    String width;

    public String getAspect_ratio() {
        return aspect_ratio;
    }

    public void setAspect_ratio(String aspect_ratio) {
        this.aspect_ratio = aspect_ratio;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}
