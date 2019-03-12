package com.moxi.energyroom.presenter.impl;

import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;

import com.moxi.energyroom.Been.EV_Type;
import com.moxi.energyroom.Been.transmitData.BaseData;
import com.moxi.energyroom.Been.transmitData.HeatFilm;
import com.moxi.energyroom.Been.transmitData.HeatUpTime;
import com.moxi.energyroom.Been.transmitData.OtherControl;
import com.moxi.energyroom.Been.transmitData.TemperatureHumidity;
import com.moxi.energyroom.dialog.DialogWindowUtils;
import com.moxi.energyroom.listener.NettyMessageCallback;
import com.moxi.energyroom.listener.OnDialogClickListener;
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
import com.moxi.energyroom.presenter.inter.IHeatSettingPresenter;
import com.moxi.energyroom.presenter.inter.IMainAPresenter;
import com.moxi.energyroom.presenter.inter.IOtherSettingPresenter;
import com.moxi.energyroom.presenter.inter.ISystemTimePresenter;
import com.moxi.energyroom.presenter.inter.ITemHumPresenter;
import com.moxi.energyroom.presenter.inter.ITimeSettingPresenter;
import com.moxi.energyroom.utils.APPLog;
import com.moxi.energyroom.utils.DensityUtil;
import com.moxi.energyroom.utils.ToastUtils;
import com.moxi.energyroom.view.inter.IBaseView;
import com.moxi.energyroom.view.inter.IMainAView;

import io.netty.channel.AbstractChannel;
import io.netty.channel.ChannelException;

public class MainAPresenterImpl implements IMainAPresenter, NettyMessageCallback
        , ISystemTimePresenter, ITemHumPresenter, ITimeSettingPresenter, IOtherSettingPresenter, IHeatSettingPresenter {
    private IMainAView mIMainAView;
    private ISystemTimeModel systemTimeModel;
    private ITemperatureAndHumidityModel temperatureAndHumidityModel;
    private IHotSettingModel hotSettingModel;
    private ITimeSettingModel timeSettingModel;
    private IMainOtherSettingModel mainOtherSettingModel;
    /**
     * 重新连接
     */
    private boolean reStart = false;

    public MainAPresenterImpl(@NonNull IMainAView aIMainAView) {
        mIMainAView = aIMainAView;
        //初始化model
        systemTimeModel = new SystemTimeModelImpl(aIMainAView.getcontext(), this);
        temperatureAndHumidityModel = new TemperatureAndHumidityModelImp(this);
        hotSettingModel = new HotSettingModelImp(mIMainAView.getcontext(),this);
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
        mIMainAView.curTemperature(value);
    }

    @Override
    public void curHumidity(String value) {
        mIMainAView.curHumidity(value);
    }

    @Override
    public void heatSetingLiangce(boolean isopen, int grade) {
        hotSettingModel.doubleSideIsOpen(isopen, grade);
    }

    @Override
    public void heatSetingGradeLiangce(int grade) {
        hotSettingModel.setDoubleSideHotGrade(grade);
    }

    @Override
    public void heatSetingBeihou(boolean isopen, int grade) {
        hotSettingModel.backIsOpen(isopen, grade);
    }

    @Override
    public void heatSetingGradeBeihou(int grade) {
        hotSettingModel.setbackHotGrade(grade);
    }

    @Override
    public void settingTimeGrade(int grade) {
        if (grade == -1) {
            timeSettingModel.onDestory();
            return;
        }
        timeSettingModel.settingSendTime(grade);
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
        mIMainAView.closeHeat();
    }

    @Override
    public void settingGrade(int grade) {
        mIMainAView.settingTimeIndex(grade);
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
        mainOtherSettingModel.setFloodlight(is);
    }

    @Override
    public void bottomYDD(boolean is) {
        mainOtherSettingModel.setReadlight(is);
    }

    @Override
    public void bottomJSD(boolean is) {
        mainOtherSettingModel.setHumidifier(is);
    }

    @Override
    public void bottomHQS(boolean is) {
        mainOtherSettingModel.setVentilator(is);
    }

    @Override
    public void bottomYB(boolean is) {
        mainOtherSettingModel.setOx(is);
    }

    @Override
    public void bottomLYYX(boolean is) {
        mainOtherSettingModel.setBluetoothSpeaker(is);
    }

    @Override
    public void backMessage(final BaseData data) {
        mIMainAView.getThisHandler().post(new Runnable() {
            @Override
            public void run() {
                temperatureAndHumidityModel.backMessage(data);
                hotSettingModel.backMessage(data);
                timeSettingModel.backMessage(data);
                mainOtherSettingModel.backMessage(data);
            }
        });
    }


    @Override
    public void TCPConnectFail(Exception e) {
        String msg = e.getMessage();
        String content = "连接失败";
        if (msg.contains("failed to connect to")) {
            content = "无法与设置IP建立连接！！！";
        }
        final String finalContent = content;
        mIMainAView.getThisHandler().post(new Runnable() {
            @Override
            public void run() {
                DialogWindowUtils.getInstance().showNormalDialog(mIMainAView.getcontext(), finalContent, new OnDialogClickListener() {
                    @Override
                    public void onClickButton(int button) {
                        reStart = true;
                        NettyClient.getInstance().startNetty();
                    }
                });
            }
        });
        mIMainAView.onConnectFail();
        timeSettingModel.timePause();
        temperatureAndHumidityModel.onPase();
    }

    @Override
    public void TCPConnectSucess() {
        APPLog.e("TCPConnectSucess");
        if (reStart) {

        }
        reStart = false;
        mIMainAView.onConnectSucess();

        temperatureAndHumidityModel.reSendMessage();
        hotSettingModel.reSendMessage();
        timeSettingModel.reSendMessage();
        mainOtherSettingModel.reSendMessage();

    }

    @Override
    public void OtherSetting(boolean isopen, int index) {
        switch (index) {
            case 1:
                mIMainAView.bottomZMD(isopen);
                break;
            case 2:
                mIMainAView.bottomYDD(isopen);
                break;
            case 3:
                mIMainAView.bottomJSD(isopen);
                break;
            case 4:
                mIMainAView.bottomHQS(isopen);
                break;
            case 5:
                mIMainAView.bottomYB(isopen);
                break;
            case 6:
                mIMainAView.bottomLYYX(isopen);
                break;
            default:
                break;
        }
    }

    @Override
    public void heatSeting(int orientation, boolean isOpen, int grade) {
        mIMainAView.heatSeting(orientation, isOpen, grade);
    }
}
