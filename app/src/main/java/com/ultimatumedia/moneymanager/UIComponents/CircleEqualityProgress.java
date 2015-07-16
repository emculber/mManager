package com.ultimatumedia.moneymanager.UIComponents;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.ultimatumedia.moneymanager.R;

import java.util.Calendar;
import java.util.UUID;

/**
 * TODO: document your custom view class.
 */
public class CircleEqualityProgress extends View {
    //Delete--------------------------------------------------------
    private String mExampleString; // TODO: use a default from R.string...
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;
    //Delete--------------------------------------------------------

    private float outerPadding = 100;
    private float radius = 100;
    private float innerPadding = 50;

    public CircleEqualityProgress(Context context) {
        super(context);
        init(null, 0);
    }

    public CircleEqualityProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CircleEqualityProgress(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CircleEqualityProgress, defStyle, 0);

        mExampleString = a.getString(
                R.styleable.CircleEqualityProgress_exampleString);
        mExampleColor = a.getColor(
                R.styleable.CircleEqualityProgress_exampleColor,
                mExampleColor);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mExampleDimension = a.getDimension(
                R.styleable.CircleEqualityProgress_exampleDimension,
                mExampleDimension);

        if (a.hasValue(R.styleable.CircleEqualityProgress_exampleDrawable)) {
            mExampleDrawable = a.getDrawable(
                    R.styleable.CircleEqualityProgress_exampleDrawable);
            mExampleDrawable.setCallback(this);
        }

        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {
        mTextPaint.setTextSize(mExampleDimension);
        mTextPaint.setColor(mExampleColor);
        mTextWidth = mTextPaint.measureText(mExampleString);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        // Draw the text.
        canvas.drawText(mExampleString,
                paddingLeft + (contentWidth - mTextWidth) / 2,
                paddingTop + (contentHeight + mTextHeight) / 2,
                mTextPaint);

        // Draw the example drawable on top of the text.
        if (mExampleDrawable != null) {
            mExampleDrawable.setBounds(paddingLeft, paddingTop,
                    paddingLeft + contentWidth, paddingTop + contentHeight);
            mExampleDrawable.draw(canvas);
        }

        Paint mTextPaint2 = new TextPaint();
        mTextPaint2.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint2.setARGB(255, 21, 125, 200);

        canvas.drawCircle(getWidth()/2, getHeight()/2,radius + innerPadding, mTextPaint2);
        canvas.drawCircle(getWidth()/2, getHeight()/2,radius, mTextPaint);

        final TypedArray styledAttributes = getContext().getTheme().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        int actionBarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        int margLeft = 100;
        int margRight = 100;
        int margTop = 100;
        int margBottom = 100;
        int strokeWidth = 25;

        //int screenHeight = canvas.getHeight();
        //int screenWidth = canvas.getWidth();

        //int radius = (((screenWidth - margLeft) - margRight) / 2);
        //int centerX = margLeft + tradius;
        //int centerY = (((screenHeight - margBottom) - tradius) - (strokeWidth / 2)) - actionBarSize;

        int frameHeight = getHeight();;
        int frameWidth = getWidth();;

        int radius = (((frameWidth - margLeft) - margRight) / 2);
        int centerX = getWidth()/2;
        int centerY = getHeight()/2;

        int rectLeft = centerX - radius;
        int rectRight = centerX + radius;
        int rectTop = centerY - radius;
        int rectBottom = centerY + radius;

        Paint progressBar = new Paint();
        RectF rectF = new RectF(rectLeft, rectTop, rectRight, rectBottom);
        progressBar.setColor(Color.GREEN);
        progressBar.setStyle(Paint.Style.STROKE);
        progressBar.setStrokeWidth(strokeWidth);
        progressBar.setFlags(progressBar.ANTI_ALIAS_FLAG);

        Paint progressBar2 = new Paint();
        RectF rectF2 = new RectF(rectLeft, rectTop, rectRight, rectBottom);
        progressBar2.setColor(Color.BLUE);
        progressBar2.setStyle(Paint.Style.STROKE);
        progressBar2.setStrokeWidth(strokeWidth);
        progressBar2.setFlags(progressBar.ANTI_ALIAS_FLAG);

        Paint Background = new Paint();
        Background.setColor(Color.GRAY);
        Background.setStyle(Paint.Style.FILL_AND_STROKE);
        Background.setStrokeWidth(strokeWidth);
        // Background.setShader(new LinearGradient(0, 0, 0, getHeight(), Color.BLACK, Color.WHITE, Shader.TileMode.MIRROR));
        Background.setFlags(Background.ANTI_ALIAS_FLAG);

        canvas.drawCircle(centerX, centerY, radius, Background);
        canvas.drawArc(rectF, 270, progress, true, progressBar);
        canvas.drawArc(rectF2, 270, -180, true, progressBar2);
        canvas.drawCircle(centerX, centerY, radius - (strokeWidth), Background);
        runingMethod();
    }

    /**
     * Gets the example string attribute value.
     *
     * @return The example string attribute value.
     */
    public String getExampleString() {
        return mExampleString;
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
    public void setExampleString(String exampleString) {
        mExampleString = exampleString;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
    public int getExampleColor() {
        return mExampleColor;
    }

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
    public void setExampleColor(int exampleColor) {
        mExampleColor = exampleColor;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    public float getExampleDimension() {
        return mExampleDimension;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
    public void setExampleDimension(float exampleDimension) {
        mExampleDimension = exampleDimension;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    public Drawable getExampleDrawable() {
        return mExampleDrawable;
    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    public void setExampleDrawable(Drawable exampleDrawable) {
        mExampleDrawable = exampleDrawable;
    }

    float progress = 0;
    float spinSpeed = 10;
    boolean running = false;
    Thread s;
    private void runingMethod() {
        final Runnable r = new Runnable() {
            public void run() {
                running = true;
                long lastSec = 0;
                long lastMin = 0;
                while (progress < 361) {
                    long sec = System.currentTimeMillis() / 1000;
                    if (sec != lastSec) {
                        progress += spinSpeed;
                        lastSec = sec;
                    }
                    postInvalidate();
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (progress == 360) progress = 0;
                }
                running = false;
            }
        };

        if (!running) {
            progress = 0;
            s = new Thread(r);
            s.start();
        }
    }
}
