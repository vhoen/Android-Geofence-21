package me.hoen.geofence_21;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import me.hoen.geofence_21.db.Event;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EventListAdapter extends ArrayAdapter<Event> {
	protected Activity activity;
	protected ArrayList<Event> list;
	protected int textViewResourceId;

	public EventListAdapter(Activity activity, int textViewResourceId,
			ArrayList<Event> list) {
		super(activity, textViewResourceId, list);
		this.activity = activity;
		this.textViewResourceId = textViewResourceId;
		this.list = list;
	}

	public int getCount() {
		return list.size();
	}

	public Event getItem(Event position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = convertView;
		if (rowView == null) {
			rowView = inflater.inflate(textViewResourceId, parent, false);
		}

		Event e = list.get(position);

		ImageView typeImageIv = (ImageView) rowView
				.findViewById(R.id.type_image);
		switch (e.getType().toLowerCase(Locale.getDefault())) {
		case "dwell":
			typeImageIv.setImageResource(R.drawable.ic_menu_dwell);
			break;
		case "enter":
			typeImageIv.setImageResource(R.drawable.ic_menu_in);
			break;
		case "exit":
			typeImageIv.setImageResource(R.drawable.ic_menu_out);
			break;
		}
		TextView typeTv = (TextView) rowView.findViewById(R.id.type);
		typeTv.setText(e.getType());

		TextView placenameTv = (TextView) rowView.findViewById(R.id.placename);
		placenameTv.setText(e.getPlaceName());

		try {
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
					Locale.getDefault());
			calendar.setTime(sdf.parse(e.getDate()));
			TextView dateTv = (TextView) rowView.findViewById(R.id.date);
			dateTv.setText(getDisplayDate(calendar));
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return rowView;
	}

	static public String getDisplayDate(Calendar date) {
		String displayDate = "";
		Calendar startDate = Calendar.getInstance();
		startDate.set(Calendar.HOUR_OF_DAY, 0);
		startDate.set(Calendar.MINUTE, 0);
		startDate.set(Calendar.SECOND, 0);
		Calendar endDate = Calendar.getInstance();
		endDate.set(Calendar.HOUR_OF_DAY, 23);
		endDate.set(Calendar.MINUTE, 59);
		endDate.set(Calendar.SECOND, 59);
		if (date.after(startDate) && date.before(endDate)) {
			SimpleDateFormat format = new SimpleDateFormat("HH:mm",
					Locale.getDefault());
			displayDate = format.format(date.getTime());
		} else {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",
					Locale.getDefault());
			displayDate = format.format(date.getTime());
		}
		return displayDate;
	}
}
