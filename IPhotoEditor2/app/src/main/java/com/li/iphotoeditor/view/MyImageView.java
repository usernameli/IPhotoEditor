package com.li.iphotoeditor.view;

/**
 * Created by brill on 2017/5/10.
 */


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.li.iphotoeditor.model.BiJi;
import com.li.iphotoeditor.model.Circle;
import com.li.iphotoeditor.model.DrawPoint;
import com.li.iphotoeditor.model.HandWriting;
import com.li.iphotoeditor.model.INoteModel;
import com.li.iphotoeditor.model.Juxing;
import com.li.iphotoeditor.model.Line;
import com.li.iphotoeditor.model.WenZi;
import com.li.iphotoeditor.utils.DrawUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class MyImageView extends View {
    Bitmap originBitmap = null;
    boolean isNeedToDrawBitmap = false;
    private Paint paint;
    private float cx = 50;      //圆点默认X坐标
    private float cy = 50;      //圆点默认Y坐标
    private int radius = 20;
    private int screenW;
    private int screenH;
    private ArrayList arrayList = new ArrayList();
    private float startY;
    private float startX;
    private int r;
    private float stopY;
    private float stopX;
    private float clickX;
    private float clickY;
    Path mPath;
    private boolean isSave = false;
    private Bitmap bm;

    public void setNeedToDraw(boolean isNeedToDrawBitmap) {
        this.isNeedToDrawBitmap = isNeedToDrawBitmap;

    }

    private void initPaint() {
        paint = new Paint();
        //设置消除锯齿
        paint.setStyle(Paint.Style.STROKE);//设置为画线

        paint.setAntiAlias(true);//设置画笔抗锯齿
        //设置画笔颜色
        paint.setColor(DrawUtils.getInstance().getColor());
        mPath = new Path();
    }

    public MyImageView(Context context) {
        super(context);
        initPaint();
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setImageBitmap(Bitmap photo) {
        originBitmap = photo;
        screenH = photo.getHeight();
        screenW = photo.getWidth();
        invalidate();
    }

    //修正圆点坐标
    private void revise() {
        if (cx <= radius) {
            cx = radius;
        } else if (cx >= (screenW - radius)) {
            cx = screenW - radius;
        }
        if (cy <= radius) {
            cy = radius;
        } else if (cy >= (screenH - radius)) {
            cy = screenH - radius;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isSave) {
            bm = originBitmap.copy(Bitmap.Config.ARGB_8888, true);
            canvas = new Canvas(bm);
        }
        if (originBitmap != null && isNeedToDrawBitmap) {
            RectF rectF = resizeBitmap();
            canvas.drawBitmap(originBitmap, null, rectF, null);
        }
        revise();
        //绘制Text
        if (paint == null)
            initPaint();
        System.out.println(arrayList.size());
        for (int i = 0; i < arrayList.size(); i++) {
            Object oj = arrayList.get(i);
            if (oj instanceof WenZi) {
                WenZi wz = (WenZi) oj;
                System.out.println(wz.getX() + "  wwwww " + wz.getY());
                // ChangePaint(wz.getStoken(), wz.getColor());
                //  paint.setStrokeWidth(20);
                // initPaint();
                paint.setStrokeWidth(0);
                paint.setTextSize(wz.getStoken());
                paint.setColor(wz.getColor());
                canvas.drawText(wz.getText(), wz.getX(), wz.getY(), paint);
            } else if (oj instanceof Circle) {
                Circle circle = (Circle) oj;
                ChangePaint(circle.getStoken(), circle.getColor());
                canvas.drawCircle(circle.getX(), circle.getY(), circle.getR(), paint);
            } else if (oj instanceof Line) {
                Line line = (Line) oj;
                ChangePaint(line.getStoken(), line.getColor());
                canvas.drawLine(line.getStartX(), line.getStartY(), line.getStopX(), line.getStopY(), paint);
            } else if (oj instanceof Juxing) {
                Juxing juxing = (Juxing) oj;
                ChangePaint(juxing.getStoken(), juxing.getStoken());
                canvas.drawRect(juxing.getStartX(), juxing.getStartY(), juxing.getStopX(), juxing.getStopY(), paint);
            } else if (oj instanceof BiJi) {
                BiJi biJi = (BiJi) oj;
                ChangePaint(biJi.getStoken(), biJi.getColor());
                canvas.drawPath(biJi.getPath(), paint);
            }
        }
        switch (DrawUtils.getInstance().getSelectStatue()) {
            case INoteModel.WENZI:
                // ChangePaint(DrawUtils.getInstance().getStoken(),DrawUtils.getInstance().getColor());
                //initPaint();
                paint.setStrokeWidth(0);
                paint.setTextSize(DrawUtils.getInstance().getStoken());
                paint.setColor(DrawUtils.getInstance().getColor());
                canvas.drawText(DrawUtils.getInstance().getText(), cx, cy, paint);
                break;
            case INoteModel.YUANXING:
                System.out.println("circle");
                ChangePaint(DrawUtils.getInstance().getStoken(), DrawUtils.getInstance().getColor());
                canvas.drawCircle(startX, startY, r, paint);
                break;
            case INoteModel.XIAN:
                System.out.println("line xian");
                ChangePaint(DrawUtils.getInstance().getStoken(), DrawUtils.getInstance().getColor());
                canvas.drawLine(startX, startY, stopX, stopY, paint);
                break;
            case INoteModel.JUXING:
                System.out.println("jjjjxing");
                ChangePaint(DrawUtils.getInstance().getStoken(), DrawUtils.getInstance().getColor());
                canvas.drawRect(startX, startY, stopX, stopY, paint);
                break;
            case INoteModel.BI:
                System.out.println("bi");
                System.out.println("default stoken" + DrawUtils.getInstance().getStoken());
                ChangePaint(DrawUtils.getInstance().getStoken(), DrawUtils.getInstance().getColor());
                canvas.drawPath(mPath, paint);
                break;
            default:
                break;
        }
        if (isSave)
            SaveToSD(canvas, bm);
    }

    private RectF resizeBitmap() {
        int photoW = originBitmap.getWidth();
        int photoH = originBitmap.getHeight();
        int w = getWidth();
        int h = getHeight();
        int reW;
        int reH;
        float pwbiwh = photoW / photoH;
        float wbih = w / h;
        if (pwbiwh >= wbih) {
            reW = w;
            reH = reW*photoH/photoW;
        } else {
            reH = h;
            reW = (photoW * h) / photoH;
        }
        RectF rectF = new RectF(0, 0, reW, reH);
        System.out.println(photoW + "   0   " + photoH);
        System.out.println(w + "   00   " + h);
        System.out.println(reW + "   0000   " + reH);
        return rectF;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (DrawUtils.getInstance().getSelectStatue()) {
            case INoteModel.WENZI:
                EventWenZI(event);
                break;
            case INoteModel.BI:
                EventBi(event);
                break;
            case INoteModel.YUANXING:
                PaintCircle(event);
                break;
            case INoteModel.XIAN:
                EventLine(event);
                break;
            case INoteModel.JUXING:
                EventJuxing(event);
                break;

            default:
                break;
            /*
             * 备注1：此处一定要将return super.onTouchEvent(event)修改为return true，原因是：
             * 1）父类的onTouchEvent(event)方法可能没有做任何处理，但是返回了false。
             * 2)一旦返回false，在该方法中再也不会收到MotionEvent.ACTION_MOVE及MotionEvent.ACTION_UP事件。
             */
            //return super.onTouchEvent(event);

        }
        return true;
    }

    private void EventJuxing(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 按下
                startX = (int) event.getX();
                startY = (int) event.getY();
                // 通知重绘
                //  postInvalidate();   //该方法会调用onDraw方法，重新绘图
                break;
            case MotionEvent.ACTION_MOVE:
                // 移动
                stopX = (int) event.getX();
                stopY = (int) event.getY();
                // 通知重绘
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                // 抬起
                stopX = (int) event.getX();
                stopY = (int) event.getY();
                // 通知重绘
                //  postInvalidate();
                System.out.println(cx + "   " + cy);
//                WenZi wenZi = new WenZi(DrawUtils.getInstance().getStoken(), DrawUtils.getInstance().getColor(), DrawUtils.getInstance().getText(), cy, cx);
//                arrayList.add(wenZi);
//                DrawUtils.getInstance().setSelectStatue(INoteModel.defaultmode);
                Juxing juxing = new Juxing(startX, DrawUtils.getInstance().getStoken(), DrawUtils.getInstance().getColor(), startY, stopX, stopY);
                arrayList.add(juxing);
                DrawUtils.getInstance().setSelectStatue(INoteModel.defaultmode);
                break;
        }
    }

    private void EventLine(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 按下
                startX = (int) event.getX();
                startY = (int) event.getY();
                // 通知重绘
                //  postInvalidate();   //该方法会调用onDraw方法，重新绘图
                break;
            case MotionEvent.ACTION_MOVE:
                // 移动
                stopX = (int) event.getX();
                stopY = (int) event.getY();
                // 通知重绘
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                // 抬起
                stopX = (int) event.getX();
                stopY = (int) event.getY();
                // 通知重绘
                //  postInvalidate();
                System.out.println(cx + "   " + cy);
//                WenZi wenZi = new WenZi(DrawUtils.getInstance().getStoken(), DrawUtils.getInstance().getColor(), DrawUtils.getInstance().getText(), cy, cx);
//                arrayList.add(wenZi);
//                DrawUtils.getInstance().setSelectStatue(INoteModel.defaultmode);
                Line line = new Line(startX, startY, stopX, stopY, DrawUtils.getInstance().getStoken(), DrawUtils.getInstance().getColor());
                arrayList.add(line);
                DrawUtils.getInstance().setSelectStatue(INoteModel.defaultmode);
                break;
        }
    }

    private void EventBi(MotionEvent event) {
        clickX = event.getX();
        clickY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = clickX;
                startY = clickY;
                mPath = new Path();
                mPath.moveTo(clickX, clickY);
                System.out.println("down");
                break;
            case MotionEvent.ACTION_MOVE:
                //   System.out.println("move");
                //System.out.println(tempHandWriting == null);
                mPath.lineTo(clickX, clickY);
                invalidate();
                startX = clickX;
                startY = clickY;
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("leve");
                System.out.println("view" + DrawUtils.getInstance().getPaintColor());
                BiJi biJi = new BiJi(mPath, DrawUtils.getInstance().getStoken(), DrawUtils.getInstance().getColor());
                arrayList.add(biJi);
                break;
        }
    }

    private void EventWenZI(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 按下
                cx = (int) event.getX();
                cy = (int) event.getY();
                // 通知重绘
                postInvalidate();   //该方法会调用onDraw方法，重新绘图
                break;
            case MotionEvent.ACTION_MOVE:
                // 移动
                cx = (int) event.getX();
                cy = (int) event.getY();
                // 通知重绘
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                // 抬起
                cx = (int) event.getX();
                cy = (int) event.getY();
                // 通知重绘
                postInvalidate();
                System.out.println(cx + "   " + cy);
                WenZi wenZi = new WenZi(DrawUtils.getInstance().getStoken(), DrawUtils.getInstance().getColor(), DrawUtils.getInstance().getText(), cy, cx);
                arrayList.add(wenZi);
                DrawUtils.getInstance().setSelectStatue(INoteModel.defaultmode);
                break;
        }
    }

    public void ChangePaint(int stoken, int color) {
        paint.setColor(color);
        paint.setStrokeWidth(stoken);
    }

    private void PaintCircle(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 按下
                startX = event.getX();
                startY = event.getY();
                // 通知重绘
                //  postInvalidate();   //该方法会调用onDraw方法，重新绘图
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                // 移动
                float dx = Math.abs(event.getX() - startX);
                float dy = Math.abs(event.getY() - startY);
                r = (int) Math.sqrt(dx * dx + dy * dy) / 3;
                invalidate();
                // 通知重绘
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                // 抬起
                cx = (int) event.getX();
                cy = (int) event.getY();
                // 通知重绘
                Circle circle = new Circle(r, startX, startY, DrawUtils.getInstance().getColor(), DrawUtils.getInstance().getStoken());
                arrayList.add(circle);
                DrawUtils.getInstance().setSelectStatue(INoteModel.defaultmode);
                r = 0;
                break;
        }

    }

    public void clearData() {
        arrayList.clear();
        System.out.println("size " + arrayList.size());
        invalidate();
    }

    public void saveToSd() {
        isSave = true;
        invalidate();
    }

    public void SaveToSD(Canvas canvas, Bitmap bm) {
        // mCanvas.drawBitmap(writer(originalBitmap), 0, 0, null);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        File f = new File("/sdcard/" + System.currentTimeMillis() + ".png");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 50, fos);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            isSave = false;
            invalidate();
        }
    }

    public void back() {
        if (arrayList.size() != 0 && arrayList != null)
            arrayList.remove(arrayList.size() - 1);
        invalidate();
    }
}