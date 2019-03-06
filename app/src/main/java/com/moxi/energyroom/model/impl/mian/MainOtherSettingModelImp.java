package com.moxi.energyroom.model.impl.mian;

import android.content.Context;

import com.moxi.energyroom.model.inter.main.IMainOtherSettingModel;
import com.moxi.energyroom.presenter.inter.IMainAPresenter;

public class MainOtherSettingModelImp implements IMainOtherSettingModel {
    private IMainAPresenter presenter;
    private Context context;
    public MainOtherSettingModelImp(Context context,IMainAPresenter presenter){
        this.presenter=presenter;
        this.context=context;
        initData();
    }
    private void initData(){

    }
    /**
     * 照明灯
     */
    private boolean floodlight = false;
    /**
     * 阅读灯
     */
    private boolean readlight = false;
    /**
     * 加湿器
     */
    private boolean humidifier = false;
    /**
     * 换气扇
     */
    private boolean ventilator = false;
    /**
     * 氧吧
     */
    private boolean ox = false;
    /**
     * 蓝牙音箱
     */
    private boolean bluetoothSpeaker = false;


    @Override
    public boolean isFloodlight() {
        return floodlight;
    }

    @Override
    public void setFloodlight(boolean floodlight) {
        this.floodlight = floodlight;
    }

    @Override
    public boolean isReadlight() {
        return readlight;
    }

    @Override
    public void setReadlight(boolean readlight) {
        this.readlight = readlight;
    }

    @Override
    public boolean isHumidifier() {
        return humidifier;
    }

    @Override
    public void setHumidifier(boolean humidifier) {
        this.humidifier = humidifier;
    }

    @Override
    public boolean isVentilator() {
        return ventilator;
    }

    @Override
    public void setVentilator(boolean ventilator) {
        this.ventilator = ventilator;
    }

    @Override
    public boolean isOx() {
        return ox;
    }

    @Override
    public void setOx(boolean ox) {
        this.ox = ox;
    }

    @Override
    public boolean isBluetoothSpeaker() {
        return bluetoothSpeaker;
    }

    @Override
    public void setBluetoothSpeaker(boolean bluetoothSpeaker) {
        this.bluetoothSpeaker = bluetoothSpeaker;
    }

    @Override
    public void onDestory() {

    }
}
