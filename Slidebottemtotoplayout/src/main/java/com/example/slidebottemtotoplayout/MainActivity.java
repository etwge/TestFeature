package com.example.slidebottemtotoplayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        findViewById(R.id.go).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ScrollNumTextView tv = (ScrollNumTextView) findViewById(R.id.scroll_num);
//                tv.setScrollNum(124404);
//            }
//        });
    }
}
