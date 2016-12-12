package com.example.test_scroller;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;


public class HorizontalScrollView extends ViewGroup {

	private static final String TAG = HorizontalScrollView.class.getSimpleName();
	private float mInitialX;
	private int pointerId;

	private int mTouchSlop;
	private int mActivePointerId;
	private float lastScrollX;
	private float maxWidth;
	private Scroller mScroller;
	private VelocityTracker mVelocityTracker;
	private int mMinimumVelocity;
	private int mMaximmumVelocity;

	public HorizontalScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		ViewConfiguration vc = ViewConfiguration.get(context);
		mTouchSlop = vc.getScaledTouchSlop();
		mMinimumVelocity = vc.getScaledMinimumFlingVelocity();
		mMaximmumVelocity = vc.getScaledMaximumFlingVelocity();
		mScroller = new Scroller(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			measureChild(child, widthMeasureSpec, heightMeasureSpec);
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int leftOffset = 0;
		for (int i = 0; i < getChildCount(); i++) {
			final View child = getChildAt(i);
			child.layout(leftOffset,0, leftOffset + child.getMeasuredWidth(),getMeasuredHeight());
			leftOffset += child.getMeasuredWidth();
		}
		maxWidth = leftOffset;
	}


	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		boolean isBeingDragged = false;
		int action = event.getActionMasked();
		switch (action){
			case MotionEvent.ACTION_DOWN:
				mActivePointerId = event.getPointerId(0);
				int pointerIndex = event.findPointerIndex(mActivePointerId);
				if(pointerIndex < 0){
					break;
				}
				mInitialX = event.getX(pointerIndex);
				break;
			case MotionEvent.ACTION_MOVE:
				if(mActivePointerId == -1){
					break;
				}
				pointerIndex = event.findPointerIndex(mActivePointerId);
				float mDeltaX = event.getX(pointerIndex) - mInitialX;
				if(Math.abs(mDeltaX) > mTouchSlop){
					isBeingDragged = true;
				}
				break;
		}
		return isBeingDragged;
	}

	@Override
	public void computeScroll() {
		if(mScroller.computeScrollOffset()){
			int curX = mScroller.getCurrX();
			Log.i("xxx", "curX:" + curX);
			scrollTo(curX, 0);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		initVelocityTrackerIfNotExist();
		mVelocityTracker.addMovement(event);
		int action = event.getActionMasked();
		switch (action){
			case MotionEvent.ACTION_DOWN:
				mActivePointerId = event.getPointerId(0);
				int pointerIndex = event.findPointerIndex(mActivePointerId);
				if(pointerIndex < 0){
					break;
				}
				lastScrollX = getScrollX();
				mInitialX = event.getX(pointerIndex);
				return true;
//				break;
			case MotionEvent.ACTION_MOVE:
				mActivePointerId = findPointIndex(event);
				if(mActivePointerId == -1){
					Log.i(TAG, "invalid pointer id");
					break;
				}
				float mDeltaX = event.getX() - mInitialX;
				Log.i("xxx", "scroll x:" + getScrollX());
				Log.i("xxx", "mDeltaX:" + mDeltaX);
				doScrollX(mDeltaX);
				return true;
//				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				mVelocityTracker.computeCurrentVelocity(1000, mMaximmumVelocity);
				int mVelocity = (int) mVelocityTracker.getXVelocity();
				boolean overMinimumVelocity = Math.abs(mVelocity) > mMinimumVelocity;
				Log.i("xxx", "maxWidth:" + maxWidth);
				if(overMinimumVelocity){
					Log.i("xxx", "fling");

					mScroller.fling(getScrollX(), 0, -mVelocity, 0, 0, (int) (maxWidth - getWidth()), 0, 0);


				}
				mInitialX = 0;
				mActivePointerId = -1;
				recycleVelocityTracker();
				break;
		}
		return false;
	}

	private void initVelocityTrackerIfNotExist() {
		if(mVelocityTracker == null){
			mVelocityTracker = VelocityTracker.obtain();
		}
	}

	private void recycleVelocityTracker(){
		if (mVelocityTracker != null) {
			mVelocityTracker.recycle();
			mVelocityTracker = null;
		}
	}

	private void doScrollX(float mDeltaX) {
		float x = lastScrollX - mDeltaX;
		if(x + getScrollX() < 0){
			x = 0;
		}else if(x + getScrollX() > maxWidth){
			x = maxWidth - getScrollX();
		}
		scrollTo((int) x, 0);
		Log.i("xxx", "scroll x:" + getScrollX());
	}

	private int findPointIndex(MotionEvent event) {
		int index = event.getActionIndex();
		return event.getPointerId(index);
	}
}
