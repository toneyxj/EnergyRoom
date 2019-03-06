package com.moxi.energyroom.view.widget.extendView;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.moxi.energyroom.view.widget.utils.ViewAlphaInterface;
import com.moxi.energyroom.view.widget.utils.ViewGroupAlphaUtil;

/**
 * Created by xj on 2018/9/14.
 */

public class AlphaRelatieLayout extends RelativeLayout {
    private ViewAlphaInterface viewAlpha;
    public AlphaRelatieLayout(Context context) {
        this(context,null);
    }

    public AlphaRelatieLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AlphaRelatieLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        viewAlpha=new ViewGroupAlphaUtil(this);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        viewAlpha.onLayout(changed);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean is=super.onTouchEvent(event);
        viewAlpha.onTouchEvent(event,is);
        return is;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        viewAlpha.onDraw(canvas);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        viewAlpha.onDestory();
    }
}
