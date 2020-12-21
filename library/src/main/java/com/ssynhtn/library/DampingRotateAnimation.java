package com.ssynhtn.library;

import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.Transformation;



public class DampingRotateAnimation extends Animation {

    private static final String TAG = DampingRotateAnimation.class.getSimpleName();

    private static final float epsilon = 0.000001f;

    private static final boolean DEBUG = false;

    private final float dampingRatio;

    private long animationStartTime = -1;
    private long frameTime;

    private float mFromDegrees;
    private float mToDegrees;

    private float mPivotXValue;
    private float mPivotYValue;

    private float mPivotX;
    private float mPivotY;

    public DampingRotateAnimation(float fromDegrees, float toDegrees, long duration) {
        this(fromDegrees, toDegrees, duration, 0.5f, 0.5f, 0.5f);
    }

    /**
     *
     * @param dampingRatio 一个cycle振幅收缩比例
     */
    public DampingRotateAnimation(float fromDegrees, float toDegrees, long duration, float dampingRatio) {
        this(fromDegrees, toDegrees, duration, dampingRatio, 0.5f, 0.5f);
    }

    /**
     *
     * @param dampingRatio 一个cycle振幅收缩比例
     */
    public DampingRotateAnimation(float fromDegrees, float toDegrees, long duration, float dampingRatio, float pivotXValue, float pivotYValue) {
        mPivotXValue = pivotXValue;
        mPivotYValue = pivotYValue;
        this.dampingRatio = dampingRatio;
        this.mFromDegrees = fromDegrees;
        this.mToDegrees = toDegrees;

        setDuration(duration);
        setRepeatCount(INFINITE);
        setInterpolator(new CycleInterpolator(1));
    }



    @Override
    public boolean getTransformation(long currentTime, Transformation outTransformation) {
        if (animationStartTime == -1) {
            animationStartTime = currentTime;
        }
        frameTime = currentTime;
        return super.getTransformation(currentTime, outTransformation);
    }

    // start at middle
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        if (DEBUG) {
            Log.d(TAG, "interpolated time " + interpolatedTime);
        }
        long realElapsedTime = frameTime - animationStartTime;

        if (DEBUG) {
            Log.d(TAG, "frame time " + frameTime + ", animationStartTime " + animationStartTime);
        }
        float cycles = realElapsedTime * 1.0f / getDuration();
        float amp = (mToDegrees - mFromDegrees) / 2.0f;
        float mid = mFromDegrees + amp;
        float damp = animationStartTime == -1 || dampingRatio == 1 ? 1 : (float) Math.pow(dampingRatio, cycles);
        float degrees = mid + amp * damp * interpolatedTime;

        if (DEBUG) {
            Log.d(TAG, "damping: " + Math.pow(dampingRatio, cycles) + ", cycles " + cycles);
        }

        float scale = getScaleFactor();

        if (mPivotX == 0.0f && mPivotY == 0.0f) {
            t.getMatrix().setRotate(degrees);
        } else {
            t.getMatrix().setRotate(degrees, mPivotX * scale, mPivotY * scale);
        }

        if (animationStartTime != -1 && damp < epsilon) {
            cancel();
        }
    }

    @Override
    public void reset() {
        super.reset();

        animationStartTime = -1;
    }



    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mPivotX = resolveSize(RELATIVE_TO_SELF, mPivotXValue, width, parentWidth);
        mPivotY = resolveSize(RELATIVE_TO_SELF, mPivotYValue, height, parentHeight);
    }
}
