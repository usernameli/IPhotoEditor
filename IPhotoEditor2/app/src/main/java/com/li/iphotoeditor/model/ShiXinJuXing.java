package com.li.iphotoeditor.model;

/**
 * Created by brill on 2017/5/11.
 */
public class ShiXinJuXing {
    private float startX;
    private int a = 0;
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

    public ShiXinJuXing(float startX, int color, int stoken, float startY, float stopX, float stopY) {
        this.startX = startX;

        this.startY = startY;
        this.stopX = stopX;
        this.stopY = stopY;
        this.stoken = stoken;
        this.color = color;
    }
}
