package com.moxi.energyroom.view.widget.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moxi.energyroom.otherPresenter.BaseUtils;
import com.moxi.energyroom.utils.APPLog;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ViewAlphaUtil implements ViewAlphaInterface {
    private View mainView;
    private boolean closeAlpha = true;

    public ViewAlphaUtil(View mainView) {
        if (mainView == null) {
            throw new RuntimeException("mainView not is null");
        }
        this.mainView = mainView;
        handler = new BaseUtils.ViewHandler(this);
    }

    private BaseUtils.ViewHandler handler;
    private float alpha = 0;
    private boolean isFirst = true;
    private boolean isDown = false;

    @Override
    public void onLayout(boolean changed) {
        initViewColor();
    }

    @Override
    public void initViewColor() {
        alpha = mainView.getAlpha();
    }

    @Override
    public void onTouchEvent(MotionEvent event, boolean back) {
        if (mainView == null) return;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!back && closeAlpha) return;
                clickDown(true);
                if (!back) {
                    handler.sendEmptyMessageDelayed(10, 500);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                clickDown(false);
                break;
            default:
                break;
        }
    }

    private void clickDown(boolean isDown) {
        if (isDown) {
            this.isDown = isDown;
            if (isFirst) {
                mainView.setAlpha((float) (alpha * 0.5));
                isFirst = false;
            }
        } else {
            mainView.setAlpha(alpha);
            isFirst = true;
            this.isDown = false;
        }
    }

    @Override
    public List<View> getAllChildViews(View view) {
        List<View> allchildren = new ArrayList<>();
        if (mainView == null) return allchildren;
        if (view instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) view;
            for (int i = 0; i < vp.getChildCount(); i++) {
                View viewchild = vp.getChildAt(i);
                if (viewchild instanceof TextView) {
                    allchildren.add((TextView) viewchild);
                }
                //再次 调用本身（递归）
                allchildren.addAll(getAllChildViews(viewchild));
            }
        }
        return allchildren;
    }

    @Override
    public void changeTextColor(boolean down) {
    }

    @Override
    public void setCloseAlpha(boolean Alpha) {
        closeAlpha = Alpha;
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (!isDown) {
            initViewColor();
        }
    }

    @Override
    public void onDestory() {
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void handleMessage(Message msg) {
        clickDown(false);
        handler.removeCallbacksAndMessages(null);
    }
}
