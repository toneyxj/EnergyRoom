package com.moxi.energyroom.view.inter;

import android.content.Context;
import android.text.Spannable;

public interface IMainAView extends IBaseView{
    /**
     * 设置 当前的日期与时间显示接口
     * @param hours 当前时间
     * @param amOrPm PM或者Am
     * @param data 日期
     * @param sw 周几
     */
    void curSystemTime(String hours,String amOrPm,String data,String sw);

    /**
     * 当前温度
     * @param value
     */
    void curTemperature(String value);

    /**
     * 当前湿度
     * @param value
     */
    void curHumidity(String value);

    /**
     * 热量设置
     * @param orientation 方位
     * @param grade 热量等级
     */
    void heatSeting(int orientation,int grade);
    /**
     * 热量设置
     * @param orientation 方位
     * @param isOpen 是否开启
     */
    void heatSeting(int orientation,boolean isOpen);

    /**
     * 加热剩余时间
     * @param time 时间值
     */
    void residueTime(Spannable time);

    /**
     * 设置剩余时间按钮为选中状态
     * @param index -1等于时间到
     */
    void settingTimeIndex(int index);

    /**
     * 关闭加热膜
     */
    void closeHeat();

    /**
     * 照明灯
     * @param is 是否开启
     */
    void bottomZMD(boolean is);
    /**
     * 阅读灯
     * @param is 是否开启
     */
    void bottomYDD(boolean is);
    /**
     * 加湿灯
     * @param is 是否开启
     */
    void bottomJSD(boolean is);
    /**
     * 换气扇
     * @param is 是否开启
     */
    void bottomHQS(boolean is);
    /**
     * 氧吧
     * @param is 是否开启
     */
    void bottomYB(boolean is);
    /**
     * 蓝牙音箱
     * @param is 是否开启
     */
    void bottomLYYX(boolean is);

    /**
     * 与服务器连接成功
     */
    void onConnectSucess();
    /**
     * 与服务连接失败
     */
    void onConnectFail();

}
