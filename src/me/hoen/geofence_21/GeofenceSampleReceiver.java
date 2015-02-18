package me.hoen.geofence_21;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

public class GeofenceSampleReceiver extends BroadcastReceiver {

	public static final String ACTION_GEOFENCES_ERROR = "me.hoen.geofence_21.ACTION_GEOFENCES_ERROR";
	public static final String ACTION_GEOFENCES_SUCCESS = "me.hoen.geofence_21.ACTION_GEOFENCES_SUCCESS";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(MainActivity.TAG, "GeofenceSampleReceiver");
		String action = intent.getAction();

		if (TextUtils.equals(action, ACTION_GEOFENCES_ERROR)) {
			Log.d(MainActivity.TAG, "Error adding geofences");
		} else if (TextUtils.equals(action, ACTION_GEOFENCES_SUCCESS)) {
			Log.d(MainActivity.TAG, "Success adding geofences");
		}

	}
}
