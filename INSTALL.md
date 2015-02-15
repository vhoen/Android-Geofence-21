# Install

- Rename [AndroidManifest.xml.dist](./AndroidManifest.xml.dist) to AndroidManifest.xml
- Rename [AndroidSimpleGeofenceStore.xml.dist](./src/me/hoen/geofence_21/SimpleGeofenceStore.java.dist) to src/me/hoen/geofence_21/SimpleGeofenceStore.java
- Add android-support-v4.jar in "libs"
- Set up Google Play Services
- Add your Google maps API KEY in the ./AndroidManifest.
```xml
<meta-data android:name="com.google.android.maps.v2.API_KEY" android:value="?????????????????????_??_?????????-????" />
