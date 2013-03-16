package org.bjzhou.directmsg.services;

import org.bjzhou.directmsg.App;
import org.bjzhou.directmsg.R;
import org.bjzhou.directmsg.ui.HomeActivity;
import org.bjzhou.directmsg.weibo.UnreadApi;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

public class UnreadService extends Service {

	private UnreadApi unreadApi;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		if (App.DEBUG)
			System.out.println("service start");
		new UnreadTask().execute();
	}

	private class UnreadTask extends AsyncTask<String, Void, Integer> {

		@Override
		protected Integer doInBackground(String... params) {
			unreadApi = new UnreadApi(getApplicationContext());
			return unreadApi.getUnread();

		}

		@Override
		protected void onPostExecute(Integer result) {
			int count = result - App.getSingleton().getUnread();
			if (count > 0) {
				notifyUnread(result);
				App.getSingleton().setUnread(result);
			}
			super.onPostExecute(result);
		}

	}
	
	@SuppressWarnings("deprecation")
	private void notifyUnread(int count) {
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Intent i = new Intent(UnreadService.this, HomeActivity.class);
		i.putExtra("refresh", true);
		PendingIntent pi = PendingIntent.getActivity(
				UnreadService.this, 0, i, 0);
		Notification.Builder builder = new Notification.Builder(
				UnreadService.this).setContentTitle(count + "条新私信")
				.setContentText("点击查看")
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentIntent(pi).setAutoCancel(true)
				.setDefaults(Notification.DEFAULT_SOUND);
		nm.notify(0, builder.getNotification());
	}
}
