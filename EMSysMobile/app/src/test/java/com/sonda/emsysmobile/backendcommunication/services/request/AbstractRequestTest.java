package com.sonda.emsysmobile.backendcommunication.services.request;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.JsonObject;
import com.sonda.emsysmobile.BaseMockTest;
import com.sonda.emsysmobile.BuildConfig;
import com.sonda.emsysmobile.backendcommunication.model.responses.EmsysResponse;
import com.sonda.emsysmobile.backendcommunication.model.responses.ErrorResponse;
import com.sonda.emsysmobile.backendcommunication.services.endpoint.EndpointService;

import org.junit.Before;
import org.junit.Test;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.lang.reflect.Type;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * Created by marccio on 10/22/16.
 */
@PrepareForTest(AbstractRequest.class)
public class AbstractRequestTest extends BaseMockTest {

    AbstractRequest<Integer> mAbstractRequest;
    private String mURL;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        EmsysResponse testResponse = new EmsysResponse();
        testResponse.setCode(1);
        mAbstractRequest = new AbstractRequest<Integer>(context, EmsysResponse.class,
                AbstractRequest.RequestType.GET) {
            @Override
            protected String getPath() {
                return "path";
            }

            @Override
            protected JsonObject getBody() {
                return new JsonObject();
            }
        };
        mAbstractRequest.setListener(new Response.Listener<Integer>() {
            @Override
            public void onResponse(Integer response) {

            }
        });
        mAbstractRequest.setErrorListener(new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    @Test
    public void execute() throws Exception {
        //Revisar, lo seteo asi para que pueda ejecutar el test
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        mURL = sharedPrefs.getString("backendUrl", BuildConfig.BASE_URL);
        EndpointService mockEndpointService = mock(EndpointService.class);
        whenNew(EndpointService.class).withAnyArguments().thenReturn(mockEndpointService);
        doNothing().when(mockEndpointService).execute(mURL, isA(AbstractRequest.RequestType.class),
                isA(String.class), isA(JsonObject.class), isA(Type.class),
                isA(Response.Listener.class), isA(Response.ErrorListener.class));
        mAbstractRequest.execute();
    }


    @Test
    public void getContext() throws Exception {
        // No se puede aseverar nada ya que el contexto es mockeado.
        // Simplemente se recorre el codigo.
        mAbstractRequest.getContext().equals(context);
    }

    @Test
    public void setContext() throws Exception {
        mAbstractRequest.setContext(null);
        assertTrue(mAbstractRequest.getContext() == null);
    }

    @Test
    public void getResponseType() throws Exception {
        assertTrue(mAbstractRequest.getResponseType().equals(EmsysResponse.class));
    }

    @Test
    public void setResponseType() throws Exception {
        mAbstractRequest.setResponseType(ErrorResponse.class);
        assertTrue(mAbstractRequest.getResponseType().equals(ErrorResponse.class));
    }

    @Test
    public void getListener() throws Exception {
        // TODO mejorar.
        mAbstractRequest.getListener().equals(new Response.Listener<Integer>() {
            @Override
            public void onResponse(Integer response) {

            }
        });
    }

    @Test
    public void setListener() throws Exception {
        Response.Listener<Integer> tmpListener = new Response.Listener<Integer>() {
            @Override
            public void onResponse(Integer response) {
                Log.d("test_tag", "test" + Integer.toString(response));
            }
        };
        mAbstractRequest.setListener(tmpListener);
        assertTrue(mAbstractRequest.getListener().equals(tmpListener));
    }

    @Test
    public void getErrorListener() throws Exception {
        // TODO mejorar.
        mAbstractRequest.getErrorListener();
        mAbstractRequest.setErrorListener(null);
        mAbstractRequest.getErrorListener().onErrorResponse(new VolleyError());
    }

    @Test
    public void setErrorListener() throws Exception {
        Response.ErrorListener tmpListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("test_tag", "test");
            }
        };
        mAbstractRequest.setErrorListener(tmpListener);
        assertTrue(mAbstractRequest.getErrorListener().equals(tmpListener));
    }

}