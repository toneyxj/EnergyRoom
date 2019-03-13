package com.moxi.energyroom.model.impl.mian;

import android.content.Context;

import com.moxi.energyroom.Been.EV_Type;
import com.moxi.energyroom.Been.transmitData.BaseData;
import com.moxi.energyroom.Been.transmitData.OtherControl;
import com.moxi.energyroom.model.inter.main.IMainOtherSettingModel;
import com.moxi.energyroom.netty.NettyClient;
import com.moxi.energyroom.presenter.inter.IMainAPresenter;
import com.moxi.energyroom.presenter.inter.IOtherSettingPresenter;
import com.moxi.energyroom.utils.SharePreferceUtil;
import com.moxi.energyroom.utils.ToastUtils;

public class MainOtherSettingModelImp implements IMainOtherSettingModel {
    private IOtherSettingPresenter presenter;
    private Context context;

    public MainOtherSettingModelImp(Context context, IOtherSettingPresenter presenter) {
        this.presenter = presenter;
        this.context = context;
        initData();
    }

    private void initData() {
        floodlight=SharePreferceUtil.getInstance(context).getBoolean("floodlight");
        readlight=SharePreferceUtil.getInstance(context).getBoolean("readlight");
        humidifier=SharePreferceUtil.getInstance(context).getBoolean("humidifier");
        ventilator=SharePreferceUtil.getInstance(context).getBoolean("ventilator");
        ox=SharePreferceUtil.getInstance(context).getBoolean("ox");
        bluetoothSpeaker=SharePreferceUtil.getInstance(context).getBoolean("bluetoothSpeaker");
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
        otherControl(1, floodlight);
        SharePreferceUtil.getInstance(context).setCache("floodlight",floodlight);
    }

    @Override
    public boolean isReadlight() {
        return readlight;
    }

    @Override
    public void setReadlight(boolean readlight) {
        this.readlight = readlight;
        otherControl(2, readlight);
        SharePreferceUtil.getInstance(context).setCache("readlight",readlight);
    }

    @Override
    public boolean isHumidifier() {
        return humidifier;
    }

    @Override
    public void setHumidifier(boolean humidifier) {
        this.humidifier = humidifier;
        otherControl(3, humidifier);
        SharePreferceUtil.getInstance(context).setCache("humidifier",humidifier);
    }

    @Override
    public boolean isVentilator() {
        return ventilator;
    }

    @Override
    public void setVentilator(boolean ventilator) {
        this.ventilator = ventilator;
        otherControl(4, ventilator);
        SharePreferceUtil.getInstance(context).setCache("ventilator",ventilator);
    }

    @Override
    public boolean isOx() {
        return ox;
    }

    @Override
    public void setOx(boolean ox) {
        this.ox = ox;
        otherControl(5, ox);
        SharePreferceUtil.getInstance(context).setCache("ox",ox);
    }

    @Override
    public boolean isBluetoothSpeaker() {
        return bluetoothSpeaker;
    }

    @Override
    public void setBluetoothSpeaker(boolean bluetoothSpeaker) {
        this.bluetoothSpeaker = bluetoothSpeaker;
        otherControl(6, bluetoothSpeaker);
        SharePreferceUtil.getInstance(context).setCache("bluetoothSpeaker",bluetoothSpeaker);
    }

    private void otherControl(int device, boolean isopen) {
        NettyClient.getInstance().sendMessages(new OtherControl(EV_Type.EV_MISC)
                .setDevice(device)
                .setOpcode(isopen ? 1 : 0));
    }

    @Override
    public void onDestory() {

    }

    @Override
    public void backMessage(BaseData baseData) {
        if (baseData instanceof OtherControl) {
            OtherControl oc = (OtherControl) baseData;
            boolean is = oc.getState() == 0;
            boolean isopen = oc.getOpcode() == 1;
            String hitn = "";
            presenter.OtherSetting(is?!isopen:isopen, oc.getDevice());
            if (is) {
                switch (oc.getDevice()) {
                    case 1:
                        hitn = "照明灯";
                        break;
                    case 2:
                        hitn = "阅读灯";
                        break;
                    case 3:
                        hitn = "加湿器";
                        break;
                    case 4:
                        hitn = "换气扇";
                        break;
                    case 5:
                        hitn = "氧吧";
                        break;
                    case 6:
                        hitn = "蓝牙音箱";
                        break;
                    default:
                        break;
                }
                hitn += "设置失败！！";
                ToastUtils.getInstance().showToastShort(hitn);
            }
        }
    }

    @Override
    public void reSendMessage() {
        if (isFloodlight()){
            setFloodlight(isFloodlight());
        }
        if (isReadlight()){
            setReadlight(isReadlight());
        }
        if (isHumidifier()){
            setHumidifier(isHumidifier());
        }
        if (isVentilator()){
            setVentilator(isVentilator());
        }
        if (isOx()){
            setOx(isOx());
        }
        if (isBluetoothSpeaker()){
            setBluetoothSpeaker(isBluetoothSpeaker());
        }
    }
}
