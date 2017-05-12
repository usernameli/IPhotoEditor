package com.li.iphotoeditor.utils;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.shapes.Shape;
import android.os.Environment;


import com.li.iphotoeditor.model.DrawPoint;
import com.li.iphotoeditor.model.HandWriting;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by user on 2016/10/20.
 */

public class xmlUtils {

    public static int CreatXml(List<HandWriting> HardWritingList) {
        int returnValue = 0; //创建结果返回，0失败，1成功
        Document document = DocumentHelper.createDocument();//创建Document对象
        Element root = document.addElement("Painting");//创建根节点
        for (int i = 0; i < HardWritingList.size(); i++) {
            Element shape = root.addElement("PaintHandWriting");//创建Shape对象对应节点
            Element color = shape.addElement("Color");//Shape对象的color节点
            System.out.println(HardWritingList.get(i).getColor()+"ccccccccccccxxxxxxxx");
            color.setText(HardWritingList.get(i).getColor()+"");
            Element width = shape.addElement("Width");//Shape对象的width节点
            width.setText(String.valueOf(HardWritingList.get(i).getWidth()));
            Element PointList = shape.addElement("Pointlist");//创建Shape对象的PointList节点
            PointList.setText(changeListDateToStr(HardWritingList.get(i).getPointList()));
        }
        try {
            OutputFormat format = new OutputFormat("    ",true);
            format.setEncoding("UTF-8");//设置编码格式
            File extDir = Environment.getExternalStorageDirectory();
            String filenames = "main.xml";
            File fullFilename = new File(extDir, filenames);
            XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(fullFilename),format);
            xmlWriter.write(document);
            xmlWriter.close();
            returnValue = 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    private static String changeListDateToStr(List<DrawPoint> pointList) {

        StringBuffer sb = new StringBuffer();
        for (int j = 0; j < pointList.size(); j++) {
            pointList.get(j);
            sb.append(pointList.get(j).getX() + "," + pointList.get(j).getY() + ";");
        }
        //System.out.println(sb.toString());
        return sb.toString();
    }
    public static List<HandWriting> TransXml(String filename) throws DocumentException {

        List<HandWriting> lists = new ArrayList<>();
        //得到Document对象
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(filename));
        //获取文档的根节点.
        Element root = document.getRootElement();
        Iterator node =root.elementIterator();
        while(node.hasNext()){
            Element shapeElement = (Element) node.next();
           HandWriting shape = new HandWriting();

            //设置公共属性
            shape.setColor(Integer.parseInt(shapeElement.elementText("Color")));
            shape.setWidth(Float.valueOf(shapeElement.elementText("Width")));
            shape.setPointList(ChangeStrToListData(shapeElement.elementText("Pointlist")));

            lists.add(shape);
        }
        return lists;
    }
    private static List<DrawPoint> ChangeStrToListData(String text){
        List<DrawPoint> lists = new ArrayList<>();
        String[] strarray =text.split(";");
        for(int i=0;i<strarray.length;i++){
            String str =strarray[i].toString();
            String str1=str.substring(0,str.indexOf(","));
            //newpoint.setX(Float.valueOf(str1));
              String str2=str.substring(str.indexOf(",")+1);
           // newpoint.setY(Float.valueOf(str2));
            DrawPoint newpoint =new DrawPoint(Float.valueOf(str1),Float.valueOf(str2));

            lists.add(newpoint);
        }
        return lists;
    }
}
