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

    /**
     * 关闭或者打开热量设定
     * @param tag 标识控制的tag
     * @param open 是否打开加热膜加热
     */
    void openOrClose(Object tag,boolean open);
}
