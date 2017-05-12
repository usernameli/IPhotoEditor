package com.li.iphotoeditor.model;

import android.graphics.Path;

/**
 * Created by brill on 2017/5/11.
 */
public class BiJi {
    private Path path;
    private int stoken;
    private int color;

    public void setPath(Path path) {
        this.path = path;
    }

    public void setStoken(int stoken) {
        this.stoken = stoken;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Path getPath() {

        return path;
    }

    public int getStoken() {
        return stoken;
    }

    public int getColor() {
        return color;
    }

    public BiJi(Path path, int stoken, int color) {

        this.path = path;
        this.stoken = stoken;
        this.color = color;
    }
}
