package com.moxi.energyroom.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

    public static int getJsonInt(JSONObject object,String tag){
        try {
           return object.getInt(tag);
        } catch (JSONException e) {
            e.printStackTrace();
            return -1;
        }
    }
    public static double getJsonDouble(JSONObject object,String tag){
        try {
           return object.getDouble(tag);
        } catch (JSONException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
