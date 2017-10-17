package com.mymeek.hencoderimitate.sample;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.mymeek.hencoderimitate.R;

/**
 * Created by witt on 2017/10/16.
 */

public class Xiaomi extends View{
    public Xiaomi(Context context) {
        super(context);
    }

    public Xiaomi(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Xiaomi(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Camera camera = new Camera();

    int degree;
    ObjectAnimator animator;

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);

        animator = ObjectAnimator.ofInt(this, "degree", 0, 360);
        animator.setDuration(1500);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int x = centerX - bitmapWidth / 2;
        int y = centerY - bitmapHeight / 2;

        canvas.save();
        canvas.translate(centerX, centerY);
        camera.save();
        camera.rotateZ(degree);
        camera.rotateY(-45);
        camera.rotateZ(-degree);
        camera.applyToCanvas(canvas);
        camera.restore();
        canvas.translate(-centerX, -centerY);
        canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();

        canvas.save();
        canvas.translate(centerX, centerY);
        canvas.rotate(-degree);
        canvas.clipRect(-centerX, -centerY, 0, centerY);
        canvas.rotate(degree);
        canvas.translate(-centerX, -centerY);
        //canvas.drawBitmap(bitmap, x, y, paint);
        canvas.restore();
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        animator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        animator.end();
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
        invalidate();
    }
}
