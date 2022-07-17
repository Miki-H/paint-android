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
import java.util.Random;


public class DrawingView extends View {
    private Paint paint = new Paint();
    Random r = new Random();
    int color = Color.BLACK;
	int touchX = -1, touchY = -1, firstTouchX = -1, firstTouchY = -1;

    private List <TouchCoord> Touchs = new ArrayList<>();

    public DrawingView(Context context){

        super(context);
    }

    public DrawingView(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        Touchs = TouchCoord.getTouchs();

        for(int i=0; i<Touchs.size(); i++){
            paint.setColor(Touchs.get(i).getColor());
            canvas.drawRect(Touchs.get(i).getLeft() - 100, Touchs.get(i).getTop() - 100, Touchs.get(i).getRight() + 100, Touchs.get(i).getBottom() + 100, paint);
        }


        if(touchX!=-1 && touchY != -1){
            canvas.drawRect(firstTouchX - 100, firstTouchY - 100, touchX + 100, touchY + 100, paint);
            color = Color.rgb(r.nextInt(255), r.nextInt(255), r.nextInt(255));
            paint.setColor(color);
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
            TouchCoord touch_up = new TouchCoord(firstTouchX, firstTouchY, touchX, touchY, color, paint);
            //Touchs.add(touch_up);
            touchX = -1;
            touchY = -1;
            //color = Color.BLACK;
        }

        invalidate();

        return true;
    }

}

