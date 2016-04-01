package com.example.bwoestman.weatheralarm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by Brian Woestman on 3/6/16.
 * Android Programming
 * We 5:30p - 9:20p
 */
public class DBHandlerTest extends MainActivity
{
    @Test
    public void testCreateTable()
    {
        DBHandler dbHandler = new DBHandler(this, null, null, 1);

    }
}
