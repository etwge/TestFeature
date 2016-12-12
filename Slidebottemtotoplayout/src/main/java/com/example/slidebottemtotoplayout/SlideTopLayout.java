package com.example.slidebottemtotoplayout;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.ScrollView;

/**
 * Author: lhy
 * Date : 2016/10/27
 * E-mail:etwge@qq.com
 */

public class SlideTopLayout extends ViewGroup{

    private static final String TAG = "SlideTopLayout";
    private static final int POS_TOP = 0;
    private static final int POS_BOTTOM = 1;


    private float mInitialDownY;
    private float curY;
    private float originalY;
    private View mTopView;
    private View mBottomView;

    private int mTouchSlop;
    private int mActivePointerId;
    private boolean isBeingDragged;

    private int scrollViewPos = POS_TOP;
    private VelocityTracker mVelocityTracker;
    private int mMaximumVelocity, mMinimumVelocity;
    private int pointerIndex;

    public SlideTopLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        ViewConfiguration vc = ViewConfiguration.get(context);
        mTouchSlop = vc.getScaledTouchSlop();
        mMaximumVelocity = vc.getScaledMaximumFlingVelocity();
        mMinimumVelocity = vc.getScaledMinimumFlingVelocity();
    }


    private void initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "onMeasure");
        if(mTopView == null || mBottomView == null){
            ensureView();
        }
        if(mTopView == null || mBottomView == null){
            return;
        }

        measureChild(mTopView, widthMeasureSpec, heightMeasureSpec);
        measureChild(mBottomView, widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.i(TAG, "onLayout");
        if(mTopView == null || mBottomView == null){
            return;
        }
        ensureTargetY();
        mTopView.layout(0, 0, getMeasuredWidth(), mTopView.getMeasuredHeight());

        int mBottomViewTop = 0;
        if(mBottomView.getLayoutParams().height < 2){
            mBottomViewTop = mTopView.getMeasuredHeight();
        }else {
            mBottomViewTop = getMeasuredHeight() - mBottomView.getLayoutParams().height;
        }
        mBottomView.layout(0, mBottomViewTop, getMeasuredWidth(), getMeasuredHeight());
    }

    private void ensureTargetY() {
        if(originalY == 0){
            originalY = curY = mTopView.getMeasuredHeight();
        }
    }

    private void ensureView() {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if(child instanceof ScrollView){
                mBottomView = child;
            }else {
                mTopView = child;
            }
        }
    }

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getActionMasked()){
			case MotionEvent.ACTION_MOVE:
				if(canChildScrollUp())
				break;
		}
		return super.dispatchTouchEvent(ev);
	}

	@Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if(!isEnabled() || canChildScrollUp()){
            return false;
        }

        int action = ev.getActionMasked();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = ev.getPointerId(0);
                isBeingDragged = false;
                pointerIndex = ev.findPointerIndex(mActivePointerId);
                if(pointerIndex < -1){
                    return false;
                }
                mInitialDownY = ev.getY(pointerIndex);
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = ev.getY();
                if (scrollViewPos == POS_TOP) {
                    if (mInitialDownY - moveY > mTouchSlop) {
                        Log.i("xxx", "true");
                        isBeingDragged = true;
                        return true;
                    }
                } else {
                    if (moveY - mInitialDownY > mTouchSlop) {
                        isBeingDragged = true;
                        return true;
                    }
                }
                break;

            case MotionEvent.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isBeingDragged = false;
                mActivePointerId = -1;
                break;
        }
        return false;
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = ev.getActionIndex();
        final int pointerId = ev.getPointerId(pointerIndex);
        if (pointerId == mActivePointerId){
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mActivePointerId = ev.getPointerId(newPointerIndex);
        }
    }

    private boolean canChildScrollUp() {
        return ViewCompat.canScrollVertically(mBottomView, -1);
    }

	private boolean canChildScrollDown(){
		return ViewCompat.canScrollVertically(mBottomView, 1);
	}

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        initVelocityTrackerIfNotExists();
        mVelocityTracker.addMovement(ev);
        switch (ev.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = ev.getPointerId(0);
                isBeingDragged = false;
                break;
            case MotionEvent.ACTION_MOVE:
                pointerIndex = ev.findPointerIndex(mActivePointerId);
                if (pointerIndex < 0) {
                    return false;
                }
                float dy = ev.getY(pointerIndex) - mInitialDownY;
                isBeingDragged = true;
                if(scrollViewPos == 0){
                    moveTargetView(dy + originalY);
                }else {
                    moveTargetView(dy);
                }

                break;
            case MotionEventCompat.ACTION_POINTER_DOWN: {
                pointerIndex = MotionEventCompat.getActionIndex(ev);
                if (pointerIndex < 0) {
                    return false;
                }
                mActivePointerId = ev.getPointerId(pointerIndex);
                break;
            }

            case MotionEventCompat.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if(isBeingDragged){
                    mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                    int velocityY = (int) mVelocityTracker.getYVelocity();
                    boolean overMinimumVelocity  = Math.abs(velocityY) > mMinimumVelocity;
                    if(scrollViewPos == 0){
                        if(isInTopArea() || overMinimumVelocity){
                            animToTop();
                        }else{
                            animToOrigin();
                        }
                    }else {
                        if(!isInTopArea() || overMinimumVelocity){
                            animToOrigin();
                        }else{
                            animToTop();
                        }
                    }
                }
                recycleVelocityTracker();
                isBeingDragged = false;
                mActivePointerId = -1;
                break;

        }
        return true;
    }

    private void animToOrigin() {
        Animation animToOrigin = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                float dy = curY + (curY * interpolatedTime);
                moveTargetView(dy);
            }
        };
        animToOrigin.reset();
        animToOrigin.setDuration(100);
        animToOrigin.setInterpolator(new LinearInterpolator());
        mBottomView.startAnimation(animToOrigin);
        scrollViewPos = 0;
    }

    private void animToTop() {
        Animation animToTop = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                float dy = curY - (curY * interpolatedTime);
                moveTargetView(dy);
            }
        };
        animToTop.reset();
        animToTop.setDuration(100);
        animToTop.setInterpolator(new LinearInterpolator());
        mBottomView.startAnimation(animToTop);
        scrollViewPos = 1;
    }

    /**
     *  是否在顶部区域
     */
    private boolean isInTopArea() {
        return mBottomView.getY() <= originalY / 2;
    }

    private void moveTargetView(float targetY) {
        if(targetY > mTopView.getMeasuredHeight()){
            targetY = mTopView.getMeasuredHeight();
        }

        if(targetY < 0){
            targetY = 0;
        }
        LayoutParams params = mBottomView.getLayoutParams();
        params.height = (int) (getMeasuredHeight() - targetY);
        mBottomView.setLayoutParams(params);
        curY = targetY;
    }
}
