package com.li.iphotoeditor.utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by user on 2016/10/18.
 */
public class DrawUtils {
    private int selectStatue = 1;
    private int paintColor = Color.RED;
    private int heigth;
    private int width=20;
    private int stoken;
    private int color=Color.BLACK;
    private String Text;
    public static String photopath="";

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public int getStoken() {
        return stoken;
    }

    public void setStoken(int stoken) {
        this.stoken = stoken;
    }

    public int getHeigth() {
        return heigth;
    }

    public int getWidth() {
        return width;
    }

    public void setHeigth(int heigth) {
        this.heigth = heigth;
    }

    public int getPaintColor() {
        return paintColor;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setPaintColor(int paintColor) {
        this.paintColor = paintColor;
    }

    private static class SingletonHolder {
        private static final DrawUtils INSTANCE = new DrawUtils();
    }

    private DrawUtils() {
    }

    public static final DrawUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void setSelectStatue(int selectStatue) {
        this.selectStatue = selectStatue;
    }

    public int getSelectStatue() {
        return selectStatue;
    }

    public void compressAndSaveBitmapToSDCard(Bitmap rawBitmap, String fileName, int quality) {
        String saveFilePaht = this.getSDCardPath() + File.separator + fileName;
        File saveFile = new File(saveFilePaht);
        if (!saveFile.exists()) {
            try {
                saveFile.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
                if (fileOutputStream != null) {
                    rawBitmap.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream);
                }
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }
/*
    public InputStream getBitmapInputStreamFromSDCard(String fileName) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String filePath = this.getSDCardPath() + File.separator + fileName;
            System.out.println(filePath);
            File file = new File(filePath);
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                return fileInputStream;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return null;
    }
*/
    private String getSDCardPath() {
        String SDCardPath = null;
        // 判断SDCard是否存在
        boolean IsSDcardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (IsSDcardExist) {
            SDCardPath = Environment.getExternalStorageDirectory().toString();
        }
        return SDCardPath;
    }
}