package com.sonda.emsysmobile.ui.eventdetail;

import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import com.github.clans.fab.FloatingActionButton;
import com.sonda.emsysmobile.R;


/**
 * Created by Pape on 7/11/2016.
 */

public class EventDetailsViewTest extends ActivityInstrumentationTestCase2<EventDetailsView> {

    private EventDetailsView mTestActivity;
    private FloatingActionButton mReportTimeButton;

    public EventDetailsViewTest() {
        super(EventDetailsView.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Starts the activity under test using
        // the default Intent with:
        // action = {@link Intent#ACTION_MAIN}
        // flags = {@link Intent#FLAG_ACTIVITY_NEW_TASK}
        // All other fields are null or empty.
        mTestActivity = getActivity();
        mReportTimeButton = (FloatingActionButton) mTestActivity
                .findViewById(R.id.button_report_time);
    }

    /**
     * Test if your test fixture has been set up correctly.
     * You should always implement a test that
     * checks the correct setup of your test fixture.
     * If this tests fails all other tests are
     * likely to fail as well.
     */
    public void testPreconditions() {
        // Try to add a message to add context to your assertions.
        // These messages will be shown if
        // a tests fails and make it easy to
        // understand why a test failed
        assertNotNull("mTestActivity is null", mTestActivity);
        assertNotNull("mTestEmptyText is null", mReportTimeButton);
    }

    @UiThreadTest
    public void testReportTime(){
        mReportTimeButton.callOnClick();
    }
}
