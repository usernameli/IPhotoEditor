package com.li.iphotoeditor.model;



import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2016/10/20.
 */

public class HandWriting {
    int color; //颜色
    float width; //宽度
    List<DrawPoint> pointList ; //笔迹上点集合
    public HandWriting() {
       pointList = new ArrayList<>();
    }

    public void addPoint(DrawPoint point) {
        pointList.add(point);
     //   setColor(DrawUtils.getInstance().getPaintColor());
       // setWidth(DrawUtils.getInstance().getStoken());
    }

    public float getWidth() {
        return width;
    }

    public void setPointList(List<DrawPoint> pointList) {
        this.pointList = pointList;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {

        return color;
    }

    public List<DrawPoint> getPointList() {
        return pointList;
    }


}
