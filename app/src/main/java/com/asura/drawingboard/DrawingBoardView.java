package com.asura.drawingboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * Created by Asura on 2017/10/16 13:36.
 * 画板视图
 */
public class DrawingBoardView extends SurfaceView implements SurfaceHolder.Callback, OnTouchListener {
    private Paint mPaint;
    private Path mPath;
    //上一次触摸事件的终点的x，y
    private float mLastX;
    private float mLastY;

    public DrawingBoardView(Context context) {
        this(context, null);
    }

    public DrawingBoardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawingBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        getHolder().addCallback(this);
        mPaint = new Paint();
        mPath = new Path();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(10);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        setOnTouchListener(this);
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        draw();
    }

    private void draw() {
        Canvas canvas = getHolder().lockCanvas();
        canvas.drawColor(Color.WHITE);
        canvas.drawPath(mPath, mPaint);
        getHolder().unlockCanvasAndPost(canvas);
    }

    /**
     * 清除视图
     */
    public void clearView() {
        mPath.reset();
        draw();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        /*switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(motionEvent.getX(), motionEvent.getY());
                draw();
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(motionEvent.getX(), motionEvent.getY());
                draw();
                break;
        }
        return true;*/

        float x = motionEvent.getX();
        float y = motionEvent.getY();

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(x, y);
                mLastX = x;
                mLastY = y;
                draw();
                return true;
            case MotionEvent.ACTION_MOVE:
                //结束点
                float endX = (mLastX + x) / 2;
                float endY = (mLastY + y) / 2;
                //贝塞尔曲线让线条更顺滑
                //上一次的终点作为起点,上一个点作为控制点,中间点作为终点
                mPath.quadTo(mLastX, mLastY, endX, endY);
                //记录上一次的操作点
                mLastX = x;
                mLastY = y;
                draw();
                break;
            case MotionEvent.ACTION_UP:
                break;

        }
        return super.onTouchEvent(motionEvent);
    }
}
