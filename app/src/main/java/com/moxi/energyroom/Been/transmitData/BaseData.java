package com.moxi.energyroom.Been.transmitData;

import com.moxi.energyroom.Been.EV_Type;
import com.moxi.energyroom.utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 基本控制数据类型
 */
public abstract class BaseData {
    /**
     * 数据类型id
     */
    private int id;
    /**
     * 数据返回的状态值
     */
    private int state = -1;
    /**
     * 最近调用时间
     */
    private long curTime = 0;
    /**
     * 控制开关
     */
    private int opcode = -1;
    /**
     * 数据发送到服务器开始时间
     */
    private long seendMessageTime;

    public BaseData(int id) {
        this.id = id;
        setCurTime();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public int getOpcode() {
        return opcode;
    }

    public BaseData setOpcode(int opcode) {
        this.opcode = opcode;
        return this;
    }

    public BaseData setState(int state) {
        this.state = state;
        return this;
    }

    public void setCurTime() {
        this.curTime = System.currentTimeMillis();
    }

    public long getSeendMessageTime() {
        return seendMessageTime;
    }

    public void setSeendMessageTime() {
        this.seendMessageTime = System.currentTimeMillis();
    }

    /**
     * 把类装换称json对象传输给服务器
     */
    public abstract String toJson();

    /**
     * 获得字符串唯一标识，依此来判断请求model的唯一性
     *
     * @return
     */
    public abstract String getOnlyValue();

    /**
     * 把服务器返回json字符串装换成具体类
     *
     * @param json 数据类型字符串
     */
    public static BaseData buildModel(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        int id = jsonObject.getInt("id");
        BaseData data = null;
        int opcode = JsonUtils.getJsonInt(jsonObject, "opcode");
        int state = JsonUtils.getJsonInt(jsonObject, "state");
        int value = -1;
        int zone = -1;
        switch (id) {
            case EV_Type.EV_SENSOR:
                double hum = JsonUtils.getJsonDouble(jsonObject, "hum");
                double tem = JsonUtils.getJsonDouble(jsonObject, "tem");
                data = new TemperatureHumidity(id)
                        .setHum((float) hum)
                        .setTem((float) tem)
                        .setOpcode(opcode)
                        .setState(state);
                break;
            case EV_Type.EV_HEATFILM:
                value = JsonUtils.getJsonInt(jsonObject, "value");
                zone = JsonUtils.getJsonInt(jsonObject, "zone");
                data = new HeatFilm(id)
                        .setValue(value)
                        .setZone(zone)
                        .setOpcode(opcode)
                        .setState(state);
                break;
            case EV_Type.EV_TIME:
                value = JsonUtils.getJsonInt(jsonObject, "value");
                data = new HeatUpTime(id)
                        .setValue(value)
                        .setOpcode(opcode)
                        .setState(state);
                break;
            case EV_Type.EV_MISC:
                int device = JsonUtils.getJsonInt(jsonObject, "device");
                data = new OtherControl(id)
                        .setDevice(device)
                        .setOpcode(opcode)
                        .setState(state);
                break;
            case EV_Type.EV_FILMSWITCH:
                value = JsonUtils.getJsonInt(jsonObject, "value");
                zone = JsonUtils.getJsonInt(jsonObject, "zone");
                data = new HeatSwitch(id)
                        .setZone(zone)
                        .setValue(value)
                        .setOpcode(opcode)
                        .setState(state);
                break;
            default:
                break;
        }
        return data;
    }
}
