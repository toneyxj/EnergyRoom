package com.moxi.energyroom.model.inter.main;

import com.moxi.energyroom.presenter.inter.IMainAPresenter;

/**
 * 主界面其余操作接口
 */
public interface IMainOtherSettingModel extends MainBaseIte{

     boolean isFloodlight() ;

     void setFloodlight(boolean floodlight);

     boolean isReadlight() ;

     void setReadlight(boolean readlight);

     boolean isHumidifier();

     void setHumidifier(boolean humidifier);

     boolean isVentilator();

     void setVentilator(boolean ventilator);

     boolean isOx();

     void setOx(boolean ox);

     boolean isBluetoothSpeaker();

     void setBluetoothSpeaker(boolean bluetoothSpeaker);
}