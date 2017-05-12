package com.li.iphotoeditor.model;

/**
 * Created by brill on 2017/5/11.
 */
public class ShiXinYuan {
    private float r;
    private float x;
    private float y;
    private int color;

    public void setR(float r) {
        this.r = r;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getR() {

        return r;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getColor() {
        return color;
    }

    public ShiXinYuan(float r, float x, float y, int color) {

        this.r = r;
        this.x = x;
        this.y = y;
        this.color = color;
    }
}
