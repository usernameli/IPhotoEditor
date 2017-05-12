package com.li.iphotoeditor.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.li.iphotoeditor.R;
import com.li.iphotoeditor.model.INoteModel;
import com.li.iphotoeditor.utils.DrawUtils;
import com.li.iphotoeditor.utils.Utils;
import com.li.iphotoeditor.view.MyImageView;
import com.li.iphotoeditor.view.RichImageView;
import com.li.iphotoeditor.view.SelectColorPopup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class EditActivity extends BaseActivity implements View.OnClickListener {

    Bitmap photo;
    File cameraFile;
    Button addText;
    Button yuanXing;
    Button Line;
    Button JuXing;
    Button BI;
    Button ShiXinYuan;
    Button ShiXinJuXing;
    Button Back;
    Button Setting;
    private SelectColorPopup menuWindow;
    private Button chongzhi;
    private Button Baocun;
    public final static int CAMERA_RESULT = 1;
    private static final int CAMERA_REQUEST = 1;
    private static final int CAMERANF_REQUEST = 2;
    private static final int CROP_1_REQUEST = 3;
    private static final int CROP_2_REQUEST = 4;
    private static final int CHOOSE_PICTURE = 5;
    // private RichImageView imageView;
    private MyImageView myImageView;

    @Override
    protected void initEvent() {
        addText.setOnClickListener(this);
        yuanXing.setOnClickListener(this);
        Line.setOnClickListener(this);
        JuXing.setOnClickListener(this);
        BI.setOnClickListener(this);
        ShiXinJuXing.setOnClickListener(this);
        ShiXinYuan.setOnClickListener(this);
        Baocun.setOnClickListener(this);
        chongzhi.setOnClickListener(this);
        Back.setOnClickListener(this);
        Setting.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        getExtraData(getIntent());
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_edit);
        myImageView = (MyImageView) findViewById(R.id.imageView);
        addText = (Button) findViewById(R.id.addText);
        yuanXing = (Button) findViewById(R.id.yuanxing);
        Line = (Button) findViewById(R.id.line);
        JuXing = (Button) findViewById(R.id.juxing);
        BI = (Button) findViewById(R.id.huabi);
        ShiXinYuan = (Button) findViewById(R.id.shixinyuanxing);
        ShiXinJuXing = (Button) findViewById(R.id.shixinjuxing);
        chongzhi = (Button) findViewById(R.id.chongzhi);
        Baocun = (Button) findViewById(R.id.baocun);
        Back = (Button) findViewById(R.id.back);
        Setting = (Button) findViewById(R.id.setting);
    }

    public void getExtraData(Intent data) {
        int resultCode = data.getIntExtra("resultCode", 10086);
        int requestCode = data.getIntExtra("requestCode", 10086);

        switch (requestCode) {
            case CHOOSE_PICTURE:
                Uri uri = data.getData();
                try {
                    if (photo != null)
                        photo.recycle();
                    photo = MediaStore.Images.Media.getBitmap(getContentResolver(),
                            uri);
                    System.out.println("bitmap size from gallery = "
                            + Utils.getBitmapSize(photo));
                    myImageView.setNeedToDraw(true);
                    myImageView.setImageBitmap(photo);

                    //  Drawable drawable =new BitmapDrawable(photo);
                    //   myImageView.setBackground(drawable);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case CAMERA_RESULT:

                if (photo != null)
                    photo.recycle();
                photo =   BitmapFactory.decodeFile(DrawUtils.photopath, null);
                myImageView.setNeedToDraw(true);
                myImageView.setImageBitmap(photo);

                break;

            case CAMERANF_REQUEST:
                if (photo != null)
                    photo.recycle();
                photo = (Bitmap) data.getExtras().get("data");
                System.out.println("bitmap size from camera not file = "
                        + Utils.getBitmapSize(photo));
                //   imageView.setImageBitmap(photo);
                //   myImageView.setBackground(getResources().getDrawable(R.drawable.bianji_meitua));
                // Drawable drawables =new BitmapDrawable(photo);
                //   myImageView.setBackground(drawables);
                System.out.println("bitmap size from gallery = "
                        + Utils.getBitmapSize(photo));
                myImageView.setNeedToDraw(true);
                myImageView.setImageBitmap(photo);
                break;
            case CROP_2_REQUEST:
                if (photo != null)
                    photo.recycle();
                photo = data.getExtras().getParcelable("data");
                //  imageView.setImageBitmap(photo);
                //     myImageView.setBackground(getResources().getDrawable(R.drawable.bianji_meitua));
                //Drawable drawabless =new BitmapDrawable(photo);
                // myImageView.(drawabless);
                myImageView.setNeedToDraw(true);
                myImageView.setImageBitmap(photo);
                break;
            default:
                break;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case CROP_2_REQUEST:
                if (photo != null)
                    photo.recycle();
                photo = data.getExtras().getParcelable("data");
                //    imageView.setImageBitmap(photo);
                myImageView.setNeedToDraw(true);
                myImageView.setImageBitmap(photo);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addText:
                final EditText et = new EditText(this);
                new AlertDialog.Builder(this)
                        .setTitle("请输入")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setView(et)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DrawUtils.getInstance().setSelectStatue(INoteModel.WENZI);
                                DrawUtils.getInstance().setText(et.getText().toString());
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
            case R.id.yuanxing:
                System.out.println("yuanxing");
                DrawUtils.getInstance().setSelectStatue(INoteModel.YUANXING);
                break;
            case R.id.line:
                System.out.println("line");
                DrawUtils.getInstance().setSelectStatue(INoteModel.XIAN);
                break;
            case R.id.juxing:
                System.out.println("juxing");
                DrawUtils.getInstance().setSelectStatue(INoteModel.JUXING);
                break;
            case R.id.huabi:
                System.out.println("huabi");
                DrawUtils.getInstance().setSelectStatue(INoteModel.BI);
                break;
            case R.id.shixinjuxing:
                DrawUtils.getInstance().setSelectStatue(INoteModel.SHIXINJUXING);
                break;
            case R.id.chongzhi:
                myImageView.clearData();
                myImageView.setImageBitmap(photo);
                break;
            case R.id.baocun:
                myImageView.saveToSd();
            case R.id.back:
                myImageView.back();
                break;
            case R.id.setting:
                menuWindow = new SelectColorPopup(EditActivity.this,
                        EditActivity.this);
                // 显示窗口
                menuWindow.showAtLocation(
                        EditActivity.this.findViewById(R.id.main),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.submit:
                menuWindow.dismiss();
                DrawUtils.getInstance().setColor(menuWindow.getColor());
                DrawUtils.getInstance().setStoken(menuWindow.getStoken());
            default:
                break;
        }
    }
}
