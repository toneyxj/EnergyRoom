package com.moxi.energyroom.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.moxi.energyroom.listener.HandlerMessageInterface;
import com.moxi.energyroom.listener.XJOnClickListener;
import com.moxi.energyroom.otherPresenter.BaseUtils;
import com.moxi.energyroom.utils.ActivityManagerUtils;

import butterknife.ButterKnife;

public  abstract class BaseActivity extends Activity implements HandlerMessageInterface{
    private BaseUtils.XJHander hander=new BaseUtils.XJHander(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewAbout();
        setContentView(getContentLayout());
        ButterKnife.bind(this);
        ActivityManagerUtils.getInstance().addActivity(this);
    }
  abstract int getContentLayout();
    abstract void viewAbout();
    @Override
    protected void onDestroy() {
        ActivityManagerUtils.getInstance().finishActivity(this);
        super.onDestroy();
        hander.removeCallbacksAndMessages(null);
    }

    @Override
    public void handleMessage(Message msg) {

    }

    public XJOnClickListener clickListener=new XJOnClickListener() {
        @Override
        public void onclickView(View view) {
            clickView(view);
        }
    };

    abstract void clickView(View view);
}
