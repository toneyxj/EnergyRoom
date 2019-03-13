package com.moxi.energyroom.presenter.inter;

public interface IIPAddressPresenter {
    /**
     * 获取到当前的ip地址
     * @param ip
     */
    void curIPAddress(String ip);

    /**
     * 获取ip地址失败
     * @param e
     */
    void getIPFail(Exception e);

    /**
     * 开始获取服务器IP地址
     */
    void startGetIp();
}
