package org.bjzhou.directmsg.weibo;

import java.util.ArrayList;
import java.util.List;

import org.bjzhou.directmsg.App;
import org.bjzhou.directmsg.bean.DMUser;
import org.bjzhou.directmsg.bean.DMessage;
import org.bjzhou.directmsg.http.HttpRequestHelper;
import org.bjzhou.directmsg.http.HttpRequestHelper.HttpMethod;
import org.bjzhou.directmsg.utils.AccessTokenHelper;
import org.bjzhou.directmsg.utils.FileHelper;
import org.bjzhou.directmsg.utils.JsonHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

public class DMApi {

	private String accessToken;
	private boolean readFromLocal;

	public DMApi(Context context) {
		accessToken = AccessTokenHelper.ReadAccessToken(context);
	}
	
	public void setReadFromLocal(boolean readFromLocal) {
		this.readFromLocal = readFromLocal;
	}

	public List<DMUser> getDMUserList() {
		String jsonData;
		if (FileHelper.getFileSize(App.getSingleton().DMUserListCache) > 1024 && readFromLocal) {
			jsonData = FileHelper.fromFile(App.getSingleton().DMUserListCache);
		} else {
			HttpRequestHelper helper = new HttpRequestHelper(Api.DM_USERLIST);
			helper.addParameter("access_token", accessToken);
			jsonData = helper.excute(HttpMethod.GET);
			if (jsonData != null)
				FileHelper.saveFile(jsonData,
						App.getSingleton().DMUserListCache);
		}
		if (jsonData == null) {
			return null;
		}
		JSONObject jsonObject = (JSONObject) JsonHelper.toJson(jsonData);
		JSONArray jsonArray = JsonHelper.getJSONArray(jsonObject, "user_list");
		int size = jsonArray.length();
		List<DMUser> list = new ArrayList<DMUser>();
		for (int i = 0; i < size; i++) {
			list.add(getDMUser(JsonHelper.getJSONObject(jsonArray, i)));
		}
		return list;
	}
	

	public DMUser getDMUser(JSONObject json) {
		DMUser dmUser = new DMUser();
		dmUser.setUser(UserApi.getUser(JsonHelper.getJSONObject(json, "user")));
		dmUser.setDirect_message(getMessage(JsonHelper.getJSONObject(json,
				"direct_message")));
		dmUser.setUnread_count(JsonHelper.getInt(json, "unread_count"));
		return dmUser;

	}

	public DMessage getMessage(JSONObject json) {
		DMessage msg = new DMessage();
		msg.setId(JsonHelper.getString(json, "id"));
		msg.setText(JsonHelper.getString(json, "text"));
		msg.setCreated_at(JsonHelper.getString(json, "created_at"));
		msg.setSender(UserApi.getUser(JsonHelper.getJSONObject(json, "sender")));
		msg.setRecipient(UserApi.getUser(JsonHelper.getJSONObject(json,
				"recipient")));
		return msg;

	}

	public List<DMessage> getMessageList(String uid) {
		String jsonData;
		HttpRequestHelper helper = new HttpRequestHelper(Api.DM_CONVERSATION);
		helper.addParameter("access_token", accessToken);
		helper.addParameter("uid", uid);
		jsonData = helper.excute(HttpMethod.GET);

		if (jsonData == null) {
			return null;
		}
		JSONObject jsonObject = (JSONObject) JsonHelper.toJson(jsonData);
		JSONArray jsonArray = JsonHelper.getJSONArray(jsonObject,
				"direct_messages");
		int size = jsonArray.length();
		List<DMessage> list = new ArrayList<DMessage>();
		for (int i = 0; i < size; i++) {
			list.add(getMessage(JsonHelper.getJSONObject(jsonArray, i)));
		}
		return list;
	}
	

	public boolean sendMessage(String text, String uid) {
		String jsonData;
		HttpRequestHelper helper = new HttpRequestHelper(Api.DM_CREATE);
		helper.addParameter("access_token", accessToken);
		helper.addParameter("uid", uid);
		helper.addParameter("text", text);
		jsonData = helper.excute(HttpMethod.POST);
		if (jsonData == null) {
			return false;
		}
		JSONObject jsonObject = (JSONObject) JsonHelper.toJson(jsonData);
		if (JsonHelper.getString(jsonObject, "error_code") != null) {
			if (App.DEBUG)
				System.out.println(JsonHelper.getString(jsonObject,
						"error_code"));
			return false;
		}
		return true;
	}
}
