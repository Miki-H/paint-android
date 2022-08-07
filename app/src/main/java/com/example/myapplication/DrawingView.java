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
    public int color = Color.BLACK, colorBorder = Color.BLACK;
	int touchX = -1, touchY = -1, firstTouchX = -1, firstTouchY = -1;
    public int border = 10;
    public String type = "Rect";

    private List <TouchCoord> Touchs = new ArrayList<>();

    public DrawingView(Context context){

        super(context);
    }

    public DrawingView(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);
    }

    private void drawingShape(Canvas canvas, int left, int top, int right, int bottom, String type, Paint paint) throws Exception {
        switch(type){
            case "Rect":
                canvas.drawRect(left, top, right, bottom, paint);
                break;
            case "Oval":
                canvas.drawOval(left, top, right, bottom, paint);
                break;
            case "Line":
                canvas.drawLine(left + 100, top + 100, right - 100, bottom - 100, paint);
                break;
            default:
                throw new Exception("Unrecognizable shape");
        }
    }

    private void drawing(Canvas canvas, int left, int top, int right, int bottom, int border, int color, int colorBorder, String type) throws Exception {
        Paint paintToDraw = this.paint;
        paintToDraw.setStyle(Paint.Style.FILL);
        paintToDraw.setColor(color);
        drawingShape(canvas, left, top, right, bottom, type, paintToDraw);
        paintToDraw.setStyle(Paint.Style.STROKE);
        paintToDraw.setColor(colorBorder);
        paintToDraw.setStrokeWidth(border);
        drawingShape(canvas, left, top, right, bottom, type, paintToDraw);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Touchs = TouchCoord.getTouchs();

        for(int i=0; i<Touchs.size(); i++){
            try {
                drawing(canvas,
                        Touchs.get(i).getLeft() - 100,
                        Touchs.get(i).getTop() - 100,
                        Touchs.get(i).getRight() + 100,
                        Touchs.get(i).getBottom() + 100,
                        Touchs.get(i).getBorder(),
                        Touchs.get(i).getColor(),
                        Touchs.get(i).getColorBorder(),
                        Touchs.get(i).getType());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        if(touchX!=-1 && touchY != -1){
            try {
                drawing(canvas,
                        firstTouchX - 100,
                        firstTouchY - 100,
                        touchX + 100,
                        touchY + 100,
                        this.border,
                        this.color,
                        this.colorBorder,
                        this.type);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
            new TouchCoord(firstTouchX, firstTouchY, touchX, touchY, border, color, colorBorder, type, paint);
            touchX = touchY = -1;
        }

        invalidate();

        return true;
    }

}

