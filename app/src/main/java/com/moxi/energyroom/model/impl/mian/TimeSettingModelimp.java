package com.moxi.energyroom.model.impl.mian;

import com.moxi.energyroom.model.inter.main.ITimeSettingModel;
import com.moxi.energyroom.presenter.inter.IMainAPresenter;
import com.moxi.energyroom.utils.TimerUtils;

public class TimeSettingModelimp implements ITimeSettingModel ,TimerUtils.TimeListener {
    private IMainAPresenter presenter;

    public TimeSettingModelimp(IMainAPresenter presenter){
        this.presenter=presenter;
        settingTime(0);
    }
    private int totalTime=0;
    private int currentTime=0;
    private TimerUtils timerUtils;
    @Override
    public int getremainTime() {
        return currentTime;
    }

    @Override
    public int getSettingTime() {
        return totalTime;
    }

    @Override
    public void settingTime(int time) {
        totalTime=time;
        onDestory();
        if (currentTime==0&&totalTime==0){
            if (null!=presenter)presenter.curRemainTime(0);
            return;
        }
        timerUtils=new TimerUtils(TimerUtils.TimerE.DOWN,totalTime,0,this);
        timerUtils.startTimer();
    }

    @Override
    public void onDestory() {
        if (timerUtils!=null)
        timerUtils.stopTimer();
    }

    @Override
    public void cuttentTime(int time) {
        if (time%60==0) {
            if (null != presenter) presenter.curRemainTime(time / 60);
        }
        currentTime=time;
    }

    @Override
    public void TimeEnd() {
        if (null!=presenter)presenter.timeOut();
    }
}
