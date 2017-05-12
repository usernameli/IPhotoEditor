package com.li.iphotoeditor.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by brill on 2016/11/9.
 */
public abstract class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initEvent();
    }

    protected abstract void initEvent();

    protected abstract void initData();

    abstract protected void initView();

    public void showMessage(boolean isLongShow, String Message) {
        if (isLongShow)
            Toast.makeText(this, Message, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, Message, Toast.LENGTH_SHORT).show();
    }

    public void jumpTo(Context context, Class<?> activity) {
        context.startActivity(new Intent(context, activity));
    }
}
