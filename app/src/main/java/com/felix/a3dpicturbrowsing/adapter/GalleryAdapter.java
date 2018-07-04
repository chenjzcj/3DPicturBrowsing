package com.felix.a3dpicturbrowsing.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by Felix.Zhong on 2015/2/9
 */
public class GalleryAdapter extends BaseAdapter {

    private Context context;
    private int[] imageIds;
    private ImageView[] images;

    public GalleryAdapter(Context context, int[] ids) {
        this.context = context;
        this.imageIds = ids;
        images = new ImageView[imageIds.length];
    }

    @Override
    public int getCount() {
        return images != null ? images.length : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return images[position];
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 创建有倒影效果的图片
     */
    public void createRefectedBitmap() {
        // 原图片与倒影图片的距离
        int ReflectionGap = 4;

        int i = 0;
        //遍历将所有图片转换成带倒影的图片
        for (int imageId : imageIds) {
            // 将id转换成图片即源图片
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imageId);
            //获取源图片的宽高
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            // 倒影图片
            Matrix matrix = new Matrix();
            // x 水平翻转    y 垂直翻转   1 正常 -1 翻转
            matrix.setScale(1, -1);
            //创建倒影图片
            Bitmap rebitmap = Bitmap.createBitmap(bitmap, 0, height / 2, width, height / 2, matrix, false);

            // 目标图片 合成原图和倒影图片
            Bitmap desBitmap = Bitmap.createBitmap(width, height + height / 2, Config.ARGB_8888);
            // 创建画布
            Canvas canvas = new Canvas(desBitmap);
            // 绘制原图片
            canvas.drawBitmap(bitmap, 0, 0, null);
            // 绘制原图与倒影之间的距离
            Paint defaultpaint = new Paint();

            canvas.drawRect(0, height, width, height + ReflectionGap, defaultpaint);
            // 绘制倒影图片
            canvas.drawBitmap(rebitmap, 0, height + ReflectionGap, null);

            Paint paint = new Paint();
            // 遮罩效果
            paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
            LinearGradient shader = new LinearGradient(0, height, 0, desBitmap.getHeight(), 0x70ffffff, 0x00ffffff, TileMode.CLAMP);
            // 设置着色器 用来绘制颜色,上色的
            paint.setShader(shader);
            canvas.drawRect(0, height, width, desBitmap.getHeight(), paint);

            ImageView iv = new ImageView(context);

            BitmapDrawable drawable = new BitmapDrawable(desBitmap);
            drawable.setAntiAlias(true);// 消除图片锯齿效果,平滑
            iv.setImageDrawable(drawable);

            images[i++] = iv;
        }
    }
}
