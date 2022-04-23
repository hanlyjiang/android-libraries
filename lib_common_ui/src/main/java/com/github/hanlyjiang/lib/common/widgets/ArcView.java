package com.github.hanlyjiang.lib.common.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.github.hanlyjiang.lib.common.ui.R;

/**
 * 顶部带弧形的 View，可用作背景
 *
 * @author hanlyjiang at 2022/4/18 22:39
 * @version 1.0
 */
public class ArcView extends View {

    private static final String TAG = ArcView.class.getSimpleName();

    private Paint mPaint;
    private Path mPath;
    private PaintFlagsDrawFilter filter;

    private int mArcHeight;
    private int mExceptArcHeight;
    private int mCanvasTranslateY;
    private final int mStrokeWidth = 8;
    private final int mStrokeWidthHalf = mStrokeWidth >> 1;
    private final Rect mArcRect = new Rect();
    private int mArcTopX, mArcTopY;
    /**
     * 可用宽高
     */
    private int aWidth;
    private int mArcColor;

    public ArcView(Context context) {
        super(context);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    protected void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray ts = context.getResources().obtainAttributes(attrs, R.styleable.ArcView);
        mArcColor = ts.getColor(R.styleable.ArcView_arcBgColor, Color.WHITE);
        mExceptArcHeight = ts.getDimensionPixelOffset(R.styleable.ArcView_arcHeight, 100);
        ts.recycle();

        mPaint = new Paint();
        mPath = new Path();
        mPaint.setAntiAlias(true);
//        mPaint.setStrokeCap(Paint.Cap.ROUND);
//        mPaint.setStrokeJoin(Paint.Join.ROUND);
        // 不设置 StrokeWidth 会导致锯齿
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(mArcColor);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        filter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        aWidth = MeasureSpec.getSize(widthMeasureSpec) - getPaddingStart() - getPaddingEnd();
        int aHeight = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();
        int mTotalWidth = MeasureSpec.getSize(widthMeasureSpec);
        int mTotalHeight = MeasureSpec.getSize(heightMeasureSpec);

        setArcHeight(Math.min(mExceptArcHeight, aHeight));

        mArcRect.left = getPaddingStart() + mStrokeWidthHalf;
        mArcRect.right = mTotalWidth - getPaddingEnd() - mStrokeWidthHalf;
        if (mArcHeight > 0) {
            mArcRect.bottom = mTotalHeight - getPaddingBottom() - mStrokeWidthHalf + mCanvasTranslateY;
            mArcRect.top = mArcHeight + getPaddingTop();
            mArcTopX = (aWidth >> 1) + getPaddingStart() + mStrokeWidthHalf;
            mArcTopY = getPaddingTop();
        } else {
            mArcRect.bottom = mTotalHeight - getPaddingBottom() - mStrokeWidthHalf;
            mArcRect.top = getPaddingTop() + mStrokeWidthHalf;
            mArcTopX = (aWidth >> 1) + getPaddingStart() + mStrokeWidthHalf;
            mArcTopY = -mArcHeight + getPaddingTop();
        }
        logPath();
    }

    private void logPath() {
        Log.d(TAG, String.format("Path:(%d,%d),(%d,%d),(%d,%d),(%d,%d),(%d,%d)",
                mArcRect.left, mArcRect.top,
                mArcTopX, mArcTopY, mArcRect.right, mArcRect.top,
                mArcRect.right, mArcRect.bottom,
                mArcRect.left, mArcRect.bottom));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(filter);
        mPath.rewind();
        // P0
        mPath.moveTo(mArcRect.left, mArcRect.top);
        // P1，P2
        mPath.quadTo(mArcTopX, mArcTopY, mArcRect.right, mArcRect.top);
        if (mArcRect.bottom >= mArcRect.top) {
            mPath.lineTo(mArcRect.right, mArcRect.bottom);
            mPath.lineTo(mArcRect.left, mArcRect.bottom);
        }
        mPath.close();
        if (mArcHeight > 0) {
            canvas.translate(0, -mCanvasTranslateY);
        }
        mPaint.setColor(mArcColor);
        canvas.drawPath(mPath, mPaint);
    }

    public void setArcHeight(int arcHeight) {
        // 高度需要乘 2
        this.mArcHeight = arcHeight << 1;
        updateCanvasTranslateY();
    }

    private void updateCanvasTranslateY() {
        // 二次贝塞尔曲线的曲线顶点距离我们View的上边界有距离，我们通过画布平移消除距离,避免曲线顶部溢出到View上边界之上
        // 计算过程：
        // 任意一点曲线的Y = B(t) = (1-t)^2 P0 + 2t(1-t)P1 + t^2P2 (t=[0,1]
        // 曲线最高点为中点，即 t=0.5,
        // 对应的控制点坐标：P0=(-,arcHeight) , P1=(-,0), P2 = (-,archHeight)
        // 由于View坐标的原点在左上角，我们需要对Y进行反转，arcHeight作为Y轴0，
        //      P0=(-,0), P1=(-,archHeight), P2 = (-,0)
        // B(t) = 0.25 * P0 + 1*0.5*P1 + 0.25*P2
        //      = 0 + 0.5 * arcHeight + 0 = arcHeight/2
        mCanvasTranslateY = (Math.abs(mArcHeight) >> 1) - (mStrokeWidth >> 1);
    }

}
