package com.moxi.energyroom;

import android.app.Application;

import com.moxi.energyroom.utils.ToastUtils;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtils.getInstance().initToast(this.getApplicationContext());
    }
}
