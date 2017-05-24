package com.example.uncsupport.myheatmap;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

/**
 * TODO: document your custom view class.
 */
public class MapView extends View {

    ArrayList<Double> lats = new ArrayList<Double>();
    ArrayList<Double> longs = new ArrayList<Double>();
    double yEnd = 35.917138;
    double xStart = -79.058341;
    double yStart = 35.899020;
    double xEnd = -79.042634;
    double dpXRange = 980;
    double dpYRange = 1250;
    double xRange = xEnd - xStart;
    double yRange = yEnd - yStart;
    double xMultiplier = dpXRange / xRange;
    double yMultiplier = dpYRange / yRange;
    int pSize = 5;



    public MapView(Context context) {
        super(context);
    }

    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MapView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        p.setColor(Color.rgb(200,0,0));

        Log.v("YK", "points#: " + longs.size());

        if (pSize == 25) {
            float x = (float) Math.floor((longs.get(longs.size()-1) - xStart) * xMultiplier);
            float y = (float) Math.floor((lats.get(lats.size()-1) - yStart) * yMultiplier);
            canvas.drawCircle(x, 1250 - y, pSize, p);
        }

        else {
            for (int i = 0; i < longs.size(); i++) {
                float x = (float) Math.floor((longs.get(i) - xStart) * xMultiplier);
                float y = (float) Math.floor((lats.get(i) - yStart) * yMultiplier);
                Log.v("VALUES", "long: " + longs.get(i) + " lat: " + lats.get(i));
                Log.v("VALUES", "x: " + x + " y: " + y);
                canvas.drawCircle(x, 1250 - y, pSize, p);
            }
        }
    }

    public void addPoint(double lon, double lat) {
        longs.add(lon);
        lats.add(lat);
    }

    public void setPSize(int x) {
        pSize = x;
    }
}
