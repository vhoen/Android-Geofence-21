package me.hoen.geofence_21;

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
		fragmentManager.beginTransaction()
				.replace(android.R.id.content, f, "home").commit();
		fragmentManager.executePendingTransactions();

		startService(new Intent(this, GeolocationService.class));

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
}
