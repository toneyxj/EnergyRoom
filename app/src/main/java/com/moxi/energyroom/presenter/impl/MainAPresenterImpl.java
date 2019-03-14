package com.moxi.energyroom.presenter.impl;

import android.app.Application;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;

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
import com.moxi.energyroom.model.impl.mian.IPAddressModelImp;
import com.moxi.energyroom.model.impl.mian.MainOtherSettingModelImp;
import com.moxi.energyroom.model.impl.mian.SystemTimeModelImpl;
import com.moxi.energyroom.model.impl.mian.TemperatureAndHumidityModelImp;
import com.moxi.energyroom.model.impl.mian.TimeSettingModelimp;
import com.moxi.energyroom.model.inter.main.IHotSettingModel;
import com.moxi.energyroom.model.inter.main.IIPAddressModel;
import com.moxi.energyroom.model.inter.main.IMainOtherSettingModel;
import com.moxi.energyroom.model.inter.main.ISystemTimeModel;
import com.moxi.energyroom.model.inter.main.ITemperatureAndHumidityModel;
import com.moxi.energyroom.model.inter.main.ITimeSettingModel;
import com.moxi.energyroom.netty.NettyClient;
import com.moxi.energyroom.presenter.inter.IHeatSettingPresenter;
import com.moxi.energyroom.presenter.inter.IIPAddressPresenter;
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

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import io.netty.channel.AbstractChannel;
import io.netty.channel.ChannelException;

public class MainAPresenterImpl implements IMainAPresenter, NettyMessageCallback
        , ISystemTimePresenter, ITemHumPresenter, ITimeSettingPresenter, IOtherSettingPresenter
        , IHeatSettingPresenter, IIPAddressPresenter {
    private IMainAView mIMainAView;
    private ISystemTimeModel systemTimeModel;
    private ITemperatureAndHumidityModel temperatureAndHumidityModel;
    private IHotSettingModel hotSettingModel;
    private ITimeSettingModel timeSettingModel;
    private IMainOtherSettingModel mainOtherSettingModel;
    private IIPAddressModel ipAddressModel;
    /**
     * 重新连接
     */
    private boolean reStart = false;

    public MainAPresenterImpl(@NonNull IMainAView aIMainAView) {
        mIMainAView = aIMainAView;
        //初始化model
        systemTimeModel = new SystemTimeModelImpl(aIMainAView.getcontext(), this);
        temperatureAndHumidityModel = new TemperatureAndHumidityModelImp(this);
        hotSettingModel = new HotSettingModelImp(mIMainAView.getcontext(), this);
        timeSettingModel = new TimeSettingModelimp(this);
        mainOtherSettingModel = new MainOtherSettingModelImp(mIMainAView.getcontext(), this);
        ipAddressModel = new IPAddressModelImp(mIMainAView.getcontext(), this);
        //启动netty连接
        NettyClient.getInstance().reSetingCallBack(this);
        ipAddressModel.reGetAddress();
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
    public void heatSetingLiangce(boolean isopen) {
        if (isopen) {
            String hitn = "";
             if (timeSettingModel.isSettingTime()) {
                hitn = "请先设置加热时间！！";
            }else if (!hotSettingModel.doubleSideIsOpen(isopen)){
                 hitn = "请先设置热量等级！！";
             }
            if (hitn.length() > 0) {
                ToastUtils.getInstance().showToastShort(hitn);
                mIMainAView.heatSeting(0,false);
                return;
            }
        }
        hotSettingModel.doubleSideIsOpen(isopen);
    }

    @Override
    public void heatSetingGradeLiangce(int grade) {
        hotSettingModel.setDoubleSideHotGrade(grade);
    }

    @Override
    public void heatSetingBeihou(boolean isopen) {
        if (isopen) {
            String hitn = "";
            if (timeSettingModel.isSettingTime()) {
                hitn = "请先设置加热时间！！";
            }else if (!hotSettingModel.backIsOpen(isopen)){
                hitn = "请先设置热量等级！！";
            }
            if (hitn.length() > 0) {
                ToastUtils.getInstance().showToastShort(hitn);
                mIMainAView.heatSeting(1,false);
                return;
            }
        }
        hotSettingModel.backIsOpen(isopen);
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
    public boolean isOpenSwitch() {
        return false;
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
        timeSettingModel.settingSendTime(-1);
        hotSettingModel.doubleSideIsOpen(false);
        hotSettingModel.backIsOpen(false);
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
        ipAddressModel.onDestory();
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
                APPLog.e("返回数据：" + data.toJson());
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
//                        NettyClient.getInstance().startNetty();
                        ipAddressModel.reGetAddress();
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
    public void heatSeting(int orientation, int grade) {
        mIMainAView.heatSeting(orientation, grade);
    }

    @Override
    public void heatSeting(int orientation, boolean isOpen) {
        mIMainAView.heatSeting(orientation, isOpen);
    }

    @Override
    public void canStartTime(boolean is) {
        if (timeSettingModel.isSettingTime()) {
            if (is){
                timeSettingModel.timeStart();
            }else {
                //关闭倒计时
                timeSettingModel.settingSendTime(-1);
            }
        }
    }


    @Override
    public void curIPAddress(String ip) {
        APPLog.e("获取到ip", ip);
        mIMainAView.removeLodingView();
        NettyClient.getInstance().startNetty(ip);
    }

    @Override
    public void getIPFail(Exception e) {
        mIMainAView.removeLodingView();
        DialogWindowUtils.getInstance().showNormalDialog(mIMainAView.getcontext(), e.getMessage(), new OnDialogClickListener() {
            @Override
            public void onClickButton(int button) {
                ipAddressModel.reGetAddress();
            }
        });
    }

    @Override
    public void startGetIp() {
        mIMainAView.onLodingView("系统初始化中...");
    }
}
