package com.li.iphotoeditor.activity;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.LinearLayout;

import com.li.iphotoeditor.R;
import com.li.iphotoeditor.utils.DrawUtils;
import com.li.iphotoeditor.utils.FileUtils;
import com.li.iphotoeditor.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout paizhao;
    private LinearLayout bianji;
    Bitmap photo;
    File cameraFile;
    private File mPhotoFile;
    private String mPhotoPath;
    public final static int CAMERA_RESULT = 1;
    private static final int CAMERA_REQUEST = 1;
    private static final int CAMERANF_REQUEST = 2;
    private static final int CROP_1_REQUEST = 3;
    private static final int CROP_2_REQUEST = 4;
    private static final int CHOOSE_PICTURE = 5;

    @Override
    protected void initEvent() {
        paizhao.setOnClickListener(this);
        bianji.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        paizhao = (LinearLayout) findViewById(R.id.paizhao);
        bianji = (LinearLayout) findViewById(R.id.bianji);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.paizhao:
                try {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");//开始拍照
                    mPhotoPath = getSDPath() + "/" + getPhotoFileName();//设置图片文件路径，getSDPath()和getPhotoFileName()具体实现在下面

                    mPhotoFile = new File(mPhotoPath);
                    if (!mPhotoFile.exists()) {
                        mPhotoFile.createNewFile();//创建新文件
                    }
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,//Intent有了图片的信息
                            Uri.fromFile(mPhotoFile));
                    startActivityForResult(intent, CAMERA_RESULT);//跳转界面传回拍照所得数据
                } catch (Exception e) {
                }
                break;
            case R.id.bianji:
            /*    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, 11);
                break;*/
                Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                openAlbumIntent.setType("image/*");
                startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                break;

            default:
                break;
        }
    }

    public String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();

    }


    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (requestCode == CAMERA_RESULT) {
            Intent intent = new Intent();
            intent.putExtra("resultCode", CAMERA_RESULT);
            intent.putExtra("requestCode", CAMERA_RESULT);
            intent.setClass(MainActivity.this, EditActivity.class);
            DrawUtils.photopath = mPhotoPath;
            startActivity(intent);
        } else {
            System.out.println(requestCode + "   " + resultCode);
            data.putExtra("resultCode", resultCode);
            data.putExtra("requestCode", requestCode);
            data.setClass(MainActivity.this, EditActivity.class);
            startActivity(data);
        }
    }
}
