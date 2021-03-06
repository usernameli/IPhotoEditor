package com.li.iphotoeditor.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by brill on 2016/11/9.
 */
public class FileUtils {
    private static FileUtils fileUtils = new FileUtils();
    private String fileName;
    private String fileDir = Environment
            .getExternalStorageDirectory() + "/IPhotoEditor";

    public static FileUtils getInstance() {
        return fileUtils;
    }

    public File getNewFile(String fileName) {
        File destDir = new File(fileDir);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        setFileName(fileName);
        return new File(fileDir, fileName);
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

}
