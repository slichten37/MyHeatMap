package com.example.uncsupport.myheatmap;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class Map extends AppCompatActivity {

    private MapView mv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mv = (MapView) findViewById(R.id.MapView);
        mv.setBackgroundResource(R.drawable.map2);
        double[] longs = getIntent().getExtras().getDoubleArray("longs");
        double[] lats = getIntent().getExtras().getDoubleArray("lats");
        Log.v("CHECK", "Longs and lats size: " + longs.length + ", " + lats.length);
        int time = getIntent().getIntExtra("time", 0);

        for (int i = 0; i < longs.length; i++) {
            mv.addPoint(longs[i], lats[i]);
        }
        mv.invalidate();

        if (time == 1) {
            TextView t = (TextView) findViewById(R.id.textView);
            t.setText("Location Over Past 1 Days");
            mv.setPSize(10);
        }
        if (time == 2) {
            TextView t = (TextView) findViewById(R.id.textView);
            t.setText("Location Over Past 7 Days");
            mv.setPSize(8);
        }
        if (time == 3) {
            TextView t = (TextView) findViewById(R.id.textView);
            t.setText("Location Over Past 14 Days");
            mv.setPSize(7);
        }
        if (time == 4) {
            TextView t = (TextView) findViewById(R.id.textView);
            t.setText("Location Over Past 30 Days");
            mv.setPSize(5);
        }
        if (time == 5) {
            TextView t = (TextView) findViewById(R.id.textView);
            t.setText("Displaying Current Location");
            mv.setPSize(20);
        }
    }


}
