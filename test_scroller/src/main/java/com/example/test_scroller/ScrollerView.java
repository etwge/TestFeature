package com.example.test_scroller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class ScrollerView extends LinearLayout {

	private float mCy = 0;
	private Paint mPaint;

	private Scroller mScroller;

	public ScrollerView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();
		mPaint.setColor(Color.YELLOW);

		mScroller = new Scroller(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mCy = getMeasuredHeight();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawCircle(getWidth() / 2,  mCy, 30, mPaint);
	}

	@Override
	public void computeScroll() {
//		mCy = mScroller.getCurrY();

		if(mScroller.computeScrollOffset()){
			Log.i("gggg", "cy:" + mScroller.getCurrY());
			mCy = mScroller.getCurrY();
			invalidate();
		}
	}

	public void scrollToBottom(){
//		mScroller.forceFinished(true);
		mScroller.startScroll(0, 0, 0, 500, 1600);
		Log.i("gggg", "height:" + getHeight());
		invalidate();
	}
}
