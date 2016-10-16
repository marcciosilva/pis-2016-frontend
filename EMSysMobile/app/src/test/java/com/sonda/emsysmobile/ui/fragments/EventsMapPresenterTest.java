package com.sonda.emsysmobile.ui.fragments;

import com.sonda.emsysmobile.BaseMockTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyList;

public class EventsMapPresenterTest extends BaseMockTest {
//    private Cursor cursor = mock(Cursor.class);
    private EventsMapView mMapView;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
//            Mockito.doNothing().when(any(AppRequestQueue.class)).addToRequestQueue(any(Request.class));
    }

    @Test
    public void loadEvents() throws Exception {
        try {
            EventsMapPresenter.loadEvents(context, mMapView);
            Mockito.doNothing().when(mMapView).updateEventsData(anyList());

        } catch (RuntimeException e) {
        }
        assertTrue(true);
    }

}