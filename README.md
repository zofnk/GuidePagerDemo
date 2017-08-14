##Pm需求:单张背景图做平移,至图片的最左移动至最右边,ImageView本身没有什么变化,类似Android的桌面单张长图的模式。
想法:
* 需要达成需求,等比缩放后图的宽度一定要大于屏幕宽度,否则就没有效果
* 画一个保存图片区域的矩形 , 再利用矩阵对矩形的Translate进行变换
* 利用ValueAnimator做成动画效果即可

//应该用如下方法才是创建Matrix对象
Matrix matrix = new Matrix ( mImage.getImageMatrix () );

###一、先将XML中的ImageView的scaleType设置成matrix

    使用RectF画出需要存放图片的矩形区域
    // 存放图片区域的矩形
    RectF rectF = new RectF(img.getDrawable().getBounds());
    // 图片宽高
    float dw = rectF.width();
    float dh = rectF.height();
    // 控件宽高
    int width = imgBg.getWidth();
    int height = imgBg.getHeight();
    //初始化矩阵值
    imgBg.setImageMatrix(new Matrix());
    //获取矩阵
    Matrix matrix = imgBg.getImageMatrix();
    //缩放比例
    float value = height / dh;
    // 缩放变换,按照比例放大，使图片高度等于控件高度
    matrix.postScale(value, value);
    // 矩阵缩放映射到存放图片区域的矩形
    matrix.mapRect(rectF);
    // 移动图片到坐标原点
    matrix.postTranslate(0, 0);
    // 将变换应用到图片
    imgBg.setImageMatrix(matrix);

注意:
* postTranslate是指在setScale后平移(preTranslate是在setScale前),他们参数是平移的距离,而不是平移目的地的坐标。

###二、设置位移动画
位移动画的移动距离为图片对当前屏幕缩放后的真实宽度减去屏幕(或者当前场景下ImageView宽度)

    setTranslateAnimation(imgBg, -(dw * value - screenWidth), 0);

动画的添加:
  
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
        // 设置插值器 LinearInterpolator为匀速效果
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }


###三、设置好初始状态的矩阵和最终状态的矩阵及变换动画

    class TransXAnimatorListener implements ValueAnimator.AnimatorUpdateListener {

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

此处onAnimationUpdate方法内矩阵变换参考:
[[篱开罗 - 自定义可旋转、平移、缩放的ImageView](http://www.jianshu.com/u/619e9a597a07)](http://www.jianshu.com/p/938ca88fb16a) 中的 [动画技巧]

###四、后续优化
测试过程中放一些分辨率高的图片的时候发现出现卡顿,使用bitmap压缩的话在压缩过程中会出现短时间的空白屏
* 这里使用[Fresco](https://github.com/facebook/fresco)的SimpleDraweeView进行替换当前的ImageView
* 添加Viewpager和Indicator效果,完成需求

也有使用过Glide去加载并简化,不过效果会有些问题,还需要再度完善,新手一枚还需要多多练习。




