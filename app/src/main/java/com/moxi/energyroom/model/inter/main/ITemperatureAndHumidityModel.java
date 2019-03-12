package com.moxi.energyroom.model.inter.main;

import com.moxi.energyroom.presenter.inter.IMainAPresenter;

/**
 * 温度与湿度数据类型
 */
public interface ITemperatureAndHumidityModel extends MainBaseIte{
//    /**
//     * 当前温度
//     * @param temp
//     */
//    void curTemperatureAndHumidity(String temp,String hum);

    String getcurTemperature();

    String getcurHumidity();

    /**
     * 初始化显示温度与湿度
     */
    void initTH();

    /**
     * 暂停
     */
    void onPase();

//    /**
//     * 开始
//     */
//    void onStart();
}
