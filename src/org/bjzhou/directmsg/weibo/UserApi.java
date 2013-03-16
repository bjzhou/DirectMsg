package org.bjzhou.directmsg.weibo;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.bjzhou.directmsg.App;
import org.bjzhou.directmsg.bean.SearchUser;
import org.bjzhou.directmsg.bean.User;
import org.bjzhou.directmsg.http.HttpRequestHelper;
import org.bjzhou.directmsg.http.HttpRequestHelper.HttpMethod;
import org.bjzhou.directmsg.utils.AccessTokenHelper;
import org.bjzhou.directmsg.utils.FileHelper;
import org.bjzhou.directmsg.utils.JsonHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

public class UserApi {

	private String accessToken;
	private JSONObject json;
	private boolean readFromLocal = true;

	public UserApi(Context context) {
		accessToken = AccessTokenHelper.ReadAccessToken(context);
	}
	
	public void setReadFromLocal(boolean readFromLocal) {
		this.readFromLocal = readFromLocal;
	}

	public List<User> friends(String uid, int count, int cursor) {
		String jsonData;
		if (FileHelper.getFileSize(App.getSingleton().UserListCache + cursor/200) > 1024 && readFromLocal) {
			jsonData = FileHelper.fromFile(App.getSingleton().UserListCache + cursor/200);
		} else {
			HttpRequestHelper helper = new HttpRequestHelper(
					Api.FRIENDS_LIST_BYID);
			helper.addParameter("access_token", accessToken);
			helper.addParameter("uid", uid);
			helper.addParameter("count", String.valueOf(count));
			helper.addParameter("cursor", String.valueOf(cursor));
			jsonData = helper.excute(HttpMethod.GET);
			FileHelper.saveFile(jsonData, App.getSingleton().UserListCache
					+ cursor/200);
		}

		if (jsonData == null)
			return null;
		json = (JSONObject) JsonHelper.toJson(jsonData);
		JSONArray jsonArray = JsonHelper.getJSONArray(json, "users");
		int size = jsonArray.length();
		List<User> list = new ArrayList<User>();
		for (int i = 0; i < size; i++) {
			list.add(getUser((JsonHelper.getJSONObject(jsonArray, i))));
		}
		return list;
	}
	

	public int hasNextCursor() {
		return JsonHelper.getInt(json, "next_cursor");
	}

	public static User getUser(JSONObject json) {
		User user = new User();
		user.setId(JsonHelper.getString(json, "id"));
		user.setProfile_image_url(JsonHelper.getString(json,
				"profile_image_url"));
		user.setScreen_name(JsonHelper.getString(json, "screen_name"));
		return user;
	}

	public List<SearchUser> searchUsers(String q) {
		HttpRequestHelper helper = new HttpRequestHelper(Api.AT_USER);
		helper.addParameter("access_token", accessToken);
		helper.addParameter("q", q);
		helper.addParameter("type", String.valueOf(0));
		String jsonData = helper.excute(HttpMethod.GET);

		JSONArray jsonArray = (JSONArray) JsonHelper.toJson(jsonData);
		int size = jsonArray.length();
		List<SearchUser> list = new ArrayList<SearchUser>();
		for (int i = 0; i < size; i++) {
			list.add(getSearchUser((JsonHelper.getJSONObject(jsonArray, i))));
		}
		return list;
	}

	@SuppressWarnings("deprecation")
	public static SearchUser getSearchUser(JSONObject json) {
		SearchUser user = new SearchUser();
		user.setUid(JsonHelper.getString(json, "uid"));
		user.setNickname(URLDecoder.decode(JsonHelper.getString(json,
				"nickname")));
		return user;
	}
	
	public User show(String uid) {
		HttpRequestHelper helper = new HttpRequestHelper(Api.USER_BYID);
		helper.addParameter("access_token", accessToken);
		helper.addParameter("uid", uid);
		String jsonData = helper.excute(HttpMethod.GET);
		
		if (jsonData == null)
			return null;
		JSONObject json = (JSONObject) JsonHelper.toJson(jsonData);
		return getUser(json);
	}

}
