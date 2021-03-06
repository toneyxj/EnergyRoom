package com.moxi.energyroom.Been.transmitData;

import org.json.JSONException;
import org.json.JSONObject;

public class TemperatureHumidity extends BaseData {
    public TemperatureHumidity(int id) {
        super(id);
    }

    /**
     * 湿度
     */
    private float hum = 0;
    /**
     * 温度
     */
    private float tem = 0;


    public float getHum() {
        return hum;
    }

    public TemperatureHumidity setHum(float hum) {
        this.hum = hum;
        return this;
    }

    public float getTem() {
        return tem;
    }

    public TemperatureHumidity setTem(float tem) {
        this.tem = tem;
        return this;
    }

    @Override
    public String toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", getId());
            jsonObject.put("opcode", getOpcode());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @Override
    public String getOnlyValue() {
        return getId() + "|";
    }
}
