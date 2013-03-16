package org.bjzhou.directmsg.ui;

import org.bjzhou.directmsg.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class ConversationActivity extends FragmentActivity {


	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_home);
		
		if (arg0 == null) {
			Intent intent = getIntent();
			Fragment fragment = new ConversationFragment();
			Bundle data = new Bundle();
			data.putString("uid", intent.getStringExtra("uid"));
			fragment.setArguments(data);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, fragment, "fragmentTag").commit();
		}
	}

}
