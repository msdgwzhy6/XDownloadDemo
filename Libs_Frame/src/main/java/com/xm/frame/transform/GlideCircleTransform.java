package com.xm.frame.transform;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * @author: 小民
 * @date: 2017-06-03
 * @time: 12:00
 * @开源地址: https://github.com/2745329043/NewSource
 * @说明: 下载图片进行圆形转换
 */
public class GlideCircleTransform extends BitmapTransformation{

    public GlideCircleTransform(Context context) {
        super(context);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        if(toTransform == null){
            return null;
        }
        int size = Math.min(toTransform.getWidth(),toTransform.getHeight());
        int x = (toTransform.getWidth() - size) / 2;
        int y = (toTransform.getHeight() - size) / 2;
        //剪切源文件
        Bitmap squared = Bitmap.createBitmap(toTransform, x, y, size, size);
        //返回结果
        Bitmap result = pool.get(size,size, Bitmap.Config.ARGB_8888);
        if(result == null){
            result = Bitmap.createBitmap(size,size, Bitmap.Config.ARGB_8888);
        }
        //绘制到Bitmap上
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setShader(new BitmapShader(squared, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        float radius = size / 2f;
        canvas.drawCircle(radius,radius,radius,paint);
        return result;
    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}
