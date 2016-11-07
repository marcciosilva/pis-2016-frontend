package com.sonda.emsysmobile.ui.activities.login;

import android.support.test.espresso.action.TypeTextAction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.sonda.emsysmobile.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * Created by Pape on 7/11/2016.
 */

@RunWith(AndroidJUnit4.class)
public class AuthActivityTest {

    @Rule
    public ActivityTestRule<AuthActivity> mActivityRule = new ActivityTestRule(AuthActivity.class);

    @Test
    public void authTest() {
        onView(withId(R.id.input_username)).perform(typeText("a"));
        onView(withId(R.id.input_password)).perform(typeText("a"));
        onView(withId(R.id.button_login)).perform(click());
    }

}
