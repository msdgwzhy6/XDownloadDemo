package com.xm.simple.update;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.xm.simple.R;

import static com.xm.simple.R.styleable.DownloadButton_background_color;
import static com.xm.simple.R.styleable.DownloadButton_progress_bg_color;
import static com.xm.simple.R.styleable.DownloadButton_progress_color;
import static com.xm.simple.R.styleable.DownloadButton_progress_width;
import static com.xm.simple.R.styleable.DownloadButton_text_color;
import static com.xm.simple.R.styleable.DownloadButton_text_size;


/**
 * 下载试图
 * 作者：小民
 */

public class DownloadButton extends View {
    private Paint mBtnPaint;
    private int mBackgroundColor;
    private Paint mTextPaint;
    private int mTextColor;
    private int mTextFontSize;
    private Paint mProgressPaint;
    private int mProgressWidth;
    private int mProgressColor;
    private int mProgressBgColor;
    private int mCurrLength;
    private Status mStatus = Status.NORMAL;
    public enum Status { NORMAL,START, DOWNLOAD, PAUSE }
    private Animation mRotateAnimotor;
    private ValueAnimator mSizeValueAnimator;
    private String mText;  //如果有自定义文本。显示是这个

    public DownloadButton(Context context) {
        this(context,null);
    }

    public DownloadButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DownloadButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomAttributes(context,attrs);
        initPaint();
        mRotateAnimotor = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
    }

    private void setCustomAttributes(Context context,AttributeSet attrs) {
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.DownloadButton);
        mBackgroundColor = attributes.getColor(DownloadButton_background_color, Color.BLUE);
        mTextFontSize = attributes.getDimensionPixelSize(DownloadButton_text_size,10);
        mTextColor = attributes.getColor(DownloadButton_text_color, Color.WHITE);
        mProgressWidth = attributes.getDimensionPixelSize(DownloadButton_progress_width,15);
        mProgressColor = attributes.getColor(DownloadButton_progress_color, Color.RED);
        mProgressBgColor = attributes.getColor(DownloadButton_progress_bg_color, Color.BLACK);
        attributes.recycle();
    }

    /* 初始化画笔 */
    private void initPaint() {
        mBtnPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBtnPaint.setStyle(Paint.Style.FILL);
        mBtnPaint.setColor(mBackgroundColor);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextFontSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setColor(mProgressBgColor);
        mProgressPaint.setStrokeWidth(mProgressWidth);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        RectF rectF = new RectF(mCurrLength, 0, width - mCurrLength, height);
        switch (mStatus){
            case NORMAL:
            case PAUSE:
                canvas.drawRoundRect(rectF, height / 5, height / 5,mBtnPaint);
                break;
            case START:
            case DOWNLOAD:
                canvas.drawRoundRect(rectF, height / 2, height / 2,mBtnPaint);
                break;
        }
        switch (mStatus){
            case NORMAL:
                updateText(canvas,rectF,"更新");
                break;
            case START:
                updateText(canvas,rectF,"...");
                break;
            case PAUSE:
                updateText(canvas,rectF,"继续下载");
                break;
            case DOWNLOAD:
                mProgressPaint.setColor(mProgressBgColor);
                int radius = (int) (height / 3.8);
                canvas.drawCircle(rectF.centerX(),rectF.centerY(),radius,mProgressPaint);
                mProgressPaint.setColor(mProgressColor);
                RectF progressRect = new RectF(rectF.centerX() - radius, rectF.centerY() - radius, rectF.centerX() + radius, rectF.centerY() + radius);
                canvas.drawArc(progressRect,0,90,false,mProgressPaint);
                break;
        }
    }

    public void setText(String text){
        mText = text;
    }

    private void updateText(Canvas canvas,RectF rectF,String text){
        if(!text.equals("...")){
            if(mText != null){
                text = mText;
            }
        }
        Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
        int baseline = (int) ((rectF.bottom + rectF.top - fontMetrics.bottom  - fontMetrics.top) / 2);
        canvas.drawText(text,rectF.centerX(),baseline,mTextPaint);
    }

    public void start(Status status){
        if(mSizeValueAnimator != null){
            mSizeValueAnimator.cancel();
        }
        mStatus = status;
        switch (status){
            case NORMAL:
            case PAUSE:
                enlarge(getWidth(),getHeight());
                break;
            case START:
                narrow(getWidth(),getHeight());
                break;
            default:
                break;
        }
    }

    private void enlarge(int width,int height){
        DownloadButton.this.clearAnimation();
        mSizeValueAnimator = ValueAnimator.ofInt((width - height) / 2, 0 );
        mSizeValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (Integer) animation.getAnimatedValue();
                mCurrLength = animatedValue;
                postInvalidate();
            }
        });
        mSizeValueAnimator.setDuration(600).start();
    }

    private void narrow(int width,int height){
        mSizeValueAnimator = ValueAnimator.ofInt(0, (width - height) / 2);
        mSizeValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (Integer) animation.getAnimatedValue();
                mCurrLength = animatedValue;
                postInvalidate();
            }
        });
        mSizeValueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mStatus = Status.DOWNLOAD;
                DownloadButton.this.startAnimation(mRotateAnimotor);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mSizeValueAnimator.setDuration(600).start();
    }
}
