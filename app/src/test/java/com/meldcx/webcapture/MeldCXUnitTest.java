package com.meldcx.webcapture;

import com.meldcx.webcapture.utils.Utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MeldCXUnitTest {
    //checking the date format
    @Test
    public void checkDateFormat() {
        assertEquals("12:12:48 19 Feb 2020", Utils.INSTANCE.getTime(1582031568247L));
    }

}