package org.bjzhou.directmsg.views;

import java.util.List;

import org.bjzhou.directmsg.App;
import org.bjzhou.directmsg.R;
import org.bjzhou.directmsg.bean.DMessage;
import org.bjzhou.directmsg.utils.DateUtil;
import org.bjzhou.directmsg.utils.ImageLoadTask;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MessageListAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<DMessage> list;
	private MyFragment fragment;

	public MessageListAdapter(Fragment fragment, List<DMessage> list) {
		this.list = list;
		if (fragment.getActivity() != null)
			inflater = fragment.getActivity().getLayoutInflater();
		this.fragment = (MyFragment) fragment;
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
	public View getView(int position, View view, ViewGroup container) {
		ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.listitem_home, null);
			holder.iv_userimage = (ImageView) view
					.findViewById(R.id.iv_userimage);
			holder.tv_username = (TextView) view.findViewById(R.id.tv_username);
			holder.tv_date = (TextView) view.findViewById(R.id.tv_date);
			holder.tv_text = (TextView) view.findViewById(R.id.tv_text);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.tv_date.setText(DateUtil.formatDate(list.get(position)
				.getCreated_at()));
		holder.tv_text.setText(list.get(position).getText());
		holder.tv_username.setText(list.get(position).getSender()
				.getScreen_name());
		holder.url = list.get(position).getSender().getProfile_image_url();
		Bitmap bitmap = App.imgs.get(list.get(position).getSender().getId());
		if (bitmap != null)
			holder.iv_userimage.setImageBitmap(bitmap);
		else {
			holder.iv_userimage.setImageBitmap(null);
			new ImageLoadTask(fragment, MessageListAdapter.this).execute(list
					.get(position).getSender());
		}
		return view;
	}

	public static class ViewHolder {
		ImageView iv_userimage;
		TextView tv_username;
		TextView tv_date;
		TextView tv_text;
		String url;
	}
}
