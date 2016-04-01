package com.example.bwoestman.weatheralarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Brian Woestman on 3/6/16.
 * Android Programming
 * We 5:30p - 9:20p
 */
public class DBHandler extends SQLiteOpenHelper implements AppInfo
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "alarmDb.db";
    private static final String TABLE_ALARMS = "alarms";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_HOUR = "hour";
    public static final String COLUMN_MINUTE = "minute";
    public static final String COLUMN_RAIN = "rain";
    public static final String COLUMN_ADJUSTMENT = "adjustment";
    public static final String COLUMN_ALARM_INTENT = "alarmIntent";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory,
                     int version)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override

    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_ALARMS_TABLE = "CREATE TABLE " + TABLE_ALARMS
                + " (" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_HOUR
                + " TEXT," + COLUMN_MINUTE + " TEXT," + COLUMN_RAIN + " TEXT,"
                + COLUMN_ADJUSTMENT + " TEXT," + COLUMN_ALARM_INTENT + ")";

        db.execSQL(CREATE_ALARMS_TABLE);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p/>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    public Long addAlarm(Alarm alarm)
    {
        ContentValues values = new ContentValues();

        String intent = alarm.getAlarmIntent().toUri(0);

        values.put(COLUMN_HOUR, alarm.getHour());
        values.put(COLUMN_MINUTE, alarm.getMinute());
        values.put(COLUMN_RAIN, alarm.getRain());
        values.put(COLUMN_ADJUSTMENT, alarm.getAdjustment());
        values.put(COLUMN_ALARM_INTENT, intent);

        SQLiteDatabase db = this.getWritableDatabase();
        Long id = db.insert(TABLE_ALARMS, null, values);

        alarm.set_id(id);

        return id;
    }
}
