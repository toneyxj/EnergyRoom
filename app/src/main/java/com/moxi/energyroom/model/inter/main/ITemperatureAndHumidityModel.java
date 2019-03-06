package com.moxi.energyroom.model.inter.main;

import com.moxi.energyroom.presenter.inter.IMainAPresenter;

/**
 * 温度与湿度数据类型
 */
public interface ITemperatureAndHumidityModel extends MainBaseIte{
    /**
     * 当前温度
     * @param temp
     */
    void curTemperature(String temp);

    String getcurTemperature();

    /**
     * 当前湿度
     * @param humidity
     */
    void curHumidity(String humidity);
    String getcurHumidity();

    /**
     * 初始化显示温度与湿度
     */
    void initTH();
}
