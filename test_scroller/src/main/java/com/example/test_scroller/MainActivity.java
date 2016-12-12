package com.example.test_scroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		final ScrollerView mScrollerView = (ScrollerView) findViewById(R.id.sc);
//		mScrollerView.postDelayed(new Runnable() {
//			@Override
//			public void run() {
//				mScrollerView.scrollToBottom();
//			}
//		}, 1600);

		AutoScrollTextView tv = (AutoScrollTextView) findViewById(R.id.sc);
		Animation animation = new Animation() {
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				super.applyTransformation(interpolatedTime, t);
			}
		};
	}
}
