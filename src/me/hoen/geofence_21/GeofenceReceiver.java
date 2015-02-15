package me.hoen.geofence_21;

import java.util.Date;
import java.util.List;

import me.hoen.geofence_21.db.EventDataSource;
import android.app.IntentService;
import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

public class GeofenceReceiver extends IntentService {
	public static final int NOTIFICATION_ID = 1;

	public GeofenceReceiver() {
		super("GeofenceReceiver");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		GeofencingEvent geoEvent = GeofencingEvent.fromIntent(intent);
		if (geoEvent.hasError()) {
			Log.d(MainActivity.TAG, "Error GeofenceReceiver.onHandleIntent");
		} else {
			Log.d(MainActivity.TAG, "GeofenceReceiver : Transition -> "
					+ geoEvent.getGeofenceTransition());

			int transitionType = geoEvent.getGeofenceTransition();

			if (transitionType == Geofence.GEOFENCE_TRANSITION_ENTER
					|| transitionType == Geofence.GEOFENCE_TRANSITION_DWELL
					|| transitionType == Geofence.GEOFENCE_TRANSITION_EXIT) {
				List<Geofence> triggerList = geoEvent.getTriggeringGeofences();

				for (Geofence geofence : triggerList) {
					SimpleGeofence sg = SimpleGeofenceStore.getInstance()
							.getSimpleGeofences().get(geofence.getRequestId());

					String transitionName = "";
					switch (transitionType) {
					case Geofence.GEOFENCE_TRANSITION_DWELL:
						transitionName = "dwell";
						break;

					case Geofence.GEOFENCE_TRANSITION_ENTER:
						transitionName = "enter";
						break;

					case Geofence.GEOFENCE_TRANSITION_EXIT:
						transitionName = "exit";
						break;
					}
					String date = DateFormat.format("yyyy-MM-dd hh:mm:ss",
							new Date()).toString();
					EventDataSource eds = new EventDataSource(
							getApplicationContext());
					eds.create(transitionName, date, geofence.getRequestId());
					eds.close();

					GeofenceNotification geofenceNotification = new GeofenceNotification(
							this);
					geofenceNotification
							.displayNotification(sg, transitionType);
				}
			}
		}
	}
}
