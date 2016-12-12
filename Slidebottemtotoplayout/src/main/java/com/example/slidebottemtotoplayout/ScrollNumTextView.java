package com.example.slidebottemtotoplayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.TextView;

/**
 * Author: lhy
 * Date : 2016/11/2
 * E-mail:etwge@qq.com
 */

public class ScrollNumTextView extends TextView{

    private int scrollNum;

    public ScrollNumTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollNum(int num){
        scrollNum = num;
        animToTargetNum();
    }

    private void animToTargetNum() {
        animation.reset();
        animation.setDuration(1500);
        animation.setInterpolator(new AccelerateInterpolator());
        startAnimation(animation);
    }

    private Animation animation = new Animation() {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            int targetNum = (int) (scrollNum * interpolatedTime);
            setText(String.valueOf(targetNum));
        }
    };
}
