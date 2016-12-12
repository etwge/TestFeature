package com.example.test_floatdialog;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

/**
 * Author: lhy
 * Date : 2016/11/1
 * E-mail:etwge@qq.com
 */

public class FloatingView extends FrameLayout implements IFloatingView{


    WindowManager mWindowManager;
    WindowManager.LayoutParams mLayout;

    // 声明屏幕的宽高
    float x, y;
    int top;
    float mTouchStartX;
    float mTouchStartY;
    private float mInitialX;
    private float mInitialY;
    private int mTouchSlop;

    public FloatingView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        addTargetView();
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    private void addTargetView() {
        View.inflate(getContext(), R.layout.view_float, this);
        findViewById(R.id.btn_float).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "click me", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        // 取得系统窗体
        mWindowManager = (WindowManager) getContext().getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        // 窗体的布局样式
        mLayout = new WindowManager.LayoutParams();
        // 设置窗体显示类型——TYPE_SYSTEM_ALERT(系统提示)
        mLayout.type = WindowManager.LayoutParams.TYPE_TOAST;
        // 设置窗体焦点及触摸：
        // FLAG_NOT_FOCUSABLE(不能获得按键输入焦点)
        mLayout.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 设置显示的模式
        mLayout.format = PixelFormat.RGBA_8888;
        // 设置对齐的方法
        mLayout.gravity = Gravity.TOP | Gravity.LEFT;
        // 设置窗体宽度和高度
        mLayout.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayout.height = WindowManager.LayoutParams.WRAP_CONTENT;
    }

    @Override
    public void init(View v) {
        addView(v);
    }

    @Override
    public void show() {
        mWindowManager.addView(this, mLayout);
    }

    boolean isBeingDragged;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(!isEnabled()){
            return false;
        }
        switch (ev.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                mTouchStartX = mInitialX = ev.getX();
                mTouchStartY = mInitialY = ev.getY();
                isBeingDragged = false;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(ev.getX() - mInitialX);
                float dy = Math.abs(ev.getY() - mInitialY);
                if(dx > mTouchSlop || dy > mTouchSlop){
                    isBeingDragged = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                isBeingDragged = false;
                break;
                
        }
        return isBeingDragged;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 获取相对屏幕的坐标，即以屏幕左上角为原点
        x = event.getRawX();
        y = event.getRawY() - top; // 25是系统状态栏的高度
        Log.i("startP", "startX" + mTouchStartX + "====startY"
                + mTouchStartY);
        Log.i("startP", "RawX" + x + "====RawY"
                + y);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 获取相对View的坐标，即以此View左上角为原点
                mTouchStartX = event.getX();
                mTouchStartY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                // 更新浮动窗口位置参数
                mLayout.x = (int) (x - mTouchStartX);
                mLayout.y = (int) (y - mTouchStartY);
                updatePos(mLayout);
                break;
            case MotionEvent.ACTION_UP:
                // 更新浮动窗口位置参数
                mLayout.x = (int) (x - mTouchStartX);
                mLayout.y = (int) (y - mTouchStartY);
                updatePos(mLayout);

                // 可以在此记录最后一次的位置
                mTouchStartX = mTouchStartY = 0;
                break;
        }
        return true;
    }

    @Override
    public void updatePos(WindowManager.LayoutParams params) {
        mWindowManager.updateViewLayout(this, params);
    }

    @Override
    public void remove() {
        mWindowManager.removeView(this);
    }

    @Override
    public void onClick() {

    }
}
