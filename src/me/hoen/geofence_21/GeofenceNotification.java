package me.hoen.geofence_21;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.location.Geofence;

public class GeofenceNotification {
	public static final int NOTIFICATION_ID = 20;

	protected Context context;

	protected NotificationManager notificationManager;
	protected Notification notification;

	public GeofenceNotification(Context context) {
		this.context = context;

		this.notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	protected void buildNotificaction(SimpleGeofence simpleGeofence,
			int transitionType) {

		String notificationText = "";
		Object[] notificationTextParams = new Object[] { simpleGeofence.getId() };

		switch (transitionType) {
		case Geofence.GEOFENCE_TRANSITION_DWELL:
			notificationText = String.format(
					context.getString(R.string.geofence_dwell),
					notificationTextParams);
			break;

		case Geofence.GEOFENCE_TRANSITION_ENTER:
			notificationText = String.format(
					context.getString(R.string.geofence_enter),
					notificationTextParams);
			break;

		case Geofence.GEOFENCE_TRANSITION_EXIT:
			notificationText = String.format(
					context.getString(R.string.geofence_exit),
					notificationTextParams);
			break;
		}
		Log.d(MainActivity.TAG, notificationText);

		NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
				context)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle(context.getString(R.string.app_name))
				.setContentText(notificationText).setAutoCancel(true);

		notification = notificationBuilder.build();
		notification.defaults |= Notification.DEFAULT_LIGHTS;
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.defaults |= Notification.DEFAULT_VIBRATE;
	}

	public void displayNotification(SimpleGeofence simpleGeofence,
			int transitionType) {
		buildNotificaction(simpleGeofence, transitionType);

		notificationManager.notify(NOTIFICATION_ID, notification);
	}
}
