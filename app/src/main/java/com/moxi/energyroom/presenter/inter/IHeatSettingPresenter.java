package com.moxi.energyroom.presenter.inter;

/**
 * 加热膜控制器
 */
public interface IHeatSettingPresenter {
    /**
     * 热量设置
     * @param orientation 方位
     * @param grade 热量等级--如果等级小于0代表
     */
    void heatSeting(int orientation,int grade);
    /**
     * 热量设置
     * @param orientation 方位
     * @param isOpen 是否开启
     */
    void heatSeting(int orientation,boolean isOpen);

    /**
     * 可开启定时时间
     */
    void canStartTime(boolean is);
}
