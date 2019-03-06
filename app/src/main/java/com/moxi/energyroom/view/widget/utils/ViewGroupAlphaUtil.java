package com.moxi.energyroom.view.widget.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moxi.energyroom.otherPresenter.BaseUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xj on 2018/9/14.
 */

public class ViewGroupAlphaUtil implements ViewAlphaInterface {
    private View mainView;
    private boolean closeAlpha = true;

    public ViewGroupAlphaUtil(View mainView) {
        if (mainView == null) {
            throw new RuntimeException("mainView not is null");
        }
        this.mainView = mainView;
        handler = new BaseUtils.ViewHandler(this);
    }


    private BaseUtils.ViewHandler handler;
    private List<View> views = new ArrayList<>();
    private List<Float> alphas = new ArrayList<>();
    private boolean isFirst = true;
    private boolean isDown = false;

    @Override
    public void onLayout(boolean changed) {
        initViewColor();
    }

    @Override
    public void initViewColor() {
        if (views != null) views.clear();
        if (alphas != null) alphas.clear();
        if (mainView instanceof ViewGroup) {
            views = getAllChildViews(mainView);
            for (int i = 0; i < views.size(); i++) {
                alphas.add(views.get(i).getAlpha());
            }
        } else if (mainView instanceof TextView) {
            views.add((TextView) mainView);
            alphas.add(mainView.getAlpha());
        }
    }

    @Override
    public void onTouchEvent(MotionEvent event, boolean back) {
        if (mainView == null) return;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!back && closeAlpha) return;
                clickDown(true);
                if (!back) {
                    handler.sendEmptyMessageDelayed(10, 100);
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
//                mainView.setAlpha(0.95f);
                changeTextColor(true);
                isFirst = false;
            }
        } else {
//            mainView.setAlpha(1f);
            changeTextColor(false);
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
                if (viewchild instanceof TextView||viewchild instanceof ImageView) {
                    allchildren.add(viewchild);
                }
                //再次 调用本身（递归）
                allchildren.addAll(getAllChildViews(viewchild));
            }
        }
        return allchildren;
    }

    @Override
    public void changeTextColor(boolean down) {
        if (views == null || mainView == null || views.size() != alphas.size()) return;
        int size = views.size();
        for (int i = 0; i < size; i++) {
            View dV= views.get(i);
            if (dV.getVisibility()!=View.VISIBLE)return;
            if (down) {
                dV.setAlpha((float) (alphas.get(i)*0.6));
            } else {
                dV.setAlpha(alphas.get(i));
            }
        }
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
        views.clear();
        alphas.clear();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void handleMessage(Message msg) {
        clickDown(false);
        handler.removeCallbacksAndMessages(null);
    }
}
