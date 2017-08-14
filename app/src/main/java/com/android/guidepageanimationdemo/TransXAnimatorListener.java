package com.android.guidepageanimationdemo;

import android.animation.ValueAnimator;
import android.graphics.Matrix;
import android.widget.ImageView;

public class TransXAnimatorListener implements ValueAnimator.AnimatorUpdateListener {

    private float[] mStartMatrixValue;
    private float[] mEndMatrixValue;
    private float[] mInterpolateMatrixValue; // 矩阵变换动画过程中间值
    private Matrix mMatrix;
    private ImageView imgBg;

    public TransXAnimatorListener(final ImageView imgBg, float[] startMatrixValue, float[] endMatrixValue) {
        this.mStartMatrixValue = startMatrixValue;
        this.mEndMatrixValue = endMatrixValue;
        this.imgBg = imgBg;
        mInterpolateMatrixValue = new float[9];
        mMatrix = imgBg.getImageMatrix();
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        float animatedValue = (float) animation.getAnimatedValue();
        // 根据动画当前进度设置矩阵的值
        for (int i = 0; i < 9; i++) {
            mInterpolateMatrixValue[i] = mStartMatrixValue[i]
                    + (mEndMatrixValue[i] - mStartMatrixValue[i]) * animatedValue;
        }
        mMatrix.setValues(mInterpolateMatrixValue);
        imgBg.setImageMatrix(mMatrix);
        // setImageMatrix() 不一定会调用invalidate()，此处手动调用即可，
        // 或者为了避免重复绘制也可以参考setImageMatrix()源码进行改进
        imgBg.invalidate();
    }
}