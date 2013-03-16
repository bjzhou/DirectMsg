package org.bjzhou.directmsg.weibo;

import org.bjzhou.directmsg.http.HttpRequestHelper;
import org.bjzhou.directmsg.http.HttpRequestHelper.HttpMethod;
import org.bjzhou.directmsg.utils.AccessTokenHelper;
import org.bjzhou.directmsg.utils.JsonHelper;
import org.json.JSONObject;

import android.content.Context;

public class UnreadApi {
	
	private String accessToken;
	private String uid;

	public UnreadApi(Context context) {
		accessToken = AccessTokenHelper.ReadAccessToken(context);
		uid = AccessTokenHelper.ReadUid(context);
	}
	
	public int getUnread() {
		HttpRequestHelper helper = new HttpRequestHelper(
				Api.UNREAD_COUNT);
		helper.addParameter("access_token", accessToken);
		helper.addParameter("uid", uid);
		String jsonData = helper.excute(HttpMethod.GET);
		
		if (jsonData == null)
			return 0;
		JSONObject json = (JSONObject) JsonHelper.toJson(jsonData);
		return JsonHelper.getInt(json, "dm");
	}
	
	public void clear() {
		HttpRequestHelper helper = new HttpRequestHelper(
				Api.UNREAD_CLEAR);
		helper.addParameter("access_token", accessToken);
		helper.addParameter("type", "dm");
		helper.excute(HttpMethod.GET);
	}

}
