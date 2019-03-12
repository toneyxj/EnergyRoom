package com.moxi.energyroom.presenter.inter;

/**
 * 系统时间更新
 */
public interface ISystemTimePresenter {
    /**
     * 设置 当前的日期与时间显示接口
     * @param hours 当前时间
     * @param amOrPm PM或者Am
     * @param data 日期
     * @param sw 周几
     */
    void curSystemTime(String hours,String amOrPm,String data,String sw);
}
