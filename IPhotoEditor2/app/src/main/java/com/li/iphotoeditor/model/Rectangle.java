package com.li.iphotoeditor.model;

import android.graphics.Path;

/**
 * Created by user on 2016/10/21.
 */

public class Rectangle {
    private DrawPoint LeftUp;
    private DrawPoint RightUp;
    private DrawPoint LeftDown;
    private DrawPoint RightDown;
    private int color;
    private int stoken;

    public void setColor(int color) {
        this.color = color;
    }

    public void setStoken(int stoken) {
        this.stoken = stoken;
    }

    public int getStoken() {

        return stoken;
    }

    public int getColor() {
        return color;
    }

    public Rectangle(DrawPoint LeftUp, DrawPoint RightUp, DrawPoint LeftDown, DrawPoint RightDown) {
        this.LeftDown = LeftDown;
        this.LeftUp = LeftUp;
        this.RightDown = RightDown;
        this.RightUp = RightUp;
    }

    public Path GetPath(Path path) {
        path.moveTo(LeftUp.getX(), LeftUp.getY());
        path.lineTo(LeftDown.getX(), LeftDown.getY());
        path.lineTo(RightDown.getX(), RightDown.getY());
        path.lineTo(RightUp.getX(), RightUp.getY());
        path.lineTo(LeftUp.getX(), LeftUp.getY());
        return path;
    }
}
