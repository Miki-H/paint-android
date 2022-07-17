package com.example.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Shape {
    private Canvas canvas;
    private Paint paint;
    private int left, right, top, bottom;
    private int Bordercolor = Color.BLACK;
    private int Fillcolor = Color.BLACK;

    public Shape(Canvas canvas, Paint paint, int left, int right, int top, int bottom, int bordercolor, int fillcolor, String type) throws Exception {
        this.canvas = canvas;
        this.paint = paint;
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
        Bordercolor = bordercolor;
        Fillcolor = fillcolor;
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Bordercolor);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Fillcolor);
        switch (type){
            case "Rect":
                canvas.drawRect(left, top, right, bottom, paint);
            case "Oval":
                canvas.drawOval(left, top, right, bottom, paint);
            default:
                throw new Exception("Unrecognizable shape");
        }
    }

    public Shape(Canvas canvas, Paint paint, int startX, int stopX, int startY, int stopY, int bordercolor) {
        this.canvas = canvas;
        this.paint = paint;
        this.left = startX;
        this.right = stopX;
        this.top = startY;
        this.bottom = stopY;
        Bordercolor = bordercolor;
        paint.setColor(Bordercolor);
        canvas.drawLine(startX, startY, stopX, stopY, paint);
    }
}
