package com.moxi.energyroom.netty;

import com.moxi.energyroom.Been.transmitData.BaseData;
import com.moxi.energyroom.listener.NettyMessageCallback;

public interface BaseNetty {
    public void startNetty();
     public void reSetingCallBack(NettyMessageCallback callback);
     public void sendMessages(BaseData data);
}
