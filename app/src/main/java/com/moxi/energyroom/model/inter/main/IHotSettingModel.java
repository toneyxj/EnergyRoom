package com.moxi.energyroom.model.inter.main;

import android.view.ViewGroup;

import com.moxi.energyroom.presenter.inter.IMainAPresenter;

/**
 * 热量设置
 */
public interface IHotSettingModel extends MainBaseIte{

    /**
     * 两侧温度是否开启
     * @param is
     */
    boolean doubleSideIsOpen(boolean is);

    /**
     * 后背是否开启
     * @param is
     */
    boolean  backIsOpen(boolean is);

    /**
     * 设置两侧温度等级
     * @param grade
     */
    void setDoubleSideHotGrade(int grade);

    /**
     * 设置背后温度等级
     * @param grade
     */
    void setbackHotGrade(int grade);

    boolean isCanStartTime();

}
