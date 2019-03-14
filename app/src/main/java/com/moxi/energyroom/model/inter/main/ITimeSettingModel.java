package com.moxi.energyroom.model.inter.main;

import com.moxi.energyroom.presenter.inter.IMainAPresenter;

public interface ITimeSettingModel extends MainBaseIte{

    /**
     * 获得剩余时间秒
     * @return
     */
    int getremainTime();

    /**
     * 暂停时间
     */
    void timePause();

    /**
     * 开始计数
     */
    void timeStart();

    /**
     * 获得设置时间-时间秒
     * @return
     */
    int getSettingTime();

//    /**
//     * 设置加热时间秒
//     * @param totalTime 总时间数
//     * @param curTime 剩余时间数
//     */
//    void settingTime(int totalTime,int curTime);

    /**
     * 设置定时时间
     * @param grade 等级小于0代表重置时间
     */
    void settingSendTime(int grade);
    /**
     * 是否已经设置时间
     * @return true，为已经设置时间
     */
    boolean isSettingTime();
}
