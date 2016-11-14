package com.li.iphotoeditor.activity;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.LinearLayout;

import com.li.iphotoeditor.R;
import com.li.iphotoeditor.utils.FileUtils;

import java.io.File;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout paizhao;
    private LinearLayout bianji;

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

                Intent intents = new Intent("android.media.action.IMAGE_CAPTURE");
                intents.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(FileUtils.getInstance().getNewFile(System.currentTimeMillis() + ".jpg")));
                intents.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                startActivityForResult(intents, 10);
            case R.id.bianji:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, 11);
                break;
        }
    }
}
