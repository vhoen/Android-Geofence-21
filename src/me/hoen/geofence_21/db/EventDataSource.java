package me.hoen.geofence_21.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class EventDataSource {
	private String[] allColumns = { SqliteHelper.COLUMN_ID,
			SqliteHelper.COLUMN_EVENT_TYPE, SqliteHelper.COLUMN_EVENT_DATE,
			SqliteHelper.COLUMN_PLACE_NAME, };
	private Context context;
	protected SQLiteDatabase database;
	protected SqliteHelper dbHelper;

	public EventDataSource(Context context) {
		this.context = context;
		initSqliteHelper();
		open();
	}

	protected void initSqliteHelper() {
		dbHelper = new SqliteHelper(context);
	}

	protected void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		database.close();
		dbHelper.close();
	}

	public void create(String type, String date, String placeName) {
		ContentValues values = new ContentValues();
		values.put(SqliteHelper.COLUMN_EVENT_TYPE, type);
		values.put(SqliteHelper.COLUMN_EVENT_DATE, date);
		values.put(SqliteHelper.COLUMN_PLACE_NAME, placeName);
		database.insert(SqliteHelper.TABLE_EVENTS, null, values);
	}

	public ArrayList<Event> getEvents() {
		ArrayList<Event> list = new ArrayList<Event>();
		String orderBy = SqliteHelper.COLUMN_EVENT_DATE + " DESC";
		Cursor cursor = database.query(SqliteHelper.TABLE_EVENTS, allColumns,
				null, null, null, null, orderBy);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			list.add(Event.fromCursor(cursor));
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return list;
	}
}
