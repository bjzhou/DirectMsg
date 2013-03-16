package org.bjzhou.directmsg.ui;

import java.util.ArrayList;
import java.util.List;

import org.bjzhou.directmsg.R;
import org.bjzhou.directmsg.bean.SearchUser;
import org.bjzhou.directmsg.bean.User;
import org.bjzhou.directmsg.utils.AccessTokenHelper;
import org.bjzhou.directmsg.views.FriendsAdapter;
import org.bjzhou.directmsg.views.MyFragment;
import org.bjzhou.directmsg.weibo.UserApi;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class FriendsFragment extends MyFragment implements OnQueryTextListener,
		OnScrollListener {

	private PullToRefreshListView lv_friends;
	private UserApi userApi;
	private List<User> list;
	private FriendsAdapter adapter;
	private SearchView search;
	private FriendsAdapter searchAdapter;
	private List<User> searchList;
	public boolean isFling = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View contentView = super.onCreateView(inflater, container,
				savedInstanceState);
		lv_friends = getListView();
		search = (SearchView) contentView.findViewById(R.id.search_view);
		lv_friends.setOnScrollListener(this);
		search.setVisibility(View.VISIBLE);
		search.setOnQueryTextListener(this);
		userApi = new UserApi(getActivity());
		list = new ArrayList<User>();
		searchList = new ArrayList<User>();
		searchAdapter = new FriendsAdapter(this, searchList);
		return contentView;
	}

	private class FriendsTask extends AsyncTask<Boolean, Void, List<User>> {

		@Override
		protected List<User> doInBackground(Boolean... params) {
			List<User> tmp = new ArrayList<User>();
			userApi.setReadFromLocal(params[0]);
			for (int i = 0;; i++) {
				if (getActivity() != null)
					tmp = userApi.friends(
							AccessTokenHelper.ReadUid(getActivity()), 200,
							200 * i);
				if (tmp == null)
					return null;
				list.addAll(tmp);
				if (userApi.hasNextCursor() == 0) {
					break;
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<User> result) {
			if (list == null)
				return;
			lv_friends.onRefreshComplete();
			adapter = new FriendsAdapter(FriendsFragment.this, list);
			lv_friends.setAdapter(adapter);
			super.onPostExecute(result);
		}

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		new FriendsTask().execute(true);
	}

	@Override
	public boolean onQueryTextChange(String q) {
		if (q.isEmpty()) {
			lv_friends.setAdapter(adapter);
			return false;
		}
		new SearchTask().execute(q);
		return false;
	}

	private class SearchTask extends AsyncTask<String, Void, List<SearchUser>> {

		@Override
		protected List<SearchUser> doInBackground(String... q) {
			return userApi.searchUsers(q[0]);
		}

		@Override
		protected void onPostExecute(List<SearchUser> result) {
			searchList.clear();
			for (int i = 0; i < result.size(); i++) {
				for (int j = 0; j < list.size(); j++) {
					if (result.get(i).getNickname()
							.equals(list.get(j).getScreen_name())) {
						searchList.add(list.get(j));
					}
				}
			}
			lv_friends.setAdapter(searchAdapter);
			super.onPostExecute(result);
		}

	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		search.clearFocus();
		return false;
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		new FriendsTask().execute(false);
	}

	@Override
	public void updateView() {
		adapter.notifyDataSetChanged();
	}

	@Override
	public String getUidOnItemClick(int position) {
		return list.get(position).getId();
	}

}
