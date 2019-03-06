package com.moxi.energyroom.model.inter.main;

import com.moxi.energyroom.presenter.inter.IMainAPresenter;

public interface ITimeSettingModel extends MainBaseIte{

    /**
     * 获得剩余时间秒
     * @return
     */
    int getremainTime();

    /**
     * 获得设置时间-时间秒
     * @return
     */
    int getSettingTime();

    /**
     * 设置加热时间秒
     * @param time 加热时间
     */
    void settingTime(int time);

}
