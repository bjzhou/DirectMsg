package org.bjzhou.directmsg.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonHelper {
    
    public static Object toJson(String data) {
        try {
            JSONObject json = new JSONObject(data);
            return json;
        } catch (JSONException e) {
            try {
                JSONArray json = new JSONArray(data);
                return json;
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }
    
    public static String getString(JSONObject json,String key) {
        try {
            return json.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONArray getJSONArray(JSONObject jsonObject, String string) {
        try {
            return jsonObject.getJSONArray(string);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getJSONObject(JSONArray jsonArray, int i) {
        try {
            return jsonArray.getJSONObject(i);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getJSONObject(JSONObject json, String string) {
        try {
            return json.getJSONObject(string);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static int getInt(JSONObject json, String string) {
        try {
            return json.getInt(string);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

}
