package com.moxi.energyroom.presenter.inter;

/**
 * 加热膜控制器
 */
public interface IHeatSettingPresenter {
    /**
     * 热量设置
     * @param orientation 方位
     * @param isOpen 是否开启
     * @param grade 热量等级
     */
    void heatSeting(int orientation,boolean isOpen,int grade);
}
