package org.bjzhou.directmsg.ui;

import org.bjzhou.directmsg.R;
import org.bjzhou.directmsg.weibo.DMApi;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.Toast;

public class SendActivity extends Activity {

	private EditText et;
	private String uid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		uid = intent.getStringExtra("uid");
		et = new EditText(this);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		et.setLayoutParams(lp);
		et.setGravity(Gravity.LEFT | Gravity.TOP);

		setContentView(et);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.send, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;
		case R.id.menu_send:
			new SendTask().execute();
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private class SendTask extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			DMApi api = new DMApi(SendActivity.this);
			return api.sendMessage(et.getText().toString(), uid);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			if (result) {
				Toast.makeText(getApplicationContext(), "发送成功", 0).show();
				setResult(1);
			} else {
				Toast.makeText(getApplicationContext(), "发送失败", 0).show();
				setResult(-1);
			}

			super.onPostExecute(result);
		}

	}

}
