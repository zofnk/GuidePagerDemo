package com.android.guidepageanimationdemo.utils;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;

/**
 * Created by callhh on 2017/8/13.
 */

public class ScreenUtils {

    /**
     * 获取手机屏幕宽度
     */
    public static int getScreenWidth(Activity activity) {
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }
    /**
     * 获取手机屏幕高度
     */
    public static int getScreenHeight(Activity activity) {
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        int height  = wm.getDefaultDisplay().getHeight();
        return height;
    }
}
