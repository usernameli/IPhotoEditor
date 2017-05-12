package com.li.iphotoeditor.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.li.iphotoeditor.R;

public class WelcomeActivity extends BaseActivity {
    Button button;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            button.setText("跳过 :" + msg.arg1+"S");
            return true;
        }
    });

    @Override
    protected void initEvent() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpTo(WelcomeActivity.this, MainActivity.class);
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 3; i >= 0; i--) {
                    try {
                        Thread.sleep(1000);
                        Message message = Message.obtain();
                        message.arg1 = i;
                        handler.sendMessage(message);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                     jumpTo(WelcomeActivity.this, MainActivity.class);
            }
        }).start();
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_welcome);
        button = (Button) findViewById(R.id.button);

    }
}
