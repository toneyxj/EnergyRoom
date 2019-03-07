package com.moxi.energyroom.view.widget.BaseView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.moxi.energyroom.utils.APPLog;

/**
 * 方形的view,最终会吧view变成正方形
 */
public class RectView extends View {
    public RectView(Context context) {
        super(context);
    }

    public RectView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int minimumWidth = getSuggestedMinimumWidth();
        final int minimumHeight = getSuggestedMinimumHeight();
        int width = measureWidth(minimumWidth, widthMeasureSpec);
        int height = measureHeight(minimumHeight, heightMeasureSpec);
        if (width == 0) {
            width = height;
        } else if (height == 0) {
            height = width;
        } else if (width < height) {
            height = width;
        } else if (width > height) {
            width = height;
        }
        setMeasuredDimension(width, height);
    }

    private int measureWidth(int defaultWidth, int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.AT_MOST://wrap
                break;
            case MeasureSpec.EXACTLY://match
                defaultWidth = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                defaultWidth = Math.max(defaultWidth, specSize);
        }
        return defaultWidth;
    }


    private int measureHeight(int defaultHeight, int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                defaultHeight = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                defaultHeight = Math.max(defaultHeight, specSize);

                break;
        }
        return defaultHeight;


    }
}
