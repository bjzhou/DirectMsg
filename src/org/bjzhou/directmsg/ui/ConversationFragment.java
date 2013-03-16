package org.bjzhou.directmsg.ui;

import java.util.ArrayList;
import java.util.List;

import org.bjzhou.directmsg.R;
import org.bjzhou.directmsg.bean.DMUser;
import org.bjzhou.directmsg.bean.DMessage;
import org.bjzhou.directmsg.views.MessageListAdapter;
import org.bjzhou.directmsg.views.MyFragment;
import org.bjzhou.directmsg.weibo.DMApi;

import android.app.ActionBar;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class ConversationFragment extends MyFragment {

	private PullToRefreshListView lv_conversation;
	private DMApi dmApi;
	private List<DMessage> list;
	private MessageListAdapter adapter;
	private String uid;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View contentView = super.onCreateView(inflater, container,
				savedInstanceState);
		getActivity().getActionBar().setNavigationMode(
				ActionBar.NAVIGATION_MODE_STANDARD);
		getActivity().getActionBar().setDisplayShowTitleEnabled(true);
		getActivity().getActionBar().setTitle(R.string.app_name);
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		Bundle data = getArguments();
		if (data != null)
			uid = data.getString("uid");
		lv_conversation = getListView();
		dmApi = new DMApi(getActivity());
		list = new ArrayList<DMessage>();

		return contentView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		switch (getCurrentState(savedInstanceState)) {
		case FIRST_TIME_START:
			// 谁能告诉我为什么需要延时...
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					lv_conversation.setRefreshing();
				}
			}, 100);
			break;
		case SCREEN_ROTATE:
			lv_conversation.setAdapter(adapter);
			break;
		case ACTIVITY_DESTROY_AND_CREATE:
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					lv_conversation.setRefreshing();
				}
			}, 100);
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int postition,
			long arg3) {
		// DO NOTHING
	}

	private class GetMessageTask extends AsyncTask<String, Void, List<DMUser>> {

		@Override
		protected List<DMUser> doInBackground(String... params) {
			list = dmApi.getMessageList(uid);
			return null;
		}

		@Override
		protected void onPostExecute(List<DMUser> result) {
			if (list == null)
				return;
			lv_conversation.onRefreshComplete();
			adapter = new MessageListAdapter(ConversationFragment.this, list);
			lv_conversation.setAdapter(adapter);
			super.onPostExecute(result);
		}

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.edit, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;
		case R.id.menu_edit:
			Intent intent = new Intent(getActivity(), SendActivity.class);
			intent.putExtra("uid", uid);
			startActivityForResult(intent, 0);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		new GetMessageTask().execute();
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void updateView() {
		adapter.notifyDataSetChanged();
	}

	@Override
	public String getUidOnItemClick(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		new GetMessageTask().execute();
	}

}
