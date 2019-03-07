package com.moxi.energyroom.model.impl.mian;

import com.moxi.energyroom.model.inter.main.ITemperatureAndHumidityModel;
import com.moxi.energyroom.presenter.impl.MainAPresenterImpl;
import com.moxi.energyroom.presenter.inter.IMainAPresenter;

public class TemperatureAndHumidityModelImp implements ITemperatureAndHumidityModel {
    private IMainAPresenter presenter;
    public TemperatureAndHumidityModelImp(IMainAPresenter presenter){
        this.presenter=presenter;
        initTH();
    }
    private String temp=null;
    private String humidity=null;

    @Override
    public void curTemperature(String temp) {
        this.temp=temp;
    }

    @Override
    public String getcurTemperature() {
        return temp;
    }

    @Override
    public void curHumidity(String humidity) {
        this.humidity=humidity;
    }

    @Override
    public String getcurHumidity() {
        return humidity;
    }

    @Override
    public void initTH() {
        presenter.curTemperature(null==temp?"0":temp);
        presenter.curHumidity(null==humidity?"0":humidity);
    }

    @Override
    public void onDestory() {

    }
}
