package com.sonda.emsysmobile.backendcommunication;

import android.net.Uri;
import android.os.Parcel;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.JsonArray;
import com.sonda.emsysmobile.BaseMockTest;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by marccio on 10/22/16.
 */
@PrepareForTest(RequestFactory.class)
public class RequestFactoryTest extends BaseMockTest {

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void genericGETRequest() throws Exception {
        JsonArrayRequest mockRequest = mock(JsonArrayRequest.class);
        PowerMockito.whenNew(JsonArrayRequest.class).withAnyArguments().thenReturn(mockRequest);
        RequestFactory.genericGETRequest("test_url", null, null);
    }

}