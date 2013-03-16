
package org.bjzhou.directmsg.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import org.bjzhou.directmsg.App;

public class AccessTokenHelper {
    

    public static void SaveAccessToken(Context context, String accessToken) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(App.PREFERENCE_NAME, Context.MODE_APPEND);
        Editor editor = sharedPreferences.edit();
        editor.putString("accessToken", accessToken);
        editor.commit();
    }
    
    public static String ReadAccessToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(App.PREFERENCE_NAME, Context.MODE_APPEND);
        return sharedPreferences.getString("accessToken", null);
    }
    
    public static void SaveUid(Context context,String uid) {
    	SharedPreferences sharedPreferences = context.getSharedPreferences(App.PREFERENCE_NAME, Context.MODE_APPEND);
    	Editor editor = sharedPreferences.edit();
        editor.putString("uid", uid);
        editor.commit();
    }
    
    public static String ReadUid(Context context) {
    	SharedPreferences sharedPreferences = context.getSharedPreferences(App.PREFERENCE_NAME, Context.MODE_APPEND);
    	return sharedPreferences.getString("uid", null);
    }

}
