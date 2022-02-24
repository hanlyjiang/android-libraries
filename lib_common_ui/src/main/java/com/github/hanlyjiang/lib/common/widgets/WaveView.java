package com.github.hanlyjiang.lib.common.widgets;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import androidx.annotation.Nullable;
import com.github.hanlyjiang.lib.common.ui.R;

public class WaveView extends View {

    private static final String TAG = WaveView.class.getSimpleName();
    private int waveColor;
    private int waveBgColor;
    private int waveColumnCount;
    private int waveAnimationDuration;
    private int totalHeight, aHeight;
    private int totalWidth, aWidth;
    private Paint mPaint;
    private Path mPath;
    private float[] waveXIndex;
    private int phase = 180;
    private ObjectAnimator phaseAnimator;
    private final LinearInterpolator linearInterpolator = new LinearInterpolator();
    private float columnWidth;

    private final Rect mBgRect = new Rect();

    public WaveView(Context context) {
        super(context);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    protected void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray ts = context.getResources().obtainAttributes(attrs, R.styleable.WaveView);
        waveColor = ts.getColor(R.styleable.WaveView_waveColor, Color.WHITE);
        waveColumnCount = ts.getInteger(R.styleable.WaveView_waveColumnCount, 3);
        if (ts.hasValue(R.styleable.WaveView_waveBackgroundColor)) {
            waveBgColor = ts.getColor(R.styleable.WaveView_waveBackgroundColor, Color.WHITE);
        }
        waveAnimationDuration = ts.getInteger(R.styleable.WaveView_waveAnimationDuration, 600);
        ts.recycle();

        mPaint = new Paint();
        mPath = new Path();
//        mPath.setFillType(Path.FillType.EVEN_ODD);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(waveColor);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        aWidth = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        aHeight = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();
        totalWidth = MeasureSpec.getSize(widthMeasureSpec);
        totalHeight = MeasureSpec.getSize(heightMeasureSpec);
        mBgRect.set(getPaddingLeft(), getPaddingTop(),
                totalWidth - getPaddingRight(),
                totalHeight - getPaddingBottom());
        calcDrawInfo(aHeight, aWidth);
        if (phaseAnimator != null) {
            phaseAnimator.cancel();
        }
        phaseAnimator = ObjectAnimator.ofInt(this, "phase", 0, 180);
        phaseAnimator.setDuration(waveAnimationDuration);
        phaseAnimator.setRepeatCount(ValueAnimator.INFINITE);
        phaseAnimator.setRepeatMode(ValueAnimator.RESTART);
        phaseAnimator.setInterpolator(linearInterpolator);
        phaseAnimator.start();
    }

    private void calcDrawInfo(int height, int width) {
        Log.d(TAG, String.format("totalHeight=%d,totalWidth=%d", height, width));
        columnWidth = width / (waveColumnCount * 2f - 1);
        mPaint.setStrokeWidth(columnWidth);
        // 圆角
        aHeight = (int) (aHeight - columnWidth);
        waveXIndex = new float[waveColumnCount];
        waveXIndex[0] = columnWidth / 2;
        for (int i = 0; i < waveColumnCount - 1; i++) {
            waveXIndex[i + 1] += waveXIndex[i] + columnWidth * 2;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int A = aHeight / 2;
        float w = 1;
        if (waveBgColor != 0 && !mBgRect.isEmpty()) {
            mPaint.setColor(waveBgColor);
            mPaint.setStrokeWidth(0);
            canvas.drawRect(mBgRect, mPaint);
            mPaint.setColor(waveColor);
            mPaint.setStrokeWidth(columnWidth);
        }
        canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop() + (columnWidth / 2));
        for (float x : waveXIndex) {
            float y = (int) sin(A, w, x, phase, 0);
            Log.d(TAG, String.format("%.2f,%.2f", x, y));
            mPath.rewind();
            mPath.moveTo(x, A - y);
            mPath.lineTo(x, A + y);
            canvas.drawPath(mPath, mPaint);
        }
        canvas.restore();
    }

    /**
     * 正弦曲线可表示为y=Asin(ωx+φ)+k，定义为函数y=Asin(ωx+φ)+k在直角坐标系上的图象，其中sin为正弦符号，
     * x是直角坐标系x轴上的数值，y是在同一直角坐标系上函数对应的y值，k、ω和φ是常数（k、ω、φ∈R且ω≠0）
     *
     * @param A A——振幅，当物体作轨迹符合正弦曲线的直线往复运动时，其值为行程的1/2。
     * @param w (ωx+φ)——相位，反映变量y所处的状态。ω——角速度， 控制正弦周期(单位弧度内震动的次数)。
     * @param x X 坐标
     * @param p φ——初相，x=0时的相位；反映在坐标系上则为图像的左右移动。
     * @param k k——偏距，反映在坐标系上则为图像的上移或下移。
     * @return
     */
    public double sin(int A, float w, float x, float p, int k) {
        return A * Math.sin(Math.toRadians(w * x + p)) + k;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
        invalidate();
    }
}
