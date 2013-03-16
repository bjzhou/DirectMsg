package org.bjzhou.directmsg;

import java.io.File;
import java.util.HashMap;

import org.bjzhou.directmsg.utils.FileHelper;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;

public class App extends Application{
	
	private static App app = null;
	    
    public static final boolean DEBUG = true;
    
    public static final String PREFERENCE_NAME = "bjzhou_preference";
    public static final String APP_KEY = "2323547071";
    public static final String APP_SECRET = "16ed80cc77fea11f7f7e96eca178ada3";
    
    public String DMUserListCache;
    public String UserListCache;
    public String UserImageCacheDir;
    
    public static App getSingleton() {
		return app;
    }

	@Override
	public void onCreate() {
	    DMUserListCache = getCacheDir() + File.separator + "dmusers.cache";
	    UserListCache = getCacheDir() + File.separator + "users.cache";
	    UserImageCacheDir = getExternalCacheDir() + File.separator + "userimage";
	    FileHelper.createDir(UserImageCacheDir);
		super.onCreate();
		app = this;
	}
	
	public String getMessagesPath(String uid) {
		String dir = getExternalCacheDir() + File.separator + uid;
		FileHelper.createDir(dir);
		return dir + File.separator + "messages.cache";
	}
	
	public void setUnread(int count) {
		SharedPreferences sp = getSharedPreferences(App.PREFERENCE_NAME, Context.MODE_APPEND);
		Editor edit = sp.edit();
		edit.putInt("unread", count);
		edit.commit();
	}
	
	public int getUnread() {
		SharedPreferences sp = getSharedPreferences(App.PREFERENCE_NAME, Context.MODE_APPEND);
		return sp.getInt("unread", 0);
	}
	
	public static HashMap<String,Bitmap> imgs = new HashMap<String, Bitmap>();
	

}
