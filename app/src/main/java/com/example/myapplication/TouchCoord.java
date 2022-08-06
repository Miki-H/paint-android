package com.example.myapplication;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

public class TouchCoord{
    private static List<TouchCoord> Touchs = new ArrayList<TouchCoord>();

    public static List<TouchCoord> getTouchs() {
        return new ArrayList<>(Touchs);
    }

    public static void removeLastTouch(){
        int sizeTouch = Touchs.size();
        if(sizeTouch != 0){
            Touchs.remove(sizeTouch - 1);
        }
    }

    public static void clearDrawings(){
        Touchs.clear();
    }

    private Paint paint;
    private int color = Color.BLACK, colorBorder = Color.BLACK;

    private int border = 1;

    private int left, top, right, bottom;
    public TouchCoord(int left, int top, int right, int bottom, int border, int color, int colorBorder, Paint paint){
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.border = border;
        this.color = color;
        this.colorBorder = colorBorder;
        this.paint = paint;
        Touchs.add(this);
    }

    public int getColor() {
        return color;
    }

    public int getColorBorder() {
        return colorBorder;
    }

    public int getBorder() {
        return border;
    }

    public Paint getPaint() {
        return paint;
    }

    public int getLeft() {
        return left;
    }

    public int getTop() {
        return top;
    }

    public int getRight() {
        return right;
    }

    public int getBottom() {
        return bottom;
    }
}
