package com.moxi.energyroom.Been.transmitData;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 其余开关控制变量
 */
public class OherControl extends BaseData{
    public OherControl(int id) {
        super(id);
    }

    /**
     * 1~6分别控制
     */
    private int device=0;

    public int getDevice() {
        return device;
    }

    public OherControl setDevice(int device) {
        this.device = device;
        return this;
    }

    @Override
    public String toJson() {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",getId());
            jsonObject.put("opcode",getOpcode());
            jsonObject.put("device",getDevice());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
    @Override
    public String getOnlyValue() {
        return getId()+"|"+getDevice();
    }
}
