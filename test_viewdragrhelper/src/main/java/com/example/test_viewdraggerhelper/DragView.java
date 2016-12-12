package com.example.test_viewdraggerhelper;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class DragView extends LinearLayout {

	private static final String TAG = DragView.class.getSimpleName();
	private ViewDragHelper mDragHelper;

	private View mView;
	private View mAutoView;
	private View mEdgeView;

	private Point mOriginalPoint = new Point();

	public DragView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		mDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
			@Override
			public boolean tryCaptureView(View child, int pointerId) {
				return child == mView || child == mAutoView;
			}

			@Override
			public int clampViewPositionHorizontal(View child, int left, int dx) {
//				Log.i(TAG, "left:" + left + " | dx:" + dx);
//				int leftBound = getPaddingLeft();
//				int rightBound = getWidth() - child.getWidth() - getPaddingRight();
//				int newLeft = Math.min(Math.max(leftBound, left), rightBound);
//				return newLeft;
				return left;
			}

			@Override
			public int clampViewPositionVertical(View child, int top, int dy) {
//				Log.i(TAG, "top:" + top + " | dy:" + dy);
//				int topBound = getPaddingTop();
//				int bottomBound = getHeight() - child.getHeight() - getPaddingBottom();
//				int newTop = Math.min(Math.max(topBound, top), bottomBound);
//				return newTop;
				return top;
			}

			@Override
			public void onViewReleased(View releasedChild, float xvel, float yvel) {
				if(releasedChild == mAutoView){
					mDragHelper.settleCapturedViewAt(mOriginalPoint.x, mOriginalPoint.y);
					invalidate();
				}
			}

			@Override
			public void onEdgeDragStarted(int edgeFlags, int pointerId) {
				mDragHelper.captureChildView(mEdgeView, pointerId);
			}

			@Override
			public int getViewHorizontalDragRange(View child) {
				return super.getViewHorizontalDragRange(child);
			}
		});
		mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
	}

	@Override
	public void computeScroll() {
		if(mDragHelper.continueSettling(true)) {
			invalidate();
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		mOriginalPoint.x = mAutoView.getLeft();
		mOriginalPoint.y = mAutoView.getTop();
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mView = getChildAt(0);
		mAutoView = getChildAt(1);
		mEdgeView = getChildAt(2);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return mDragHelper.shouldInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mDragHelper.processTouchEvent(event);
		return true;
	}
}
