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
    public String type = "Line";

    private List <Integer> FreeX = new ArrayList<Integer>();
    private List <Integer> FreeY = new ArrayList<Integer>();
    private TouchCoord touch = null;

    private List <TouchCoord> Touchs = new ArrayList<>();

    public DrawingView(Context context){

        super(context);
    }

    public DrawingView(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);
    }

    private void drawingShape(Canvas canvas, int left, int top, int right, int bottom, String type, Paint paint) throws Exception {
        int rest, restV, restH;
        switch(type){
            case "Rect":
                canvas.drawRect(left, top, right, bottom, paint);
                break;
            case "Square":
                restV = right - left;
                restH = bottom - top;
                rest = restV - restH;
                canvas.drawRect(left, top, right, bottom + rest, paint);
                break;
            case "Oval":
                canvas.drawOval(left, top, right, bottom, paint);
                break;
            case "Circle":
                restV = right - left;
                restH = bottom - top;
                rest = restV - restH;
                canvas.drawOval(left, top, right, bottom + rest, paint);
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

    private void drawing(Canvas canvas, List<Integer> X, List<Integer> Y, int border, int color){
        this.paint.setStrokeWidth(border);
        this.paint.setColor(color);
        for(int i=0; i<X.size(); i++){
            int pointX = X.get(i);
            int pointY = Y.get(i);
            canvas.drawPoint(pointX, pointY, this.paint);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Touchs = TouchCoord.getTouchs();

        for(int i=0; i<Touchs.size(); i++){
            TouchCoord toDraw = Touchs.get(i);
            if(toDraw.getType().equals("Free")){
                drawing(canvas, toDraw.getxList(), toDraw.getyList(), toDraw.getBorder(), toDraw.getColor());
            }else{
                try {
                    drawing(canvas,
                            toDraw.getLeft() - 100,
                            toDraw.getTop() - 100,
                            toDraw.getRight() + 100,
                            toDraw.getBottom() + 100,
                            toDraw.getBorder(),
                            toDraw.getColor(),
                            toDraw.getColorBorder(),
                            toDraw.getType());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        if(touchX!=-1 && touchY != -1){
            if (this.type.equals("Free")){
                drawing(canvas, FreeX, FreeY, this.border, this.colorBorder);
            }else{
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
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(touchX==-1){
            firstTouchX = (int) event.getX();
            firstTouchY = (int) event.getY();
        }

        if(type.equals("Free")){
            FreeX.add((int) event.getX());
            FreeY.add((int) event.getY());
            if (touch == null){
                touch = new TouchCoord(FreeX, FreeY, border, colorBorder, paint);
            }else{
                touch.addToFreeLists((int) event.getX(), (int) event.getY());
            }
            if(event.getAction() == MotionEvent.ACTION_UP){
                FreeX.clear();
                FreeY.clear();
                touch = null;
                touchX = touchY = -1;
            }
        }else{
            touchX = (int) event.getX();
            touchY = (int) event.getY();

            if(event.getAction() == MotionEvent.ACTION_UP){
                new TouchCoord(firstTouchX, firstTouchY, touchX, touchY, border, color, colorBorder, type, paint);
                touchX = touchY = -1;
            }
        }

        invalidate();

        return true;
    }

}

