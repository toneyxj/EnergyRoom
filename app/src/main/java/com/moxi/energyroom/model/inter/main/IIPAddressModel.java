package com.moxi.energyroom.model.inter.main;

import android.view.View;

public interface IIPAddressModel extends MainBaseIte {

    /**
     * 获取服务器ip地址
     * @return
     */
    String getIpAddress();

    /**
     * 重新获取服务器ip地址
     */
    void reGetAddress();
}
