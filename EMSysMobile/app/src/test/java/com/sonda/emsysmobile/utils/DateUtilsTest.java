package com.sonda.emsysmobile.utils;

import com.sonda.emsysmobile.R;

import org.junit.Test;
import org.mockito.invocation.Invocation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by marccio on 10/20/16.
 */
public class DateUtilsTest {

    @Test
    public void testConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException,
            InstantiationException, InvocationTargetException {
        Constructor<DateUtils> constructor = DateUtils.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void dateToString() throws Exception {
        assertTrue(DateUtils.dateToString(null).equals(""));
        Date testDate = new Date();
        DateFormat testDateFormat = new SimpleDateFormat(DateUtils.SIMPLE_FORMAT);
        assertTrue(DateUtils.dateToString(testDate).equals(testDateFormat.format(testDate)));
    }

}