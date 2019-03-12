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
import com.moxi.energyroom.presenter.inter.IMainAPresenter;
import com.moxi.energyroom.utils.APPLog;
import com.moxi.energyroom.utils.DensityUtil;
import com.moxi.energyroom.utils.ToastUtils;
import com.moxi.energyroom.view.inter.IBaseView;
import com.moxi.energyroom.view.inter.IMainAView;

import io.netty.channel.AbstractChannel;
import io.netty.channel.ChannelException;

public class MainAPresenterImpl implements IMainAPresenter, NettyMessageCallback {
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
    public void heatSetingLiangce(boolean isopen, int grade) {
        NettyClient.getInstance().sendMessages(new HeatFilm(EV_Type.EV_HEATFILM)
                .setZone(0)
                .setValue(grade + 1)
                .setOpcode(isopen ? 1 : 1));
    }

    @Override
    public void heatSetingGradeLiangce(int grade) {
        NettyClient.getInstance().sendMessages(new HeatFilm(EV_Type.EV_HEATFILM)
                .setZone(0)
                .setValue(grade + 1)
                .setOpcode(1));
    }

    @Override
    public void heatSetingBeihou(boolean isopen, int grade) {
        NettyClient.getInstance().sendMessages(new HeatFilm(EV_Type.EV_HEATFILM)
                .setZone(1)
                .setValue(grade + 1)
                .setOpcode(isopen ? 1 : 0));
    }

    @Override
    public void heatSetingGradeBeihou(int grade) {
        NettyClient.getInstance().sendMessages(new HeatFilm(EV_Type.EV_HEATFILM)
                .setZone(1)
                .setValue(grade + 1)
                .setOpcode(1));
    }

    @Override
    public void settingTimeGrade(int grade) {
        if (grade==-1){
            timeSettingModel.onDestory();
            return;
        }
        int setingTime = (grade + 1) * 30 * 60;
        int curT = timeSettingModel.getSettingTime();
        if (curT == setingTime) return;

        timeSettingModel.settingTime(setingTime);

        int min = (grade + 1) * 30;
        NettyClient.getInstance().sendMessages(new HeatUpTime(EV_Type.EV_TIME)
                .setValue(min)
                .setOpcode(1));
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
        otherControl(1,is);
    }

    @Override
    public void bottomYDD(boolean is) {
        mIMainAView.bottomYDD(is);
        otherControl(2,is);
    }

    @Override
    public void bottomJSD(boolean is) {
        mIMainAView.bottomJSD(is);
        otherControl(3,is);
    }

    @Override
    public void bottomHQS(boolean is) {
        mIMainAView.bottomHQS(is);
        otherControl(4,is);
    }

    @Override
    public void bottomYB(boolean is) {
        mIMainAView.bottomYB(is);
        otherControl(5,is);
    }

    @Override
    public void bottomLYYX(boolean is) {
        mIMainAView.bottomLYYX(is);
        otherControl(6,is);
    }

    private void otherControl(int device,boolean isopen){
        NettyClient.getInstance().sendMessages(new OtherControl(EV_Type.EV_MISC)
                .setDevice(device)
                .setOpcode(isopen ? 1 : 0));
    }

    @Override
    public void backMessage(final BaseData data) {
        mIMainAView.getThisHandler().post(new Runnable() {
            @Override
            public void run() {
                String hitn = "";
                if (data instanceof TemperatureHumidity) {
                    TemperatureHumidity th = (TemperatureHumidity) data;

                    curTemperature(String.valueOf(th.getTem()));
                    curHumidity(String.valueOf(th.getHum()));
                } else if (data instanceof HeatFilm) {
                    HeatFilm film = (HeatFilm) data;
                    if (film.getState() == 0) {
                        hitn = "热量设置失败！！";
                    } else {
                        mIMainAView.heatSeting(film.getZone(), film.getOpcode() == 1, film.getValue());
                    }
                } else if (data instanceof HeatUpTime) {
                    HeatUpTime hut = (HeatUpTime) data;
                    if (hut.getState() == 0) {
                        hitn = "加热时间设置失败！！";
                    } else {
                        int va = hut.getValue();
                        int grade = (va / 30) - 1;
                        mIMainAView.settingTimeIndex(grade);
                    }
                } else if (data instanceof OtherControl) {
                    OtherControl oc = (OtherControl) data;
                    boolean is=oc.getState()==0;
                    boolean isopen=oc.getOpcode()==1;
                    switch (oc.getDevice()) {
                        case 1:
                            if (is){
                                hitn="照明灯";
                                break;
                            }
                            mIMainAView.bottomZMD(isopen);
                            break;
                        case 2:
                            if (is){
                                hitn="阅读灯";
                                break;
                            }
                            mIMainAView.bottomYDD(isopen);
                            break;
                        case 3:
                            if (is){
                                hitn="加湿器";
                                break;
                            }
                            mIMainAView.bottomJSD(isopen);
                            break;
                        case 4:
                            if (is){
                                hitn="换气扇";
                                break;
                            }
                            mIMainAView.bottomHQS(isopen);
                            break;
                        case 5:
                            if (is){
                                hitn="氧吧";
                                break;
                            }
                            mIMainAView.bottomYB(isopen);
                            break;
                        case 6:
                            if (is){
                                hitn="蓝牙音箱";
                                break;
                            }
                            mIMainAView.bottomLYYX(isopen);
                            break;
                        default:
                            break;
                    }
                    if (is){
                        hitn+="设置失败！！";
                    }
                }
                if (null != hitn && hitn.equals("")) {
                    ToastUtils.getInstance().showToastShort(hitn);
                }
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
                        NettyClient.getInstance().startNetty();
                    }
                });
            }
        });
    }

    @Override
    public void TCPConnectSucess() {
        APPLog.e("TCPConnectSucess");
    }
}
