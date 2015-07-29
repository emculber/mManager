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
import android.util.Log;
import android.view.View;

import com.ultimatumedia.moneymanager.R;

import java.util.Calendar;
import java.util.UUID;

/**
 * TODO: document your custom view class.
 */
public class CircleEqualityProgress extends View {

    private String text = "Default Text";
    private float textSize = 50;
    private float textWidth = 50;
    private float textHeight = 50;
    private int innerCircleColor = 0x0000000;
    private int clockwiseColor = 0x0000000;
    private int counterClockwiseColor = 0x0000000;
    private float padding = 100;
    private float circleThickness = 50;
    private int angleStart = 270;
    private float angleStop = 100;

    private Paint innerCirclePaint;
    private Paint clockwisePaint;
    private Paint counterClockwisePaint;
    private TextPaint textPaint;

    /*private float outerPadding = 100;
    private float radius = 100;
    private float innerPadding = 50;*/

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
        /*centerText format="string"
          colorCounterClockwise format="color"
          colorClockwise format="color"
          padding" format="dimension"*/

        text = a.getString(R.styleable.CircleEqualityProgress_centerText);
        textSize = a.getDimension(R.styleable.CircleEqualityProgress_centerTextSize, textSize);
        innerCircleColor = a.getColor(R.styleable.CircleEqualityProgress_innerCircleColor, innerCircleColor);
        clockwiseColor = a.getColor(R.styleable.CircleEqualityProgress_colorClockwise, clockwiseColor);
        counterClockwiseColor = a.getColor(R.styleable.CircleEqualityProgress_colorCounterClockwise, counterClockwiseColor);;
        padding = a.getDimension(R.styleable.CircleEqualityProgress_padding, padding);
        circleThickness = a.getDimension(R.styleable.CircleEqualityProgress_circleThickness, circleThickness);

        a.recycle();


        innerCirclePaint = new Paint();
        innerCirclePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        innerCirclePaint.setColor(innerCircleColor);
        innerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        clockwisePaint = new Paint();
        clockwisePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        clockwisePaint.setColor(clockwiseColor);
        clockwisePaint.setStyle(Paint.Style.STROKE);
        clockwisePaint.setStrokeWidth(circleThickness);

        counterClockwisePaint = new Paint();
        counterClockwisePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        counterClockwisePaint.setColor(counterClockwiseColor);
        counterClockwisePaint.setStyle(Paint.Style.STROKE);
        counterClockwisePaint.setStrokeWidth(circleThickness);

        textPaint = new TextPaint();
        textPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Paint.Align.LEFT);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {
        textPaint.setTextSize(textSize);
        textPaint.setColor(Color.BLACK);
        textWidth = textPaint.measureText(text);

        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        textHeight = fontMetrics.bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int centerX = getWidth()/2;
        int centerY = getHeight()/2;
        float radius;

        if(centerX > centerY) {
            radius = centerY - (padding*2);
        }else {
            radius = centerX - (padding*2);
        }

        float rectLeft = centerX - radius;
        float rectRight = centerX + radius;
        float rectTop = centerY - radius;
        float rectBottom = centerY + radius;


        RectF boundRect = new RectF(rectLeft, rectTop, rectRight, rectBottom);

        canvas.drawCircle(centerX, centerY, radius, innerCirclePaint);
        canvas.drawArc(boundRect, angleStart, angleStop + progress, true, clockwisePaint);
        canvas.drawArc(boundRect, angleStart, angleStop + progress-360, true, counterClockwisePaint);
        canvas.drawCircle(centerX, centerY, radius - (circleThickness - (circleThickness/2)), innerCirclePaint);
        canvas.drawText(text, centerX - (textWidth/2), centerY+(textHeight/2), textPaint);
        //canvas.drawArc(boundRect, 270, 150-360, true, counterClockwisePaint);
        //canvas.drawArc(boundRect, 270, 150, true, clockwisePaint);
    }

    public void updateView(String text, float newAngle) {
        angleStop = newAngle;
        this.text = text;
        invalidateTextPaintAndMeasurements();
        invalidate();
    }

    float progress = 0;
    float spinSpeed = 1;
    boolean running = false;
    Thread s;
    public void runningMethod() {
        final Runnable r = new Runnable() {
            public void run() {
                running = true;
                long lastSec = 0;
                long lastMin = 0;
                while (progress < 361) {
                    long sec = System.currentTimeMillis() / 100;
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
