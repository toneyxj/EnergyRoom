package com.moxi.energyroom.view.widget.extendView;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.moxi.energyroom.view.widget.BaseView.XJButton;
import com.moxi.energyroom.view.widget.utils.ViewAlphaInterface;
import com.moxi.energyroom.view.widget.utils.ViewAlphaUtil;

public class BackAlphaButton extends XJButton {
    private ViewAlphaInterface viewAlpha;
    public BackAlphaButton(Context context) {
        this(context,null);
    }

    public BackAlphaButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BackAlphaButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        viewAlpha=new ViewAlphaUtil(this);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        viewAlpha.onLayout(changed);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        viewAlpha.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean is=super.onTouchEvent(event);
        viewAlpha.onTouchEvent(event,is);
        return is;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        viewAlpha.onDestory();
    }

}
