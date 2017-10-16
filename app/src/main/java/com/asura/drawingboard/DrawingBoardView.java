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
    private Paint mPaint = new Paint();
    private Path mPath = new Path();

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
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(motionEvent.getX(), motionEvent.getY());
                draw();
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(motionEvent.getX(), motionEvent.getY());
                draw();
                break;
        }
        return true;
    }
}
