package com.li.iphotoeditor.model;

/**
 * Created by user on 2016/10/21.
 */

public class Circle {
    private float r;
    private float x;
    private float y;
    private int color;

    public int getColor() {
        return color;
    }
    private int stoken;

    public int getStoken() {
        return stoken;
    }

    public void setStoken(int stoken) {
        this.stoken = stoken;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setR(float r) {
        this.r = r;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getR() {

        return r;
    }

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    public Circle(float r, float x, float y, int color, int stoken) {
        this.r = r;
        this.x = x;
        this.y = y;
        this.color = color;
        this.stoken = stoken;
    }

    public Circle(float x, float y, float r) {

        this.x = x;
        this.y = y;
        this.r = r;
    }

}
