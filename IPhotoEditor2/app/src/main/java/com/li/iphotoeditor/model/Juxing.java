package com.li.iphotoeditor.model;

/**
 * Created by brill on 2017/5/11.
 */
public class Juxing {
    private float startX;
    private float startY;
    private float stopX;
    private float stopY;
    private int stoken;
    private int color;

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public void setStartY(float startY) {
        this.startY = startY;
    }

    public void setStopX(float stopX) {
        this.stopX = stopX;
    }

    public void setStopY(float stopY) {
        this.stopY = stopY;
    }

    public void setStoken(int stoken) {
        this.stoken = stoken;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getStartX() {

        return startX;
    }

    public float getStartY() {
        return startY;
    }

    public float getStopX() {
        return stopX;
    }

    public float getStopY() {
        return stopY;
    }

    public int getStoken() {
        return stoken;
    }

    public int getColor() {
        return color;
    }

    public Juxing(float startX, int color, int stoken, float startY, float stopX, float stopY) {

        this.startX = startX;
        this.color = color;
        this.stoken = stoken;
        this.startY = startY;
        this.stopX = stopX;
        this.stopY = stopY;
    }
}
