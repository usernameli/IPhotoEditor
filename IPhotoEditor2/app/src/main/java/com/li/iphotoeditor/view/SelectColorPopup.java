package com.li.iphotoeditor.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.ColorPicker.OnColorChangedListener;
import com.larswerkman.holocolorpicker.ColorPicker.OnColorSelectedListener;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SVBar;
import com.larswerkman.holocolorpicker.SaturationBar;
import com.larswerkman.holocolorpicker.ValueBar;
import com.li.iphotoeditor.R;

public class SelectColorPopup extends PopupWindow implements OnColorChangedListener, OnColorSelectedListener {


    private View mMenuView;
    private ColorPicker picker;
    private int stoken;

    public int getStoken() {
        return stoken;
    }

    public int getColor() {
        return picker.getColor();
    }

    public SelectColorPopup(Activity context, OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.select_color, null);
        picker = (ColorPicker) mMenuView.findViewById(R.id.picker);
        SVBar svBar = (SVBar) mMenuView.findViewById(R.id.svbar);
        OpacityBar opacityBar = (OpacityBar) mMenuView.findViewById(R.id.opacitybar);
        SaturationBar saturationBar = (SaturationBar) mMenuView.findViewById(R.id.saturationbar);
        ValueBar valueBar = (ValueBar) mMenuView.findViewById(R.id.valuebar);
        Button submit = (Button) mMenuView.findViewById(R.id.submit);
        submit.setOnClickListener(itemsOnClick);
        final TextView textView = (TextView) mMenuView.findViewById(R.id.popstokentext);
        SeekBar seekBar = (SeekBar) mMenuView.findViewById(R.id.popseekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //  textView.setText("拖动停止");
            }

            /**
             * 拖动条开始拖动的时候调用
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //   textView.setText("开始拖动");
            }

            /**
             * 拖动条进度改变的时候调用
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                textView.setText("当前大小" + progress);
                stoken = progress;
                //  textView.setTextSize(progress / 3);
            }
        });
        picker.addSVBar(svBar);
        picker.addOpacityBar(opacityBar);
        picker.addSaturationBar(saturationBar);
        picker.addValueBar(valueBar);

        //To get the color
        picker.getColor();

        //To set the old selected color u can do it like this
        picker.setOldCenterColor(picker.getColor());
        // adds listener to the colorpicker which is implemented
        //in the activity
        picker.setOnColorChangedListener(this);
        picker.setOnColorSelectedListener(this);
        this.setContentView(mMenuView);
        this.setWidth(LayoutParams.FILL_PARENT);
        this.setHeight(LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);
        mMenuView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

    }

    @Override
    public void onColorChanged(int color) {
    }

    @Override
    public void onColorSelected(int color) {
    }

}