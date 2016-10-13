package com.sonda.emsysmobile.logic.model.core;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;

/**
 * Created by marccio on 13-Oct-16.
 */
public class EventStateTest {

    @Test
    public void test() {
        EventState eventState = EventState.CREATED;
        assertFalse(eventState.equals(EventState.SENT));
    }

}