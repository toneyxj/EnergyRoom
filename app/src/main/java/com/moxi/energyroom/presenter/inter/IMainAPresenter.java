package com.moxi.energyroom.presenter.inter;

public interface IMainAPresenter {


    /**
     * 热量设定两侧
     * @param isopen 是否打开
     */
    void heatSetingLiangce(boolean isopen);

    /**
     * 热量设定两侧
     * @param grade 等级
     */
    void heatSetingGradeLiangce(int grade);

    /**
     * 热量设定后背
     * @param isopen 是否打开
     */
    void heatSetingBeihou(boolean isopen);

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
     * 加热开关是否可以打开
     * @return
     */
    boolean isOpenSwitch();

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
