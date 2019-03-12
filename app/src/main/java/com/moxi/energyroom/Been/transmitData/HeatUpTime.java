package com.moxi.energyroom.Been.transmitData;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 加热时间model
 */
public class HeatUpTime extends BaseData{
    public HeatUpTime(int id) {
        super(id);
    }

    /**
     * 设置当前加热时间,毫秒
     */
    private int value=0;

    public int getValue() {
        return value;
    }

    public HeatUpTime setValue(int value) {
        this.value = value;
        return this;
    }

    @Override
   public String toJson() {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",getId());
            jsonObject.put("opcode",getOpcode());
            jsonObject.put("value",getValue());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
    @Override
   public String getOnlyValue() {
        return getId()+"|";
    }
}
