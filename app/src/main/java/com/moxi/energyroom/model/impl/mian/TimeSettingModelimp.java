package com.moxi.energyroom.model.impl.mian;

import com.moxi.energyroom.Been.EV_Type;
import com.moxi.energyroom.Been.transmitData.BaseData;
import com.moxi.energyroom.Been.transmitData.HeatUpTime;
import com.moxi.energyroom.model.inter.main.ITimeSettingModel;
import com.moxi.energyroom.netty.NettyClient;
import com.moxi.energyroom.presenter.inter.IMainAPresenter;
import com.moxi.energyroom.presenter.inter.ITimeSettingPresenter;
import com.moxi.energyroom.utils.TimerUtils;
import com.moxi.energyroom.utils.ToastUtils;

public class TimeSettingModelimp implements ITimeSettingModel ,TimerUtils.TimeListener {
    private ITimeSettingPresenter presenter;

    public TimeSettingModelimp(ITimeSettingPresenter presenter){
        this.presenter=presenter;
        settingTime(0,0);
    }
    private int totalTime=-1;
    private int middleTime=-1;
    private int currentTime=-1;
    private TimerUtils timerUtils;
    @Override
    public int getremainTime() {
        return currentTime;
    }

    @Override
    public void timePause() {
        onDestory();
    }

    /**
     * 启动开始定时
     */
    @Override
    public void timeStart() {
        startTime( this.currentTime);
    }
    @Override
    public int getSettingTime() {
        return totalTime;
    }

    public void settingTime(int totalTime, int curTime) {
        this.currentTime=curTime;
        this.totalTime=totalTime;
        cuttentTime(this.currentTime);

        presenter.settingGrade((totalTime/30)-1);
    }

    @Override
    public void settingSendTime(int grade) {
        //小于0代表重置时间
        if (grade<0){
            settingTime(0,0);
            return;
        }
        int setingTime = (grade + 1) * 30 ;
        if (middleTime == setingTime) return;
        middleTime=setingTime;
        NettyClient.getInstance().sendMessages(new HeatUpTime(EV_Type.EV_TIME)
                .setValue(middleTime)
                .setOpcode(1));
    }

    @Override
    public boolean isSettingTime() {
        return currentTime>0&&totalTime>0;
    }

    private void startTime(int startTime){
        onDestory();
        if (currentTime==0&&totalTime==0){
            if (null!=presenter)presenter.curRemainTime(0);
            return;
        }
        timerUtils=new TimerUtils(TimerUtils.TimerE.DOWN,startTime,0,this);
        timerUtils.startTimer();
    }

    @Override
    public void onDestory() {
        if (timerUtils!=null)
        timerUtils.stopTimer();
    }

    @Override
    public void backMessage(BaseData baseData) {
        if (baseData instanceof HeatUpTime) {
            HeatUpTime hut = (HeatUpTime) baseData;
            if (hut.getState() == 0) {
                ToastUtils.getInstance().showToastShort("加热时间设置失败！！");
                presenter.settingGrade((totalTime/30)-1);
            } else {
                this.totalTime=middleTime;
                if (baseData.getOpcode()==1) {
                    int va = hut.getValue();
                    settingTime(totalTime, va);
                }else {

                }
            }
        }
    }

    @Override
    public void reSendMessage() {
        if (currentTime<=0&&totalTime<=0)return;
        NettyClient.getInstance().sendMessages(new HeatUpTime(EV_Type.EV_TIME)
                .setValue(currentTime<=0?totalTime:currentTime)
                .setOpcode(1));
    }

    @Override
    public void cuttentTime(int time) {
//        if (time%60==0) {
//            if (null != presenter) presenter.curRemainTime(time / 60);
//        }
//        if (time%60==0) {
            if (null != presenter) presenter.curRemainTime(time );
//        }
        currentTime=time;
    }

    @Override
    public void TimeEnd() {
        if (null!=presenter)presenter.timeOut();
    }
}
