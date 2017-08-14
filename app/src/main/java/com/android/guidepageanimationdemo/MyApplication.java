package com.android.guidepageanimationdemo;

import android.app.Activity;
import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.LinkedList;
import java.util.List;

public class MyApplication extends Application {

    private static List<Activity> activitys = null;

    public static MyApplication application;

    public static MyApplication getInstance() {
        return application;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        activitys = new LinkedList<Activity>();
        Fresco.initialize(this);//fresco图片加载初始化
    }


    /**
     * 保存已打开过的activity到容器里
     */
    public static void addActivity(Activity activity) {
        if (activitys != null && activitys.size() > 0) {
            if (!activitys.contains(activity)) {
                activitys.add(activity);
            }
        } else {
            activitys.add(activity);
        }
    }

    /**
     * 移除已打开过的activity到容器里
     */
    public static void removeActivity(Activity activity) {
        activitys.remove(activity);
    }

    /**
     * 关闭所有activity
     */
    public static void finishAll() {
        if (activitys != null && activitys.size() > 0) {
            for (Activity activity : activitys) {
                activity.finish();
            }
        }
    }

    /**
     * 退出应用 finish所有创建的Activity
     */
    public static void exitApp() {
//        PrintUtil.cancleToast();  //退出应用不再显示Toast
        finishAll();
        android.os.Process.killProcess(android.os.Process.myPid());//sha掉当前进程
//        System.gc();
    }

}
