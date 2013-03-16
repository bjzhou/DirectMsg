
package org.bjzhou.directmsg.weibo;

import org.bjzhou.directmsg.App;
import org.bjzhou.directmsg.http.HttpRequestHelper;
import org.bjzhou.directmsg.http.HttpRequestHelper.HttpMethod;
import org.bjzhou.directmsg.utils.AccessTokenHelper;
import org.bjzhou.directmsg.utils.JsonHelper;
import org.json.JSONObject;

import android.content.Context;

public class Authorize {

    public static void authorize(Context context, String username, String password) {
    	
        HttpRequestHelper request = new HttpRequestHelper(Api.OAUTH2_ACCESS_TOKEN);
        request.addParameter("username", username);
        request.addParameter("password", password);
        request.addParameter("client_id", App.APP_KEY);
        request.addParameter("client_secret", App.APP_SECRET);
        request.addParameter("grant_type", "password");
        String result = request.excute(HttpMethod.POST);
        JSONObject json = (JSONObject) JsonHelper.toJson(result);
        
        String token = JsonHelper.getString(json, "access_token");
        String userId = JsonHelper.getString(json, "uid");

        AccessTokenHelper.SaveAccessToken(context, token);
        AccessTokenHelper.SaveUid(context, userId);

    }

}
