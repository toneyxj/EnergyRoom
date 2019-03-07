package com.moxi.energyroom.presenter.inter;

public interface IMainAPresenter {
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
     * 热量设置传输json
     * @param json
     */
    void  hotSettingValue(String json);

    /**
     * 热量设定两侧
     * @param isopen 是否打开
     * @param grade 设置等级
     */
    void heatSetingLiangce(boolean isopen,int grade);

    /**
     * 热量设定两侧
     * @param grade 等级
     */
    void heatSetingGradeLiangce(int grade);

    /**
     * 热量设定后背
     * @param isopen 是否打开
     * @param grade 设置等级
     */
    void heatSetingBeihou(boolean isopen,int grade);

    /**
     * 热量设定后背
     * @param grade 等级
     */
    void heatSetingGradeBeihou(int grade);

    /**
     * 设置加热时间等级
     * @param grade 0/1/2 ：分别代表30min与60min与90min
     */
    void settingTimeGrade(int grade);
    /**
     *当前剩余加热时间分钟数
     */
    void curRemainTime(long time);

    /**
     * 加热时间到
     */
    void timeOut();

    /**
     * 界面关闭调用通知
     */
    void viewIsFinish();

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

}
