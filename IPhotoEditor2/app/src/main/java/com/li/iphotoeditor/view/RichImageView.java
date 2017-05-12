package com.li.iphotoeditor.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import com.li.iphotoeditor.activity.BaseActivity;
import com.li.iphotoeditor.model.Circle;
import com.li.iphotoeditor.model.DrawPoint;
import com.li.iphotoeditor.model.HandWriting;
import com.li.iphotoeditor.model.INoteModel;
import com.li.iphotoeditor.model.Rectangle;
import com.li.iphotoeditor.utils.DrawUtils;
import com.li.iphotoeditor.utils.xmlUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class RichImageView extends View {
    private Paint paint = null; //
    private Bitmap originalBitmap = null;//原始图
    // private Bitmap new1Bitmap = null;
    private Stack<Path> doStatck;//操作栈
    private List<HandWriting> tempHandWritingList;
    private List<Circle> tempCircleList;
    private List<Rectangle> tempRectangles;
    private boolean isSave = false;
    private float tempX;
    Bitmap bm;
    private float tempY;
    private Canvas mCanvas;
    private float clickX = 0;
    private float clickY = 0;
    private float startX = 0;
    private Rectangle rectangle;
    HandWriting tempHandWriting = null;
    private float startY = 0;
    private INoteModel Realxiangpi;
    // private boolean isXiangpi = false;
    private boolean isClear = false;
    private int color = Color.RED;//默认画笔颜色
    private float strokeWidth = 20f;//默认画笔宽度
    Path mPath;
    private Path RicPath;
    // private List<DrawPoint> listPoint;
    private List<HandWriting> HandWritingList;
    private Context context;
    private int r;
    private List pathList;
    Path tempPath;
    private HandWriting handWriting;//笔迹

    public RichImageView(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    public RichImageView(Context context, AttributeSet atts) {
        this(context, atts, 0);
        // TODO Auto-generated constructor stub
    }

    @SuppressWarnings("static-access")
    public RichImageView(Context context, AttributeSet atts, int defStyle) {
        super(context, atts, defStyle);
        // TODO Auto-generated constructor stub
        this.context = context;
        HandWritingList = new ArrayList<>();
        tempCircleList = new ArrayList<>();
        tempRectangles = new ArrayList<>();
        File extDir = Environment.getExternalStorageDirectory();
        String filenames = "main.xml";
        File file = new File(extDir, filenames);
        if (file.exists()) {
            try {
                tempHandWritingList = xmlUtils.TransXml(file.getAbsolutePath());
                HandWritingList.addAll(tempHandWritingList);
            } catch (Exception e) {
                e.printStackTrace();
                tempHandWritingList = new ArrayList<>();
            }
        } else {
            tempHandWritingList = new ArrayList<>();
        }


        pathList = new ArrayList<>();
        //listPoint = new ArrayList<>();
        //     ColorDrawable drawable = new ColorDrawable(Color.BLACK);
        originalBitmap = Bitmap.createBitmap(DrawUtils.getInstance().getWidth(), DrawUtils.getInstance().getHeigth(), Bitmap.Config.ARGB_8888);
        //  Canvas canvas = new Canvas(originalBitmap);
        //     drawable.draw(canvas);
        initPaint();
        //doStatck = new Stack<>();
        //  originalBitmap = BitmapFactory.decodeResource(899,600,getResources(), R.drawable.default_bgs).copy(Bitmap.Config.ARGB_8888, true);//白色的画板
        //   originalBitmap = bitmaps;
        // new1Bitmap = originalBitmap.createBitmap(originalBitmap);
        mPath = new Path();
        tempPath = new Path();
        RicPath = new Path();
    }


    public void setXiangpi(INoteModel xiangpi) {
        this.Realxiangpi = xiangpi;
    }

    //清楚
    @SuppressWarnings("static-access")
    public void clear() {
        isClear = true;
        System.out.println("clear");
        //  if (doStatck.isEmpty()) {
        //     ((BaseActivity) context).showMessage(false, "不能再后退了呢");
        //    } else {
        //    InputStream inputStream = DrawUtils.getInstance().getBitmapInputStreamFromSDCard(doStatck.pop());
        //    Bitmap rawBitmap2 = BitmapFactory.decodeStream(inputStream).copy(Bitmap.Config.ARGB_8888, true);
        //    new1Bitmap = originalBitmap.createBitmap(rawBitmap2);
        if (tempHandWritingList.size() != 0)
            tempHandWritingList.remove(tempHandWritingList.size() - 1);
        else {
            ((BaseActivity) context).showMessage(false, "不能再后退了");
        }
        invalidate();//重置
        //    }
    }

    public void setStrokeWidth(float width) {
        this.strokeWidth = width;
        // initPaint();
        paint.setStrokeWidth(width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        //  super.onDraw(canvas);
        // DrawOrbit(canvas);
        //    this.mCanvas = canvas;
        if (isSave) {
            bm = Bitmap.createBitmap(DrawUtils.getInstance().getWidth(), DrawUtils.getInstance().getHeigth(), Bitmap.Config.ARGB_8888);
            canvas = new Canvas(bm);
        }
        System.out.println(tempHandWritingList.size() + "size");
        try {
            for (HandWriting handWriting : tempHandWritingList) {
                paint.setColor(handWriting.getColor());
                paint.setStrokeWidth(handWriting.getWidth());
                //  tempPath.reset();
                if (handWriting.getPointList().size() != 0) {
                    DrawPoint drawPoints = handWriting.getPointList().get(0);
                    tempPath.moveTo(drawPoints.getX(), drawPoints.getY());
                    for (DrawPoint drawPoint : handWriting.getPointList()) {
                        tempPath.lineTo(drawPoint.getX(), drawPoint.getY());
                    }
                    canvas.drawPath(tempPath, paint);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tempPath.reset();
        }
        paint.setColor(DrawUtils.getInstance().getPaintColor());
        paint.setStrokeWidth(DrawUtils.getInstance().getStoken());
        if (true)
            for (Circle circle : tempCircleList) {
                paint.setColor(circle.getColor());
                paint.setStrokeWidth(circle.getStoken());
                canvas.drawCircle(circle.getX(), circle.getY(), circle.getR(), paint);
            }
        //   } else {
        //       canvas.drawBitmap(writer(originalBitmap), 0, 0, null);
        //   }
        if (DrawUtils.getInstance().getSelectStatue() == INoteModel.JUXING)
            for (Rectangle circle : tempRectangles) {
                paint.setColor(circle.getColor());
                paint.setStrokeWidth(circle.getStoken());
                //  canvas.drawCircle(circle.getX(), circle.getY(), circle.getR(), paint);
                canvas.drawPath(circle.GetPath(mPath), paint);
            }
        // canvas.drawCircle(S, cy, radius, paint);
        //修正圆点坐标
        //  revise();
        //随机设置画笔颜色
        // setPaintRandomColor();
        //   canvas.drawCircle(cX,cY,10,paint);
        // for (int i=0;i<listPoint.size()-1;i++) {
        //       tempPath.reset();
        // tempPath.quadTo(listPoint.get(i).getX(), listPoint.get(i).getY(), listPoint.get(i + 1).getX(), listPoint.get(i + 1).getY());
        // canvas.save();
        //canvas.clipRect(tempX-5, tempX-5,tempX+5, tempY + 5);
        //  canvas.drawBitmap(bitmap, x2, y2, paint);

        //canvas.drawCircle(tempX,tempY,5,paint);
        //canvas.restore();
        //  }  // canvas.drawCircle(listPoint.get(i).getX(),listPoint.get(i).getY(),10,paint);
        if (!isClear)
            canvas.drawPath(mPath, paint);
        //mPath.reset();
        //  canvas.save();
        if (isSave)
            SaveToSD(canvas, bm);
    }

//    private void DrawOrbit(Canvas canvas) {
//        for (DrawPoint point : listPoint) {
//            canvas.drawCircle(point.getX(), point.getY(), 10, paint);
//        }
//
//    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        isClear = false;
        switch (DrawUtils.getInstance().getSelectStatue()) {
            case INoteModel.BI:
//                setColor(DrawUtils.getInstance().getPaintColor());
                PaintPen(event);
                break;
            case INoteModel.XIANGPI:
                setColor(Color.WHITE);
                DrawUtils.getInstance().setPaintColor(Color.WHITE);
                DrawUtils.getInstance().setStoken(20);
                setStrokeWidth(DrawUtils.getInstance().getStoken());
                PaintPen(event);
                break;
            case INoteModel.XIAN:
                setColor(DrawUtils.getInstance().getPaintColor());
                PaintLine(event);
                break;
            case INoteModel.YUANXING:
                setColor(DrawUtils.getInstance().getPaintColor());
                PaintCircle(event);
                break;
            case INoteModel.JUXING:
                setColor(DrawUtils.getInstance().getPaintColor());
                PaintJuxing(event);
                break;
        }

        return true;
    }

    private void PaintJuxing(MotionEvent event) {
        clickX = event.getX();
        clickY = event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startX = clickX;
            startY = clickY;
            // mPath.reset();
            mPath.moveTo(clickX, clickY);


        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            //  float dx = Math.abs(clickX - startX);
            //   float dy = Math.abs(clickY - startY);
            float dx = event.getX();
            float dy = event.getY();
//   if(dx>=3||dy>=3){
            //设置贝塞尔曲线的操作点为起点和终点的一半
            // float cX = (clickX + startX);
            // float cY = (clickY + startY);
            mPath.lineTo(dx, startY);
            mPath.lineTo(dx, dy);
            mPath.lineTo(startX, dy);
            mPath.lineTo(startX, startY);
            rectangle = new Rectangle(new DrawPoint(dx, startY), new DrawPoint(dx, startY), new DrawPoint(dx, startY), new DrawPoint(startX, startY));
            // tempHandWritingList.add(handWriting);
            rectangle.setColor(DrawUtils.getInstance().getPaintColor());
            rectangle.setStoken(DrawUtils.getInstance().getStoken());
            tempRectangles.add(rectangle);
            invalidate();
            //canvas.drawPath(path2, paint);
            //    String fileName = System.currentTimeMillis() + ".jpg";
            //   DrawUtils.getInstance().compressAndSaveBitmapToSDCard(new1Bitmap, fileName, 100);
            //  doStatck.push(fileName);
        }

    }

    private void PaintCircle(MotionEvent event) {
        clickX = event.getX();
        clickY = event.getY();
        //   initPaint();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startX = clickX;
            startY = clickY;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            float dx = Math.abs(event.getX() - startX);
            float dy = Math.abs(event.getY() - startY);
            r = (int) Math.sqrt(dx * dx + dy * dy);
            Circle circle = new Circle(startX, startY, r);
            circle.setColor(DrawUtils.getInstance().getPaintColor());
            circle.setStoken(DrawUtils.getInstance().getStoken());
            tempCircleList.add(circle);
            // mCanvas.drawBitmap(writerCircle(new1Bitmap), 0, 0, null);
            invalidate();
            //   String fileName = System.currentTimeMillis() + ".jpg";
            //   DrawUtils.getInstance().compressAndSaveBitmapToSDCard(new1Bitmap, fileName, 100);
            //  doStatck.push(fileName);
        }

    }


    private void PaintLine(MotionEvent event) {
        clickX = event.getX();
        clickY = event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startX = clickX;
            startY = clickY;
            // mPath.reset();
            mPath.moveTo(clickX, clickY);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            //   float dx = Math.abs(clickX - startX);
            //   float dy = Math.abs(clickY - startY);
//   if(dx>=3||dy>=3){
            //设置贝塞尔曲线的操作点为起点和终点的一半
            // float cX = (clickX + startX);
            // float cY = (clickY + startY);
            mPath.quadTo(startX, startY, event.getX(), event.getY());
            invalidate();
            //  String fileName = System.currentTimeMillis() + ".jpg";
            //  DrawUtils.getInstance().compressAndSaveBitmapToSDCard(new1Bitmap, fileName, 100);
            //  doStatck.push(fileName);
        }

    }

    private void PaintPen(MotionEvent event) {
        clickX = event.getX();
        clickY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = clickX;
                startY = clickY;
                mPath.reset();
                mPath.moveTo(clickX, clickY);
                handWriting = new HandWriting();
                System.out.println("down");
                System.out.println(tempHandWriting == null);
                break;
            case MotionEvent.ACTION_MOVE:
                //   System.out.println("move");
                //System.out.println(tempHandWriting == null);
                handWriting.addPoint(new DrawPoint(clickX, clickY));
                mPath.lineTo(clickX, clickY);
                invalidate();
                startX = clickX;
                startY = clickY;
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("leve");
                tempHandWritingList.add(handWriting);
                handWriting.setColor(DrawUtils.getInstance().getPaintColor());
                handWriting.setWidth(DrawUtils.getInstance().getStoken());
                // .add(handWriting);
                //  handWriting.setColor(DrawUtils.getInstance().getPaintColor());
                //  handWriting.setWidth(DrawUtils.getInstance().getStoken());
                System.out.println("view" + DrawUtils.getInstance().getPaintColor());
                //   pathList.add(path);
                //   xmlUtils.CreatXml(HandWritingList,System.currentTimeMillis()+".xml");
                //  System.out.println("asdasdasd"+HandWritingList.size());
                break;
      /*  }*/
      /*  if (event.getAction() == MotionEvent.ACTION_DOWN) {*/
      /*      //手指点下屏幕时触发*/
      /*  } else if (event.getAction() == MotionEvent.ACTION_MOVE) {*/
      /*      //手指移动时触发*/
      /*      //  Realxiangpi.SetXiangPiLocation(clickX,clickY,startX,startX);*/
//    /*        float dx = Math.abs(clickX - startX);*/
//    /*        float dy = Math.abs(clickY - startY);*/
//   i/*f(dx>=3||dy>=3){*/
      /*      //设置贝塞尔曲线的操作点为起点和终点的一半*/
//    /*         cX = (clickX + startX) / 2;*/
//    /*         cY = (clickY + startY) / 2;*/
/*
*/

      /*      //   mPath.quadTo(startX, startY, cX, cY);*/
      /*      //  if (tempDrawPoint == null)*/
      /*      //   if (tempY != 0) {*/
      /*      //      // mPath.quadTo(tempX, tempY, clickX, clickY);*/
      /*      //  }*/
      /*      //  tempX = clickX;*/
      /*      //   tempY = clickY;*/
      /*      // tempDrawPoint = new DrawPoint();*/
      /*      ///  tempDrawPoint.setX(clickX);*/
      /*      // tempDrawPoint.setY(clickY);*/
      /*      //  listPoint.add(tempDrawPoint);*/
      /*      System.out.println("move");*/
      /*  } else if (event.getAction() == MotionEvent.ACTION_UP) {*/
      /*      //  String fileName = System.currentTimeMillis() + ".jpg";*/
      /*      //   DrawUtils.getInstance().compressAndSaveBitmapToSDCard(new1Bitmap, fileName, 100);*/
      /*      //   doStatck.push(fileName);*/
      /*      tempY = tempX = 0;*/
      /*      dirtyX = clickX;*/
      /*      dirtyX = clickY;*/
/*
*/


        }
        //   invalidate();
        // postInvalidate();
    }


    public Bitmap writer(Bitmap pic) {
        //    initPaint();

        Canvas canvas = null;
        if (isClear) {
            canvas = new Canvas(originalBitmap);
            isClear = false;
        } else {
            canvas = new Canvas(pic);
        }
        canvas.drawPath(mPath, paint);
        if (isClear) {

            return originalBitmap;
        }
        // doStatck.push(DrawUtils.copy(pic));
        return pic;
    }


    public Bitmap writerCircle(Bitmap pic) {
        initPaint();

        Canvas canvas = null;
        if (isClear) {
            canvas = new Canvas(originalBitmap);
        } else {
            canvas = new Canvas(pic);
        }

        //canvas.drawLine(startX, startY, clickX, clickY, paint);//画线
        canvas.drawCircle(startX, startY, r, paint);
        if (isClear) {
            //    doStatck.push(new1Bitmap);
            return originalBitmap;
        }
        //  doStatck.push(pic);
        return pic;
    }

    private void initPaint() {
        if (paint == null)
            paint = new Paint();//初始化画笔

        paint.setStyle(Style.STROKE);//设置为画线

        paint.setAntiAlias(true);//设置画笔抗锯齿

        paint.setColor(color);//设置画笔颜色

        paint.setStrokeWidth(strokeWidth);//设置画笔宽度
    }


    /**
     * @param @param color 设定文件
     * @return void 返回类型
     * @throws
     * @Title: setColor
     * @Description: TODO(设置画笔颜色)
     */
    public void setColor(int color) {

        this.color = color;
        // DrawUtils.getInstance().setPaintColor(color);
        //  initPaint();
        paint.setColor(color);
    }

    public Bitmap getPaint() {
        return originalBitmap;
    }

    public List getHandWritingList() {
        return tempHandWritingList;
    }

    public void Reset() {
        tempHandWritingList.clear();
        tempRectangles.clear();
        tempCircleList.clear();
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
            ((BaseActivity) context).showMessage(false, "存储完成");
            invalidate();
        }
    }

    public void setSaveFlag(boolean isSave) {
        this.isSave = isSave;
    }

    public void setImageBitmap(Bitmap photo) {
      //  originalBitmap = photo;
     //   invalidate();
    }
}
