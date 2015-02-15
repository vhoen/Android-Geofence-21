package me.hoen.geofence_21;

import java.util.HashMap;

import android.text.format.DateUtils;

import com.google.android.gms.location.Geofence;

public class SimpleGeofenceStore {
	private static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;
	public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS = GEOFENCE_EXPIRATION_IN_HOURS
			* DateUtils.HOUR_IN_MILLIS;
	protected HashMap<String, SimpleGeofence> geofences = new HashMap<String, SimpleGeofence>();
	private static SimpleGeofenceStore instance = new SimpleGeofenceStore();

	public static SimpleGeofenceStore getInstance() {
		return instance;
	}

	private SimpleGeofenceStore() {
		geofences.put("The Shire", new SimpleGeofence("The Shire", 51.663398, -0.209118,
				100, GEOFENCE_EXPIRATION_IN_MILLISECONDS,
				Geofence.GEOFENCE_TRANSITION_ENTER
						| Geofence.GEOFENCE_TRANSITION_DWELL
						| Geofence.GEOFENCE_TRANSITION_EXIT));
	}

	public HashMap<String, SimpleGeofence> getSimpleGeofences() {
		return this.geofences;
	}
}