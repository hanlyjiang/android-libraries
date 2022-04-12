package com.github.hanlyjiang.lib.common.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.github.hanlyjiang.lib.common.ui.R;

public class ArcView extends View {

    private static final String TAG = ArcView.class.getSimpleName();

    private int arcBgColor;
    private int totalHeight, aHeight;
    private int totalWidth, aWidth;
    private Paint mPaint;
    private Path mPath;

    private int arcHeight;

    private final Rect mBgRect = new Rect();

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
        arcBgColor = ts.getColor(R.styleable.ArcView_arcBgColor, Color.WHITE);
        arcHeight = ts.getDimensionPixelOffset(R.styleable.ArcView_arcHeight, 100);
        ts.recycle();

        mPaint = new Paint();
        mPath = new Path();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        // 不设置StrokeWidth 会导致锯齿
        mPaint.setStrokeWidth(8);
        mPaint.setColor(arcBgColor);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        aWidth = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        aHeight = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();
        totalWidth = MeasureSpec.getSize(widthMeasureSpec);
        totalHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (arcHeight > aHeight << 2) {
            arcHeight = aHeight;
        }
        mBgRect.setEmpty();
        if (arcHeight < aHeight) {
            mBgRect.set(
                    getPaddingLeft(),
                    getPaddingTop() + arcHeight,
                    totalWidth - getPaddingRight(),
                    totalHeight - getPaddingBottom()
            );
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!mBgRect.isEmpty()) {
            canvas.drawRect(mBgRect, mPaint);
        }
        mPath.rewind();
        mPath.moveTo(getPaddingLeft(), arcHeight + getPaddingTop());
        mPath.quadTo(totalWidth >> 1, getPaddingTop(),
                totalWidth - getPaddingRight(), arcHeight + getPaddingTop());
        canvas.drawPath(mPath, mPaint);
    }
}
