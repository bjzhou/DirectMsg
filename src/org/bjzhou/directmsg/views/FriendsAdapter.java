/**
 * 
 */
package org.bjzhou.directmsg.views;

import java.util.List;

import org.bjzhou.directmsg.App;
import org.bjzhou.directmsg.R;
import org.bjzhou.directmsg.bean.User;
import org.bjzhou.directmsg.utils.ImageLoadTask;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FriendsAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<User> list;
	private MyFragment fragment;

	public FriendsAdapter(Fragment fragment, List<User> list) {
		this.list = list;
		this.fragment = (MyFragment) fragment;
		inflater = fragment.getActivity().getLayoutInflater();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.listitem_friends, null);
			holder.iv_userimage = (ImageView) view
					.findViewById(R.id.iv_userimage);
			holder.tv_username = (TextView) view.findViewById(R.id.tv_username);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.tv_username.setText(list.get(position).getScreen_name());
		Bitmap bitmap = App.imgs.get(list.get(position).getId());
		if (bitmap != null)
			holder.iv_userimage.setImageBitmap(bitmap);
		else {
			holder.iv_userimage.setImageBitmap(null);
			new ImageLoadTask(fragment,FriendsAdapter.this).execute(list.get(position));
		}
		return view;
	}

	public static class ViewHolder {
		ImageView iv_userimage;
		TextView tv_username;
	}

}
