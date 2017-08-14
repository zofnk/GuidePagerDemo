package com.android.guidepageanimationdemo.activity;

import android.animation.ValueAnimator;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.guidepageanimationdemo.MyViewPagerAdapter;
import com.android.guidepageanimationdemo.R;
import com.android.guidepageanimationdemo.TransXAnimatorListener;
import com.android.guidepageanimationdemo.utils.ScreenUtils;
import com.android.guidepageanimationdemo.view.ViewPagerIndicatorView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.pager_welcom)
    ViewPager mPager;
    @BindView(R.id.viewPagerIndicatorView)
    ViewPagerIndicatorView viewPagerIndicatorView;
    private ArrayList<View> viewList;
    private SimpleDraweeView imgBg1, imgBg2, imgBg3, imgBg4, imgBg5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        addView();
        initVpIndicator();
        setListener();
    }

    private void initVpIndicator() {
        mPager.setAdapter(new MyViewPagerAdapter(viewList));
        viewPagerIndicatorView.setNumber(viewList.size());
        viewPagerIndicatorView.setRadius(10);
        //设置指示器间隔
        viewPagerIndicatorView.setInterval(40);
    }

    /***
     * viewpager监听事件
     */
    private void setListener() {
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // ViewPager页面滑动的回到，会执行很多次
            }

            @Override
            public void onPageSelected(int position) {
                viewPagerIndicatorView.setJ(position);
                // 在页面选择
                switch (position) {
                    case 0:
                        setImageTranslateMatrix(imgBg1);
                        break;
                    case 1:
                        setImageTranslateMatrix(imgBg2);
                        break;
                    case 2:
                        setImageTranslateMatrix(imgBg3);
                        break;
                    case 3:
                        setImageTranslateMatrix(imgBg4);
                        break;
                    case 4:
                        setImageTranslateMatrix(imgBg5);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addView() {
        LayoutInflater lf = getLayoutInflater().from(this);
        View view1 = lf.inflate(R.layout.layout_welcome1, null);
        View view2 = lf.inflate(R.layout.layout_welcome2, null);
        View view3 = lf.inflate(R.layout.layout_welcome3, null);
        View view4 = lf.inflate(R.layout.layout_welcome4, null);
        View view5 = lf.inflate(R.layout.layout_welcome5, null);

        imgBg1 = (SimpleDraweeView) view1.findViewById(R.id.imgBg);
        imgBg2 = (SimpleDraweeView) view2.findViewById(R.id.imgBg2);
        imgBg3 = (SimpleDraweeView) view3.findViewById(R.id.imgBg3);
        imgBg4 = (SimpleDraweeView) view4.findViewById(R.id.imgBg4);
        imgBg5 = (SimpleDraweeView) view5.findViewById(R.id.imgBg5);
        imgBg1.setImageResource(R.mipmap.ic_welcome_01);
        imgBg2.setImageResource(R.mipmap.ic_welcome_02);
        imgBg3.setImageResource(R.mipmap.ic_welcome_03);
        imgBg4.setImageResource(R.mipmap.ic_welcome_04);
        imgBg5.setImageResource(R.mipmap.ic_welcome_05);
        setImageTranslateMatrix(imgBg1);

        //进入首页
        view5.findViewById(R.id.tvEnterApp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(BzwWelcomeActivity.this, BzwMainActivity.class));
//                finish();
//                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                Toast.makeText(MainActivity.this, "123···Let's go to the front page !", Toast.LENGTH_SHORT).show();
            }
        });
        viewList = new ArrayList<>();// 将要分页显示的View装入数组中
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);
        viewList.add(view5);

    }

    /**
     * 设置背景图片平移
     */
    private void setImageTranslateMatrix(final ImageView imgBg) {
        if (imgBg == null) {
            return;
        }
        imgBg.post(new Runnable() {
            @Override
            public void run() {
                RectF rectF = new RectF(imgBg.getDrawable().getBounds()); // 存放图片区域的矩形
                int screenWidth = ScreenUtils.getScreenWidth(MainActivity.this);
                // 图片宽高
                float dw = rectF.width();
                float dh = rectF.height();
                // 控件宽高
                int width = imgBg.getWidth();
                int height = imgBg.getHeight();
                //初始化矩阵值
                imgBg.setImageMatrix(new Matrix());
                // 获取矩阵
                Matrix matrix = imgBg.getImageMatrix();
                //缩放比例
                float value = height / dh;
                // 缩放变换,按照比例放大，是图片高度等于控件高度
                matrix.postScale(value, value);
                // 矩阵缩放映射到存放图片区域的矩形
                matrix.mapRect(rectF);
                // 将变换应用到图片
                imgBg.setImageMatrix(matrix);
                setTranslateAnimation(imgBg, -(dw * value - screenWidth), 0);
            }
        });
    }


    public void setTranslateAnimation(final ImageView imgBg, float translateX, float translateY) {
        Matrix matrix = imgBg.getImageMatrix();
        float[] startMatrixValue = new float[9];
        float[] endMatrixValue = new float[9];
        // 获取矩阵的初始值
        matrix.getValues(startMatrixValue);
        // 对矩阵进行变换
        matrix.postTranslate(translateX, translateY);
        // 获取变换后的值
        matrix.getValues(endMatrixValue);
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.addUpdateListener(new TransXAnimatorListener(imgBg, startMatrixValue, endMatrixValue));
        animator.setDuration(4000);
        animator.setTarget(imgBg);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }
}
