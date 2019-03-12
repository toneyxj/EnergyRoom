package com.moxi.energyroom.presenter.inter;

/**
 * 温湿度控制器
 */
public interface ITemHumPresenter {
    /**
     * 当前温度
     * @param value
     */
    void curTemperature(String value);

    /**
     * 当前湿度
     * @param value
     */
    void curHumidity(String value);

}
