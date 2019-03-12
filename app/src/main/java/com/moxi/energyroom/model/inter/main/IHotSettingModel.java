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
    void doubleSideIsOpen(boolean is,int grade);

    /**
     * 后背是否开启
     * @param is
     */
    void  backIsOpen(boolean is,int grade);

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

}
