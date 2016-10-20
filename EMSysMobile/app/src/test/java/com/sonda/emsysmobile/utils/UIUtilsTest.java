package com.sonda.emsysmobile.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.sonda.emsysmobile.BaseMockTest;
import com.sonda.emsysmobile.ui.views.dialogs.SimpleDialog;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.hamcrest.core.IsAnything.any;
import static org.hamcrest.core.IsAnything.anything;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * Created by marccio on 10/20/16.
 */
@PrepareForTest(UIUtils.class)
public class UIUtilsTest extends BaseMockTest {

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testConstructorIsPrivate() throws NoSuchMethodException, IllegalAccessException,
            InstantiationException, InvocationTargetException {
        Constructor<UIUtils> constructor = UIUtils.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();

    }

    @Test
    public void hideSoftKeyboard() throws Exception {
        Activity activity = mock(Activity.class);
        View view = mock(View.class);
        InputMethodManager inputMethodManager = mock(InputMethodManager.class);
        when(activity.getCurrentFocus()).thenReturn(view);
        when(activity.getSystemService(Context.INPUT_METHOD_SERVICE))
                .thenReturn(inputMethodManager);
        when(inputMethodManager.hideSoftInputFromWindow(isA(IBinder.class), anyInt()))
                .thenReturn(true);
        UIUtils.hideSoftKeyboard(activity);
    }

    @Test
    public void getSimpleDialog() throws Exception {
        Bundle mockBundle = mock(Bundle.class);
        PowerMockito.whenNew(Bundle.class).withNoArguments().thenReturn(mockBundle);
        doNothing().when(mockBundle).putString(anyString(), anyString());
        mockStatic(BaseBundle.class);

        android.support.v4.app.DialogFragment dialog = mock(SimpleDialog.class);
        PowerMockito.whenNew(SimpleDialog.class).withNoArguments().thenReturn((SimpleDialog) dialog);
        doNothing().when(dialog).setArguments(isA(Bundle.class));

        UIUtils.getSimpleDialog(null);
    }

    @Test
    public void handleErrorMessage() throws Exception {
        mockStatic(Log.class);
        AlertDialog mockDialog = mock(AlertDialog.class);
        AlertDialog.Builder mockBuilder = mock(AlertDialog.Builder.class);
        Context mockContext = mock(Context.class);
        PowerMockito.whenNew(AlertDialog.Builder.class).withAnyArguments()
                .thenReturn(mockBuilder);
        when(mockBuilder.setTitle(anyString())).thenReturn(mockBuilder);
        when(mockBuilder.setMessage(anyString())).thenReturn(mockBuilder);
        when(mockBuilder.setPositiveButton(anyString(), isA(DialogInterface.OnClickListener.class)))
                .thenReturn(mockBuilder);
        when(mockBuilder.show()).thenReturn(mockDialog);
        UIUtils.handleErrorMessage(mockContext, 0, "");
    }

    @Test
    public void handleVolleyErrorResponse() throws Exception {

    }

}