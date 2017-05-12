package com.li.iphotoeditor.model;

/**
 * Created by user on 2016/10/20.
 */

public class DrawPoint {
    private float x;
    private float y;
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {

        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public DrawPoint(float x, float y){
        this.x =x;
        this.y = y;
    }
}
