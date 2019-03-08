package com.moxi.energyroom.presenter.impl;

import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;

import com.moxi.energyroom.Been.transmitData.BaseData;
import com.moxi.energyroom.listener.NettyMessageCallback;
import com.moxi.energyroom.model.impl.mian.HotSettingModelImp;
import com.moxi.energyroom.model.impl.mian.MainOtherSettingModelImp;
import com.moxi.energyroom.model.impl.mian.SystemTimeModelImpl;
import com.moxi.energyroom.model.impl.mian.TemperatureAndHumidityModelImp;
import com.moxi.energyroom.model.impl.mian.TimeSettingModelimp;
import com.moxi.energyroom.model.inter.main.IHotSettingModel;
import com.moxi.energyroom.model.inter.main.IMainOtherSettingModel;
import com.moxi.energyroom.model.inter.main.ISystemTimeModel;
import com.moxi.energyroom.model.inter.main.ITemperatureAndHumidityModel;
import com.moxi.energyroom.model.inter.main.ITimeSettingModel;
import com.moxi.energyroom.netty.NettyClient;
import com.moxi.energyroom.presenter.inter.IMainAPresenter;
import com.moxi.energyroom.utils.APPLog;
import com.moxi.energyroom.utils.DensityUtil;
import com.moxi.energyroom.view.inter.IMainAView;

import io.netty.channel.ChannelException;

public class MainAPresenterImpl implements IMainAPresenter ,NettyMessageCallback {
    private IMainAView mIMainAView;
    private ISystemTimeModel systemTimeModel;
    private ITemperatureAndHumidityModel temperatureAndHumidityModel;
    private IHotSettingModel hotSettingModel;
    private ITimeSettingModel timeSettingModel;
    private IMainOtherSettingModel mainOtherSettingModel;


    public MainAPresenterImpl(@NonNull IMainAView aIMainAView) {
        mIMainAView = aIMainAView;
        //初始化model
        systemTimeModel = new SystemTimeModelImpl(aIMainAView.getcontext(), this);
        temperatureAndHumidityModel = new TemperatureAndHumidityModelImp(this);
        hotSettingModel = new HotSettingModelImp(this);
        timeSettingModel = new TimeSettingModelimp(this);
        mainOtherSettingModel = new MainOtherSettingModelImp(mIMainAView.getcontext(), this);
        //启动netty连接
        NettyClient.getInstance().reSetingCallBack(this);
        NettyClient.getInstance().startNetty();
    }

    @Override
    public void curSystemTime(String hours, String amOrPm, String data, String sw) {
        mIMainAView.curSystemTime(hours, amOrPm, data, sw);
    }

    @Override
    public void curTemperature(String value) {
        if (null != temperatureAndHumidityModel)
            temperatureAndHumidityModel.curTemperature(value);
        mIMainAView.curTemperature(value + "°C");
    }

    @Override
    public void curHumidity(String value) {
        if (null != temperatureAndHumidityModel)
            temperatureAndHumidityModel.curHumidity(value);
        mIMainAView.curHumidity(value + "%");
    }

    @Override
    public void hotSettingValue(String json) {

    }

    @Override
    public void heatSetingLiangce(boolean isopen, int grade) {

    }

    @Override
    public void heatSetingGradeLiangce(int grade) {

    }

    @Override
    public void heatSetingBeihou(boolean isopen, int grade) {

    }

    @Override
    public void heatSetingGradeBeihou(int grade) {

    }

    @Override
    public void settingTimeGrade(int grade) {
        int setingTime = (grade + 1) * 30 * 60;
        int curT = timeSettingModel.getSettingTime();
        if (curT == setingTime) return;

        timeSettingModel.settingTime(setingTime);
        mIMainAView.settingTimeIndex(grade);
    }

    @Override
    public void curRemainTime(long time) {
        String value = time + "\nmin";
        Spannable WordtoSpan = new SpannableString(value);
        int start = value.indexOf("min");
        int end = start + 3;
        WordtoSpan.setSpan(new AbsoluteSizeSpan(DensityUtil.dip2px(mIMainAView.getcontext(), 18)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mIMainAView.residueTime(WordtoSpan);
}

    @Override
    public void timeOut() {
        mIMainAView.settingTimeIndex(-1);
    }

    @Override
    public void viewIsFinish() {
        systemTimeModel.onDestory();
        temperatureAndHumidityModel.onDestory();
        hotSettingModel.onDestory();
        timeSettingModel.onDestory();
        mainOtherSettingModel.onDestory();
        //取消关联
        NettyClient.getInstance().reSetingCallBack(null);
    }

    @Override
    public void bottomZMD(boolean is) {
        mIMainAView.bottomZMD(is);
    }

    @Override
    public void bottomYDD(boolean is) {
        mIMainAView.bottomYDD(is);
    }

    @Override
    public void bottomJSD(boolean is) {
        mIMainAView.bottomJSD(is);
    }

    @Override
    public void bottomHQS(boolean is) {
        mIMainAView.bottomHQS(is);
    }

    @Override
    public void bottomYB(boolean is) {
        mIMainAView.bottomYB(is);
    }

    @Override
    public void bottomLYYX(boolean is) {
        mIMainAView.bottomLYYX(is);
    }

    @Override
    public void backMessage(BaseData data) {

    }

    @Override
    public void TCPConnectFail(Exception e) {
        if (e instanceof ChannelException){
            //无法与设置IP建立连接！！！
        }
    }

    @Override
    public void TCPConnectSucess() {

    }
}
