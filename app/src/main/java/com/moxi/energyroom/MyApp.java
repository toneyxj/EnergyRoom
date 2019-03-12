package com.moxi.energyroom;

import android.app.Application;

import com.moxi.energyroom.utils.APPLog;
import com.moxi.energyroom.utils.ToastUtils;
import com.tencent.bugly.crashreport.CrashReport;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtils.getInstance().initToast(this.getApplicationContext());
//        CrashReport.initCrashReport(getApplicationContext(), "a89eadc975", APPLog.isOpen);
    }
}
