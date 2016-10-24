package com.sonda.emsysmobile.utils;

import com.sonda.emsysmobile.backendcommunication.RequestFactory;
import com.sonda.emsysmobile.utils.constants.ExternalService;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertTrue;

/**
 * Created by marccio on 10/22/16.
 */

public class PrivateConstructorsTest {

    @Test
    public void testConstructorUIUtilsIsPrivate() throws NoSuchMethodException, IllegalAccessException,
            InstantiationException, InvocationTargetException {
        Constructor<UIUtils> constructor = UIUtils.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void testConstructorRequestFactoryIsPrivate() throws NoSuchMethodException,
            IllegalAccessException,
            InstantiationException, InvocationTargetException {
        Constructor<RequestFactory> constructor = RequestFactory.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void testConstructorExternalServiceIsPrivate() throws NoSuchMethodException,
            IllegalAccessException,
            InstantiationException, InvocationTargetException {
        Constructor<ExternalService> constructor = ExternalService.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void testConstructorMapUtilsIsPrivate() throws NoSuchMethodException,
            IllegalAccessException,
            InstantiationException, InvocationTargetException {
        Constructor<MapUtils> constructor = MapUtils.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

}
