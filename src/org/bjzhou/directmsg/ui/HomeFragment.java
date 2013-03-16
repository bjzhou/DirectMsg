package org.bjzhou.directmsg.ui;

import java.util.ArrayList;
import java.util.List;

import org.bjzhou.directmsg.App;
import org.bjzhou.directmsg.R;
import org.bjzhou.directmsg.bean.DMUser;
import org.bjzhou.directmsg.services.UnreadService;
import org.bjzhou.directmsg.views.DMUserListAdapter;
import org.bjzhou.directmsg.views.MyFragment;
import org.bjzhou.directmsg.weibo.DMApi;
import org.bjzhou.directmsg.weibo.UnreadApi;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class HomeFragment extends MyFragment {

	private PullToRefreshListView lv_home;
	private DMApi dmApi;
	private List<DMUser> list;
	private DMUserListAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View contentView = super.onCreateView(inflater, container,
				savedInstanceState);
		lv_home = getListView();
		dmApi = new DMApi(getActivity());
		list = new ArrayList<DMUser>();

		int unread = App.getSingleton().getUnread();
		if (unread > 0) {
			setCustomAction(unread);
		}
		return contentView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		switch (getCurrentState(savedInstanceState)) {
		case FIRST_TIME_START:
			init();
			break;

		default:
			break;
		}
	}

	private void init() {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				AlarmManager am = (AlarmManager) getActivity()
						.getSystemService(Context.ALARM_SERVICE);
				Intent intent = new Intent(getActivity(), UnreadService.class);
				PendingIntent pi = PendingIntent.getService(getActivity(), 0,
						intent, 0);
				am.cancel(pi);
				am.setRepeating(AlarmManager.RTC_WAKEUP, 0, 5 * 60 * 1000, pi);
			}
		}, 1000);
	}

	private void setCustomAction(int count) {
		ActionBar actionBar = getActivity().getActionBar();
		actionBar.setDisplayShowCustomEnabled(true);
		Button btn = (Button) getActivity().getLayoutInflater().inflate(R.layout.action_unread, null);
		btn.setText(count+"");
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				lv_home.setRefreshing();
			}
		});
		LayoutParams lp = new LayoutParams(Gravity.RIGHT);
		actionBar.setCustomView(btn,lp);
	}

	private class GetDMUserListTask extends
			AsyncTask<Boolean, Void, List<DMUser>> {

		@Override
		protected List<DMUser> doInBackground(Boolean... params) {
			// TODO Auto-generated method stub
			dmApi.setReadFromLocal(params[0]);
			list = dmApi.getDMUserList();
			if (!params[0])
				new UnreadApi(getActivity()).clear();
			return null;
		}

		@Override
		protected void onPostExecute(List<DMUser> result) {
			if (list == null)
				return;
			adapter = new DMUserListAdapter(HomeFragment.this, list);
			lv_home.onRefreshComplete();
			lv_home.setAdapter(adapter);
			super.onPostExecute(result);
		}

	}

	@Override
	public void onResume() {
		Bundle data = getArguments();
		boolean refresh = false;
		if (data != null)
			refresh = data.getBoolean("refresh", false);
		new GetDMUserListTask().execute(!refresh);
		super.onResume();
	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		new GetDMUserListTask().execute(false);
		getActivity().getActionBar().setDisplayShowCustomEnabled(false);
		App.getSingleton().setUnread(0);
	}

	@Override
	public void updateView() {
		adapter.notifyDataSetChanged();
	}

	@Override
	public String getUidOnItemClick(int position) {
		return list.get(position).getUser().getId();
	}

}
