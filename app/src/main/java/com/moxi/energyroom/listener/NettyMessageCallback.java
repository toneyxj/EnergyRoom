package com.moxi.energyroom.listener;

import android.view.View;

import com.moxi.energyroom.Been.transmitData.BaseData;

/**
 * 返回
 */
public interface NettyMessageCallback {
    /**
     * 服务器返回信息
     * @param data 装换后的数据类型
     */
    void backMessage(BaseData data);

    /**
     * tcp连接失败
     * @param e
     */
    void TCPConnectFail(Exception e);

    /**
     * tcp连接成功
     */
    void TCPConnectSucess();

}
