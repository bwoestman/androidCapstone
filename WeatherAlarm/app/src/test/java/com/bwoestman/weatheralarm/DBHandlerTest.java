package com.bwoestman.weatheralarm;

import org.junit.Test;

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
