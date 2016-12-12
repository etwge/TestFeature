package com.example.test_scroller;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.Scroller;
import android.widget.TextView;

public class AutoScrollTextView extends TextView{

	public AutoScrollTextView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		setSingleLine();
		setEllipsize(null);
		final Scroller scroller = new MyScroller(getContext());
		setScroller(scroller);
		postDelayed(new Runnable() {
			@Override
			public void run() {
				scroller.startScroll(0, 0 , 90, 0, 3000);
				postInvalidate();
			}
		}, 1500);
	}


	class MyScroller extends Scroller {

		public MyScroller(Context context) {
			super(context);
		}

		@Override
		public boolean computeScrollOffset() {
			return super.computeScrollOffset();
		}
	}
}
