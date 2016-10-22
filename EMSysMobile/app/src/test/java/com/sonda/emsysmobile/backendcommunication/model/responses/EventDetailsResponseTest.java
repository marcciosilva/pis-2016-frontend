package com.sonda.emsysmobile.backendcommunication.model.responses;

import com.sonda.emsysmobile.logic.model.core.CategoryDto;
import com.sonda.emsysmobile.logic.model.core.CategoryPriority;
import com.sonda.emsysmobile.logic.model.core.EventDto;
import com.sonda.emsysmobile.logic.model.core.ExtensionDto;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by marccio on 10/22/16.
 */
public class EventDetailsResponseTest {

    private EventDto mEventDto;
    private EventDetailsResponse mEventDetailsResponse;

    @Before
    public void setUp() throws Exception {
        mEventDto =
                new EventDto(0, "informant", "phone", new Date(1992, 1, 1), new Date(1992, 1, 2),
                        true,
                        "origin", "sectorCode", "street", "corner", "number", "department", new
                        ArrayList<ExtensionDto>(),
                        new CategoryDto(0, "code", "key", CategoryPriority.HIGH, true)
                        , 0.0, 0.0);
        mEventDetailsResponse = new EventDetailsResponse(mEventDto);
    }

    @Test
    public void getEvent() throws Exception {
        assertTrue(mEventDetailsResponse.getEvent()
                .equals(new EventDto(0, "informant", "phone", new Date(1992, 1, 1),
                        new Date(1992, 1, 2),
                        true,
                        "origin", "sectorCode", "street", "corner", "number", "department", new
                        ArrayList<ExtensionDto>(),
                        new CategoryDto(0, "code", "key", CategoryPriority.HIGH, true)
                        , 0.0, 0.0)));
    }

}