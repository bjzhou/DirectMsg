package org.bjzhou.directmsg.utils;

import org.bjzhou.directmsg.App;
import org.bjzhou.directmsg.bean.User;
import org.bjzhou.directmsg.views.MyFragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.BaseAdapter;

public class ImageLoadTask extends AsyncTask<User, Void, String> {
	
	private MyFragment fragment;
	private BaseAdapter adapter;
	
	public ImageLoadTask(MyFragment fragment, BaseAdapter adapter) {
		this.fragment = fragment;
		this.adapter = adapter;
	}

	@Override
	protected String doInBackground(User... params) {
		User user = params[0];
		String id = user.getId();
		if (App.imgs.get(id) != null)
			return null;
		String url = user.getProfile_image_url();
		String result = FileHelper.downloadPic(url,
				App.getSingleton().UserImageCacheDir);
		Bitmap bitmap = BitmapFactory.decodeFile(result);
		App.imgs.put(id, bitmap);
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		if (!fragment.isFling)
			adapter.notifyDataSetChanged();
		super.onPostExecute(result);
	}

}
