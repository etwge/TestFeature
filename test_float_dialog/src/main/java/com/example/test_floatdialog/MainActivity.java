package com.example.test_floatdialog;

import android.app.Dialog;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    WindowManager mWindowManager;
    WindowManager.LayoutParams mLayout;
    FloatingView floatView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
//        floatView = new FloatingView(this, null);
//        floatView.show();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.i("xxx", "hasFocus:" + hasFocus);
        if(!hasFocus){
            floatView.remove();
        }
        Rect rect = new Rect();
        // /取得整个视图部分,注意，如果你要设置标题样式，这个必须出现在标题样式之后，否则会出错
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int top = rect.top;//状态栏的高度，所以rect.height,rect.width分别是系统的高度的宽度

        Log.i("top",""+top);
    }
}
