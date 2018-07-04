package com.felix.a3dpicturbrowsing.view;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;
import android.widget.ImageView;

/**
 * 3D倒影显示照片画廊控件
 * <p>
 * Created by Felix.Zhong on 2015/2/9
 */
public class GalleryFlow extends Gallery {

    public GalleryFlow(Context context, AttributeSet attrs) {
        super(context, attrs);
        setStaticTransformationsEnabled(true);
    }

    private Camera camera = new Camera();

    /**
     * 最大的旋转角度值
     */
    private int maxRotateAngle = 20;
    /**
     * 最大的缩放值
     */
    private int maxZoom = -250;
    /**
     * 画廊的当前页
     */
    private int CurrentOfGallery;

    /**
     * 获得gallery展示图片的中心点
     */
    public int getCurrentOfGallery() {
        return (getWidth() - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
    }

    /**
     * 获得图片的中心点
     */
    public int getCurrentOfView(View view) {
        return view.getLeft() + view.getWidth() / 2;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        CurrentOfGallery = getCurrentOfGallery();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected boolean getChildStaticTransformation(View child, Transformation t) {
        // 得到图片的中心点
        int currentOfChild = getCurrentOfView(child);
        int height = child.getLayoutParams().height;
        int width = child.getLayoutParams().width;
        int rotateAngle;// 旋转角度

        t.clear();
        // 设置图片样式
        t.setTransformationType(Transformation.TYPE_MATRIX);

        if (getSelectedView().hashCode() == child.hashCode()) {
            //选中中心位置
            transformationBitmap((ImageView) child, t, 0);
        } else {
            // 不是中心位置
            rotateAngle = (int) ((float) (CurrentOfGallery - currentOfChild) / width * maxRotateAngle);

            if (Math.abs(rotateAngle) > maxRotateAngle) {
                rotateAngle = rotateAngle < 0 ? -maxRotateAngle : maxRotateAngle;
            }

            transformationBitmap((ImageView) child, t, rotateAngle);
        }

        return true;
    }

    private void transformationBitmap(ImageView child, Transformation t, int rotateAngle) {
        camera.save();// 保存图像变化的效果

        Matrix imageMatrix = t.getMatrix();
        int rotate = Math.abs(rotateAngle);

        int width = child.getWidth();
        int height = child.getHeight();

        // z 轴 正数,图片变大 x 水平方向移动 y 垂直方向移动
        camera.translate(0.0f, 0.f, 100f);
        if (rotate < maxRotateAngle) {
            float zoom = (float) (rotate * 1.5 + maxZoom);
            camera.translate(0.0f, 0.0f, zoom);
            child.setAlpha((int) (255 - rotate * 2.5));
        }

        camera.rotateY(rotateAngle);

        camera.getMatrix(imageMatrix);

        imageMatrix.preTranslate(-(width / 2), -(height) / 2);
        imageMatrix.preTranslate((width / 2), (height) / 2);

        camera.restore();// 还原
    }

}
