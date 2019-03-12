package com.moxi.energyroom.model.impl.mian;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;

import com.moxi.energyroom.model.inter.main.ISystemTimeModel;
import com.moxi.energyroom.presenter.inter.IMainAPresenter;
import com.moxi.energyroom.view.activity.MainActivity;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SystemTimeModelImpl implements ISystemTimeModel {
    private IMainAPresenter presenter;
    private Context context;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            initData();
        }

    };

    public SystemTimeModelImpl(Context context, IMainAPresenter presenter) {
        this.presenter = presenter;
        this.context = context;
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_TIME_TICK);
        context.registerReceiver(receiver, intentFilter);
        initData();
    }

    @Override
    public void onDestory() {
        context.unregisterReceiver(receiver);
    }

    /**初始化时间设置
     *
     */
    private void initData() {
        long time = System.currentTimeMillis();
        Date date = new Date(time);
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");
        String data=fmt.format(date);
        Calendar calendar=Calendar.getInstance();

        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int minute=calendar.get(Calendar.MINUTE);
        String pmOrAm=hour<=12?"AM":"PM";
        String []arr = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
        String weeks=arr[calendar.get(Calendar.DAY_OF_WEEK)-1]; //1.数组下标从0开始；2.老外的第一天是从星期日开始的

        String m=minute<10?("0"+minute):minute+"";
        String h=hour<10?("0"+hour):hour+"";
        if (null!=presenter)presenter.curSystemTime(h+":"+m,pmOrAm,data,weeks);
    }
}
