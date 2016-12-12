package com.example.test_floatdialog;

import android.view.View;
import android.view.WindowManager;

/**
 * Author: lhy
 * Date : 2016/11/1
 * E-mail:etwge@qq.com
 */

public interface IFloatingView {

    void init(View v);
    void show();
    void updatePos(WindowManager.LayoutParams params);
    void remove();
    void onClick();
}
