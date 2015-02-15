package me.hoen.geofence_21.db;

import android.database.Cursor;

public class Event {
	protected int id;
	protected String type;
	protected String date;
	protected String placeName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	static public Event fromCursor(Cursor cursor) {
		if (cursor != null && cursor.getCount() != 0) {
			Event e = new Event();
			e.setId(cursor.getInt(cursor.getColumnIndex(SqliteHelper.COLUMN_ID)));
			e.setType(cursor.getString(cursor
					.getColumnIndex(SqliteHelper.COLUMN_EVENT_TYPE)));
			e.setDate(cursor.getString(cursor
					.getColumnIndex(SqliteHelper.COLUMN_EVENT_DATE)));
			e.setPlaceName(cursor.getString(cursor
					.getColumnIndex(SqliteHelper.COLUMN_PLACE_NAME)));
			return e;
		}
		return null;
	}
}