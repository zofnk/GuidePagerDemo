package com.android.guidepageanimationdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义ViewPager页面指示器
 * 圈圈的半径和间距在代码中数字表示像素
 * 所以要根据手机的分辨率动态的改变圈圈的半径和间距
 */
public class ViewPagerIndicatorView extends View {

    private Paint p1 = new Paint();   //默认画画是实心的
    private Paint p2 = new Paint();
    private int interval = 25;        //圈圈之间的间距
    private int number;            //页面指示器的数量
    private int radius = 8;            //圈圈半径

    /**
     * 页面指示器的数量
     *
     * @param number
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * 指示器半径(要适配)
     *
     * @param radius
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    /**
     * 指示器之间的间隔(要适配)
     *
     * @param interval
     */
    public void setInterval(int interval) {
        this.interval = interval;
    }


    public ViewPagerIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private int j;

    public void setJ(int j) {
        this.j = j;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //抗锯齿
        p1.setAntiAlias(true);
        p2.setAntiAlias(true);
        p1.setColor(Color.parseColor("#ff3431")); //前景色主调色：红色
        p2.setColor(Color.parseColor("#999999")); //背景色灰色
        //		p1.setStyle(Style.STROKE);  空心
        int width = getWidth();
        int height = getHeight();
        for (int i = 0; i < number; i++) {
            canvas.drawCircle(width / 2 - (number - 1) * 0.5F * interval + i * interval, height / 2, radius, p2);  //中间点位于画布的中心
        }
        canvas.drawCircle(width / 2 - (number - 1) * 0.5F * interval + j * interval, height / 2, radius, p1);
    }
}
