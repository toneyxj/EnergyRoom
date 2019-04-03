package com.moxi.energyroom.model.impl.mian;

import com.moxi.energyroom.Been.EV_Type;
import com.moxi.energyroom.Been.transmitData.BaseData;
import com.moxi.energyroom.Been.transmitData.TemperatureHumidity;
import com.moxi.energyroom.model.inter.main.ITemperatureAndHumidityModel;
import com.moxi.energyroom.netty.NettyClient;
import com.moxi.energyroom.netty.TimeCounter;
import com.moxi.energyroom.presenter.inter.ITemHumPresenter;

public class TemperatureAndHumidityModelImp implements ITemperatureAndHumidityModel, TimeCounter.TimeCallback {
    private TimeCounter timeCounter;
    private ITemHumPresenter presenter;
    public TemperatureAndHumidityModelImp(ITemHumPresenter presenter){
        this.presenter=presenter;
        timeCounter = new TimeCounter(this);
        initTH();
    }
    private String temp=null;
    private String humidity=null;


//    @Override
    private void curTemperatureAndHumidity(String temp, String hum) {
        this.temp=temp;
        this.humidity=hum;
        initTH();
    }

    @Override
    public String getcurTemperature() {
        return temp;
    }


    @Override
    public String getcurHumidity() {
        return humidity;
    }

    @Override
    public void initTH() {
        presenter.curTemperature((null==temp?"0":temp)+"%");
        presenter.curHumidity((null==humidity?"0":humidity)+"°C");
    }

    @Override
    public void onPase() {
        onDestory();
    }


    @Override
    public void onDestory() {
        timeCounter.stopTimer();
    }

    @Override
    public void backMessage(BaseData baseData) {
        if (baseData instanceof TemperatureHumidity) {
            TemperatureHumidity th = (TemperatureHumidity) baseData;
            curTemperatureAndHumidity(String.valueOf(th.getTem()),String.valueOf(th.getHum()));
        }
    }

    @Override
    public void reSendMessage() {
        timeCounter.startTimer();
    }

    @Override
    public void onTimerCallBack() {
        //发送数据到服务器
        NettyClient.getInstance().sendMessages(new TemperatureHumidity(EV_Type.EV_SENSOR).setOpcode(0));
    }
}
