package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;


public class DrawingView extends View {
    private Paint paint = new Paint();
    // Random r = new Random();
    public int color = Color.BLACK, colorBorder = Color.BLACK;
	int touchX = -1, touchY = -1, firstTouchX = -1, firstTouchY = -1;
    public int border = 1;

    private List <TouchCoord> Touchs = new ArrayList<>();

    public DrawingView(Context context){

        super(context);
    }

    public DrawingView(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);
    }

    private void drawing(Canvas canvas, int left, int top, int right, int bottom, int border, int color, int colorBorder){
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setColor(color);
        canvas.drawRect(left, top, right, bottom, this.paint);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setColor(colorBorder);
        this.paint.setStrokeWidth(border);
        canvas.drawRect(left, top, right, bottom, this.paint);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        Touchs = TouchCoord.getTouchs();

        for(int i=0; i<Touchs.size(); i++){
            drawing(canvas,
                    Touchs.get(i).getLeft() - 100,
                    Touchs.get(i).getTop() - 100,
                    Touchs.get(i).getRight() + 100,
                    Touchs.get(i).getBottom() + 100,
                    Touchs.get(i).getBorder(),
                    Touchs.get(i).getColor(),
                    Touchs.get(i).getColorBorder());
        }


        if(touchX!=-1 && touchY != -1){
            drawing(canvas,
                    firstTouchX - 100,
                    firstTouchY - 100,
                    touchX + 100,
                    touchY + 100,
                    this.border,
                    this.color,
                    this.colorBorder);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(touchX==-1){
            firstTouchX = (int) event.getX();
            firstTouchY = (int) event.getY();
        }

        touchX = (int) event.getX();
        touchY = (int) event.getY();

        if(event.getAction() == MotionEvent.ACTION_UP){
            TouchCoord touch_up = new TouchCoord(firstTouchX, firstTouchY, touchX, touchY, border, color, colorBorder, paint);
            touchX = touchY = -1;
        }

        invalidate();

        return true;
    }

}

