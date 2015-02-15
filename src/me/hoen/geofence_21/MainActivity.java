package me.hoen.geofence_21;

import com.google.android.gms.location.Geofence;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;

public class MainActivity extends ActionBarActivity {
	public static String TAG = "lstech.aos.debug";

	static public boolean geofencesAlreadyRegistered = false;

	private GeofenceSampleReceiver geofenceSampleReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Fragment f = new MapFragment();

		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().add(android.R.id.content, f, "home")
				.commit();
		fragmentManager.executePendingTransactions();

		 startGeolocationService(getApplicationContext());

		IntentFilter filter = new IntentFilter();
		filter.addAction(GeolocationService.ACTION_GEOFENCES_ERROR);
		filter.addAction(GeolocationService.ACTION_GEOFENCES_SUCCESS);

		geofenceSampleReceiver = new GeofenceSampleReceiver();

		LocalBroadcastManager.getInstance(this).registerReceiver(
				geofenceSampleReceiver, filter);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		LocalBroadcastManager.getInstance(this).unregisterReceiver(
				geofenceSampleReceiver);
	}

	static public void startGeolocationService(Context context) {

		Intent geolocationService = new Intent(context,
				GeolocationService.class);
		PendingIntent piGeolocationService = PendingIntent.getService(context,
				0, geolocationService, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(piGeolocationService);
		alarmManager
				.setInexactRepeating(AlarmManager.RTC_WAKEUP,
						System.currentTimeMillis(), 2 * 60 * 1000,
						piGeolocationService);
	}
}
