package com.moxi.energyroom.Been.transmitData;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 加热膜开关控制
 */
public class HeatSwitch extends BaseData{
    public HeatSwitch(int id) {
        super(id);
    }

    /**
     * 0 - left&right, 1 - back， 2 - bottom， 3 - other
     */
    private int zone=-1;
    /**
     * 0关，1开
     */
    private int value=-1;

    public int getZone() {
        return zone;
    }

    public HeatSwitch setZone(int zone) {
        this.zone = zone;
        return this;
    }

    public int getValue() {
        return value;
    }

    public HeatSwitch setValue(int value) {
        this.value = value;
        return this;
    }

    @Override
    public String toJson() {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",getId());
            jsonObject.put("opcode",getOpcode());
            jsonObject.put("zone",getZone());
            jsonObject.put("value",getValue());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @Override
    public String getOnlyValue() {
        return getId()+"|"+getZone()+"|"+getValue();
    }
}
