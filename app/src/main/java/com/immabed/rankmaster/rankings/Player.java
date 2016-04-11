package com.immabed.rankmaster.rankings;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by Brady Coles on 2016-03-26.
 */
public class Player implements Serializable{
    private String id;
    private String name;

    public Player(String name) {
        Random seed = new Random();
        id = Long.toString(seed.nextLong(), 36);
        this.name = name;
    }

    public String getId () {
        return id;
    }

    public String toString() {
        return name;
    }

    public void changeName(String name) {
        this.name = name;
    }
}
