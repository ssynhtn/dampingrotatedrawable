package com.ssynhtn.dampingrotatedrawable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.ssynhtn.library.DampingRotateAnimation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DampingRotateAnimation rotate = new DampingRotateAnimation(-30, 30, 200, 0.8f);
        findViewById(R.id.shaker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(rotate);
            }
        });
    }
}