package org.bjzhou.directmsg.ui;

import org.bjzhou.directmsg.App;
import org.bjzhou.directmsg.R;
import org.bjzhou.directmsg.utils.AccessTokenHelper;
import org.bjzhou.directmsg.views.MyFragment;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.ArrayAdapter;

public class HomeActivity extends FragmentActivity implements
		OnNavigationListener {

	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	private boolean refresh = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		// 初始化数据
		App.getSingleton();

		Intent intent = getIntent();
		if (AccessTokenHelper.ReadAccessToken(this) == null) {
			intent = new Intent();
			intent.setClass(this, LoginActivity.class);
			startActivity(intent);
			finish();
		}
		if (intent.getBooleanExtra("refresh", false)) {
			refresh = true;
		}

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setListNavigationCallbacks(
				new ArrayAdapter<String>(actionBar.getThemedContext(),
						android.R.layout.simple_list_item_1,
						android.R.id.text1, new String[] {
								getString(R.string.title_activity_home),
								getString(R.string.friends_list), }), this);

	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current dropdown position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current dropdown position.
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
				.getSelectedNavigationIndex());
	}

	@Override
	public boolean onNavigationItemSelected(int position, long arg1) {
		Fragment fragment = null;
		Bundle data = new Bundle();
		switch (position) {
		case 0:
			fragment = new HomeFragment();
			data.putBoolean("refresh", refresh);
			fragment.setArguments(data);
			break;
		case 1:
			fragment = new FriendsFragment();
			break;
		}
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, fragment, "fragmentTag").commit();
		return true;
	}

	@Override
	public void onBackPressed() {
		MyFragment current = (MyFragment) getSupportFragmentManager()
				.findFragmentById(R.id.container);
		current.onBackPressed();
	}

}
