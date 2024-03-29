package com.sonda.emsysmobile.backendcommunication.model.responses;

import com.sonda.emsysmobile.logic.model.core.CategoryDto;
import com.sonda.emsysmobile.logic.model.core.CategoryPriority;
import com.sonda.emsysmobile.logic.model.core.EventDto;
import com.sonda.emsysmobile.logic.model.core.ExtensionDto;
import com.sonda.emsysmobile.logic.model.core.ExtensionState;
import com.sonda.emsysmobile.logic.model.core.ZoneDto;
import com.sonda.emsysmobile.logic.model.core.attachments.GeolocationDto;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by marccio on 10/5/16.
 */
public class EventsResponseTest {

    private int mIdentifier;
    private String mInformant;
    private String mPhone;
    private Date mTimeStamp;
    private Date mCreatedDate;
    private boolean mInProcess;
    private String mOrigin;
    private String mSectorCode;
    private String mStreet;
    private String mCorner;
    private String mNumber;
    private String mDepartment;
    private double mLatitude;
    private double mLongitude;
    private List<ExtensionDto> mExtensions;
    // Category.
    private CategoryDto mCategory;
    private int mCategoryIdentifier;
    private String mCategoryCode;
    private String mCategoryKey;
    private CategoryPriority mCategoryPriority;
    private boolean mCategoryIsActive;
    // Extension.
    private ExtensionDto mExtensionDto;
    private int mExtensionIdentifier;
    private String mExtensionDescription;
    private ExtensionState mExtensionState;
    private Date mExtensionTimeStamp;
    private CategoryDto mExtensionCategory;
    private ZoneDto mExtensionZone;
    private transient EventDto mExtensionEvent;
    // Extension > ZoneDto.
    private String mExtensionZoneName;
    private int mExtensionZoneIdentifier;
    private String mExtensionZoneExecUnitName;
    EventsResponse mEventsResponse;
    private List<EventDto> mEvents;
    private List<String> mExtensionResources;

    @Before
    public void setUp() throws Exception {
        mIdentifier = 1;
        mInformant = "mInformant";
        mPhone = "mPhone";
        mTimeStamp = new Date(2016, 10, 5);
        mCreatedDate = new Date(2016, 10, 5);
        mInProcess = true;
        mOrigin = "mOrigin";
        mSectorCode = "mSectorCode";
        mStreet = "mStreet";
        mCorner = "mCorner";
        mNumber = "mNumber";
        mDepartment = "mDepartment";
        mLatitude = -34.905743;
        mLongitude = -56.198887;
        mExtensions = new ArrayList<>();
        // Category.
        mCategoryIdentifier = 1;
        mCategoryCode = "categoryCode";
        mCategoryKey = "categoryKey";
        mCategoryPriority = CategoryPriority.HIGH;
        mCategoryIsActive = true;
        mCategory = new CategoryDto(mCategoryIdentifier, mCategoryCode, mCategoryKey,
                mCategoryPriority, mCategoryIsActive);
        // Extension.
        mExtensionIdentifier = 1;
        mExtensionDescription = "description";
        mExtensionState = ExtensionState.CLOSED;
        mExtensionTimeStamp = new Date(2016, 10, 5);
        mExtensionCategory = mCategory;
        mExtensionZoneName = "zone1";
        mExtensionZoneIdentifier = 1;
        mExtensionZoneExecUnitName = "ue1";
        mExtensionZone = new ZoneDto(mExtensionZoneName, mExtensionZoneIdentifier,
                mExtensionZoneExecUnitName);
        // EventDto.
        mExtensionResources = new ArrayList<>();
        mExtensionEvent = new EventDto(mIdentifier, mInformant, mPhone, mTimeStamp, mCreatedDate,
                mInProcess, mOrigin, mSectorCode, mStreet, mCorner, mNumber, mDepartment,
                mExtensions, mCategory, mLatitude, mLongitude);
        mExtensionDto = new ExtensionDto(mExtensionIdentifier, mExtensionDescription,
                mExtensionState, mExtensionTimeStamp, mExtensionCategory, mExtensionZone, null,
                mExtensionEvent, mExtensionResources);

        mExtensions.add(mExtensionDto);
        mEvents = new ArrayList<>();
        mEvents.add(mExtensionEvent);
        mEventsResponse = new EventsResponse(mEvents);
    }

    @Test
    public void getEvents() throws Exception {
        assertTrue(mEventsResponse.getEvents().equals(mEvents));
    }

    @Test
    public void setEvents() throws Exception {
        EventDto testEvent = new EventDto(mExtensionEvent.getIdentifier() + 1,
                mExtensionEvent.getInformant(), mExtensionEvent.getPhone(),
                mExtensionEvent.getTimeStamp(), mExtensionEvent.getCreatedDate(),
                mExtensionEvent.isInProcess(), mExtensionEvent.getOrigin(),
                mExtensionEvent.getSectorCode(), mExtensionEvent.getStreet(),
                mExtensionEvent.getCorner(), mExtensionEvent.getNumber(),
                mExtensionEvent.getDepartment(), mExtensionEvent.getExtensions(),
                mExtensionEvent.getCategory(), mExtensionEvent.getLatitude(),
                mExtensionEvent.getLongitude());
        ArrayList<EventDto> testEvents = new ArrayList<>();
        testEvents.add(testEvent);
        mEventsResponse.setEvents(testEvents);
        assertTrue(mEventsResponse.getEvents().equals(testEvents));
    }

}
