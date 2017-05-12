package com.li.iphotoeditor.model;

/**
 * Created by brill on 2017/5/11.
 */
public class WenZi {
    private float x;
    private float y;
    private String Text;
    private int stoken;
    private int color;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getX() {
        return x;
    }

    public WenZi(float x, float y, String text, int stoken) {
        this.x = x;
        this.y = y;
        Text = text;
        this.stoken = stoken;
    }

    public WenZi(int stoken, int color, String text, float y, float x) {
        this.stoken = stoken;
        this.color = color;
        Text = text;
        this.y = y;
        this.x = x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setStoken(int stoken) {
        this.stoken = stoken;
    }

    public void setText(String text) {
        Text = text;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getY() {
        return y;
    }

    public String getText() {
        return Text;
    }

    public int getStoken() {
        return stoken;
    }
}
