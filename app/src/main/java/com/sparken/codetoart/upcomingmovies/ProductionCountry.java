package com.sparken.codetoart.upcomingmovies;

import java.io.Serializable;

/**
 * Created by root on 20/7/17.
 */

public class ProductionCountry implements Serializable{

    String iso_3166_1;
    String name;

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
