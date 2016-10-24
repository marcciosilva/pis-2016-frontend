package com.sonda.emsysmobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.toolbox.Volley;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Log.class, Handler.class, Looper.class, TextUtils.class, PreferenceManager.class })
public abstract class BaseMockTest {

    protected Context           context           = mock(Context.class);
    protected SharedPreferences sharedPreferences = mock(SharedPreferences.class);

    @Before
    public void setUp() throws Exception {
        mockStatic(Looper.class);
        mockStatic(Log.class);
        mockStatic(Handler.class);
        mockStatic(TextUtils.class);
        mockStatic(PreferenceManager.class);
        mockStatic(android.net.Uri.class);
        mockStatic(Location.class);

        when(PreferenceManager.getDefaultSharedPreferences(any(Context.class))).thenReturn(sharedPreferences);
        when(Looper.getMainLooper()).thenReturn(null);
        PowerMockito.whenNew(Handler.class).withAnyArguments().thenReturn(null);

        Answer<?> logAnswer = new Answer<Void>() {
            @Override public Void answer(InvocationOnMock invocation) throws Throwable {
                final String tag = (String)invocation.getArguments()[0];
                final String msg = (String)invocation.getArguments()[1];
                System.out.println(invocation.getMethod().getName().toUpperCase() + "/[" + tag + "] " + msg);
                return null;
            }
        };
        PowerMockito.doAnswer(logAnswer).when(Log.class, "d", anyString(), anyString());
        PowerMockito.doAnswer(logAnswer).when(Log.class, "i", anyString(), anyString());
        PowerMockito.doAnswer(logAnswer).when(Log.class, "w", anyString(), anyString());
        PowerMockito.doAnswer(logAnswer).when(Log.class, "e", anyString(), anyString());
        PowerMockito.doAnswer(logAnswer).when(Log.class, "wtf", anyString(), anyString());

        PowerMockito.doAnswer(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                final String s = (String)invocation.getArguments()[0];
                return s == null || s.length() == 0;
            }
        }).when(TextUtils.class, "isEmpty", anyString());

        when(sharedPreferences.getString(anyString(), anyString())).thenReturn("");
        when(sharedPreferences.getLong(anyString(), anyLong())).thenReturn(0L);
        when(sharedPreferences.getInt(anyString(), anyInt())).thenReturn(0);
        when(sharedPreferences.getBoolean(anyString(), anyBoolean())).thenReturn(false);
        when(sharedPreferences.getFloat(anyString(), anyFloat())).thenReturn(0f);
        when(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPreferences);
        when(context.getPackageName()).thenReturn("com.sonda.emsysmobile");
    }
}

//import android.content.Context;
//import android.content.SharedPreferences;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Handler;
//import android.os.Looper;
//import android.preference.PreferenceManager;
//import android.text.TextUtils;
//import android.util.Log;
//
//import com.android.volley.Request;
//import com.sonda.emsysmobile.BuildConfig;
//import com.sonda.emsysmobile.backendcommunication.AppRequestQueue;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.mockito.invocation.InvocationOnMock;
//import org.mockito.stubbing.Answer;
//import org.powermock.api.mockito.PowerMockito;
//import org.powermock.core.classloader.annotations.PrepareForTest;
//import org.powermock.modules.junit4.PowerMockRunner;
//
//import static android.R.id.list;
//import static org.junit.Assert.*;
//import static org.mockito.Matchers.any;
//import static org.mockito.Matchers.anyBoolean;
//import static org.mockito.Matchers.anyFloat;
//import static org.mockito.Matchers.anyInt;
//import static org.mockito.Matchers.anyList;
//import static org.mockito.Matchers.anyLong;
//import static org.mockito.Matchers.anyString;
//import static org.mockito.Matchers.eq;
//import static org.mockito.Mockito.when;
//import static org.mockito.MockitoAnnotations.initMocks;
//import static org.powermock.api.mockito.PowerMockito.mock;
//import static org.powermock.api.mockito.PowerMockito.mockStatic;
//
///**
// * Created by marccio on 12-Oct-16.
// */
//@RunWith(PowerMockRunner.class)
//@PrepareForTest({ Log.class, Handler.class, Looper.class, TextUtils.class, PreferenceManager.class })
//public class EventsMapPresenterTest {
//
//
//    protected Context mMockContext = mock(Context.class);;
//
//    protected SharedPreferences sharedPreferences = mock(SharedPreferences.class);
//
//    protected EventsMapView mMapView;
//
//    @Before
//    public void setUp() throws Exception {
//        mMapView = EventsMapView.getInstance();
//        mockStatic(Looper.class);
//        mockStatic(Log.class);
//        mockStatic(Handler.class);
//        mockStatic(TextUtils.class);
//        mockStatic(PreferenceManager.class);
//        mockStatic(Uri.class);
//        try {
//            Mockito.doThrow(new RuntimeException()).when(any(AppRequestQueue.class)).addToRequestQueue(any(Request.class));
//        } catch (RuntimeException e) {
//        }
////        when(sharedPreferences.getString(eq("backendUrl"), anyString()))
////                .thenReturn(BuildConfig.BASE_MOCK_URL);
//
//        when(PreferenceManager.getDefaultSharedPreferences(any(Context.class))).thenReturn(sharedPreferences);
//        when(Looper.getMainLooper()).thenReturn(null);
//        PowerMockito.whenNew(Handler.class).withAnyArguments().thenReturn(null);
//
//        Answer<?> logAnswer = new Answer<Void>() {
//            @Override public Void answer(InvocationOnMock invocation) throws Throwable {
//                final String tag = (String)invocation.getArguments()[0];
//                final String msg = (String)invocation.getArguments()[1];
//                System.out.println(invocation.getMethod().getName().toUpperCase() + "/[" + tag + "] " + msg);
//                return null;
//            }
//        };
//        PowerMockito.doAnswer(logAnswer).when(any(Log.class), eq("d"), anyString(), anyString());
//        PowerMockito.doAnswer(logAnswer).when(any(Log.class), eq("i"), anyString(), anyString());
//        PowerMockito.doAnswer(logAnswer).when(any(Log.class), eq("w"), anyString(), anyString());
//        PowerMockito.doAnswer(logAnswer).when(any(Log.class), eq("e"), anyString(), anyString());
//        PowerMockito.doAnswer(logAnswer).when(any(Log.class), eq("wtf"), anyString(), anyString());
//
//        PowerMockito.doAnswer(new Answer<Boolean>() {
//            @Override
//            public Boolean answer(InvocationOnMock invocation) throws Throwable {
//                final String s = (String)invocation.getArguments()[0];
//                return s == null || s.length() == 0;
//            }
//        }).when(TextUtils.class, eq("isEmpty"), anyString());
//
//        when(sharedPreferences.getString(anyString(), anyString())).thenReturn("");
//        when(sharedPreferences.getLong(anyString(), anyLong())).thenReturn(0L);
//        when(sharedPreferences.getInt(anyString(), anyInt())).thenReturn(0);
//        when(sharedPreferences.getBoolean(anyString(), anyBoolean())).thenReturn(false);
//        when(sharedPreferences.getFloat(anyString(), anyFloat())).thenReturn(0f);
//        when(mMockContext.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPreferences);
//        when(mMockContext.getPackageName()).thenReturn("org.smssecure.smssecure");
//    }
//
//    @Test
//    public void loadEvents() throws Exception {
//        EventsMapPresenter.loadEvents(mMockContext, mMapView);
//        Mockito.doNothing().when(mMapView).updateEventData(anyList());
//    }
//
//}