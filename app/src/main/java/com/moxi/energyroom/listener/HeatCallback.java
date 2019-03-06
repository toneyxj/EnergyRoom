package com.moxi.energyroom.listener;

/**
 * 热量点击返回监听
 */
public interface HeatCallback {
    /**
     * 点击监听等级
     * @param tag 控件标记
     * @param grade 点击等级
     */
    void onClickObj(Object tag,int grade);
    void openOrClose(Object tag,boolean open);
}
