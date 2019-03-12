package com.moxi.energyroom.presenter.inter;

/**
 * 加热时间控制器
 */
public interface ITimeSettingPresenter {
    /**
     *当前剩余加热时间分钟数
     */
    void curRemainTime(long time);

    /**
     * 加热时间到
     */
    void timeOut();
    /**
     * 设置加热时间等级
     * @param grade
     */
    void settingGrade(int grade);
}
