package com.li.iphotoeditor.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.li.iphotoeditor.R;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void initEvent() {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.welcome);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpTo(WelcomeActivity.this, MainActivity.class);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_welcome);
    }
}
