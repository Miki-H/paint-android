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
    private int color = Color.BLACK;
    private int left, top, right, bottom;

    public TouchCoord(int left, int top, int right, int bottom, int color, Paint paint){
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.color = color;
        this.paint = paint;
        Touchs.add(this);
    }

    public int getColor() {
        return color;
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
