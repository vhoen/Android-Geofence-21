package me.hoen.geofence_21.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "geofencing.db";
	private static final int DATABASE_VERSION = 1;
	public static final String TABLE_EVENTS = "events";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_EVENT_DATE = "event_date";
	public static final String COLUMN_EVENT_TYPE = "event_type";
	public static final String COLUMN_PLACE_NAME = "place_name";
	protected SQLiteDatabase database;
	protected Context context;

	public SqliteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		this.database = database;
		createEventsTable();
	}

	protected void createEventsTable() {
		String sql = "CREATE TABLE '" + SqliteHelper.TABLE_EVENTS
				+ "' ( '_id' INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "'event_date' DATETIME," + "'event_type' VARCHAR ( 200 ),"
				+ "'place_name' VARCHAR ( 200 ))";
		database.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
		onCreate(db);
	}
}