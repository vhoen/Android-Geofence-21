package me.hoen.geofence_21;

import java.util.HashMap;
import java.util.Map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment {
	protected SupportMapFragment mapFragment;
	protected GoogleMap map;
	protected Marker myPositionMarker;

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				int resultCode = bundle.getInt("done");
				if (resultCode == 1) {
					Double latitude = bundle.getDouble("latitude");
					Double longitude = bundle.getDouble("longitude");

					updateMarker(latitude, longitude);
				}
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setRetainInstance(true);
		setHasOptionsMenu(true);

		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_map, container,
				false);

		mapFragment = SupportMapFragment.newInstance();
		FragmentTransaction fragmentTransaction = getChildFragmentManager()
				.beginTransaction();
		fragmentTransaction.add(R.id.map_container, mapFragment);
		fragmentTransaction.commit();

		return rootView;
	}

	@Override
	public void onPause() {
		super.onPause();

		getActivity().unregisterReceiver(receiver);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mapFragment != null) {
			mapFragment.getMapAsync(new OnMapReadyCallback() {

				@Override
				public void onMapReady(GoogleMap googleMap) {
					map = googleMap;
					map.animateCamera(CameraUpdateFactory.zoomTo(15));
					displayGeofences();
				}
			});
		}

		getActivity().registerReceiver(receiver,
				new IntentFilter("me.hoen.geofence_21.geolocation.service"));
	}

	protected void displayGeofences() {
		HashMap<String, SimpleGeofence> geofences = SimpleGeofenceStore
				.getInstance().getSimpleGeofences();

		for (Map.Entry<String, SimpleGeofence> item : geofences.entrySet()) {
			SimpleGeofence sg = item.getValue();

			CircleOptions circleOptions1 = new CircleOptions()
					.center(new LatLng(sg.getLatitude(), sg.getLongitude()))
					.radius(sg.getRadius()).strokeColor(Color.BLACK)
					.strokeWidth(2).fillColor(0x500000ff);
			map.addCircle(circleOptions1);
		}
	}

	protected void createMarker(Double latitude, Double longitude) {
		LatLng latLng = new LatLng(latitude, longitude);

		myPositionMarker = map.addMarker(new MarkerOptions().position(latLng));
		map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
	}

	protected void updateMarker(Double latitude, Double longitude) {
		if (myPositionMarker == null) {
			createMarker(latitude, longitude);
		}

		LatLng latLng = new LatLng(latitude, longitude);
		myPositionMarker.setPosition(latLng);
		map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);

		inflater.inflate(R.menu.menu_map, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.events:
			Fragment f = new EventsFragment();

			getFragmentManager().beginTransaction()
					.replace(android.R.id.content, f).addToBackStack("events")
					.commit();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
