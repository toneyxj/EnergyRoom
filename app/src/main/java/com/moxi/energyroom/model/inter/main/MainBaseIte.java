package com.moxi.energyroom.model.inter.main;

import com.moxi.energyroom.Been.transmitData.BaseData;

public interface MainBaseIte {
    /**
     * 关闭调用
     */
    void onDestory();

    /**
     * 发送数据
     * @param baseData
     */
    void backMessage(BaseData baseData);
    /**
     * 重新发送消息
     */
    void reSendMessage();
}
