package de.netalic.falcon.uiTest;


import android.os.SystemClock;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.netalic.falcon.MyApp;
import de.netalic.falcon.R;
import de.netalic.falcon.ui.dashboard.DashboardActivity;
import de.netalic.falcon.ui.registration.authnticationdefinition.AuthenticationDefinitionActivity;
import nuesoft.helpdroid.network.SharedPreferencesJwtPersistor;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AuthenticationDefinitionScreenTest {

    @Rule
    public IntentsTestRule<AuthenticationDefinitionActivity> mActivityTestRule = new IntentsTestRule<>(AuthenticationDefinitionActivity.class);

    @BeforeClass
    public static void injectJwtToken() {

        SharedPreferencesJwtPersistor sharedPreferencesJwtPersistor = new SharedPreferencesJwtPersistor(MyApp.getInstance().getApplicationContext());
        sharedPreferencesJwtPersistor.save("eyJhbGciOiJIUzI1NiIsImlhdCI6MTUzNjEyOTMzMCwiZXhwIjoxNTM2MjE1NzMwfQ.eyJpZCI6MiwicGhvbmUiOiI5ODExMTEiLCJyb2xlcyI6WyJjbGllbnQiXSwic2Vzc2lvbklkIjoiMDI1ODNkZDgtNzQyZi00NDVmLWE3MDItNjdjODJiMGNlM2UxIiwiZW1haWwiOm51bGwsImlzQWN0aXZlIjpmYWxzZSwiYmFzZUN1cnJlbmN5IjoiVVNEIiwiYmFzZUN1cnJlbmN5U3ltYm9sIjoiJCJ9.NQpU-AsOnkbAmLXUhQtxzW9IN7f3O1GfM5MvGoR-3Mw");

    }

    //TODO:(Ehsan) find a way for UI test of pattern view
    @Test
    public void invalidPattern_showError() {

    }

    @Test
    public void invalidPassword_confirmNotActive() {

        onView(withId(R.id.radiobutton_authenticationdefinition_password)).perform(click());
        onView(withId(R.id.edittext_authenticationdefinition_entercode)).perform(typeText("mockpassword"));
        onView(withId(R.id.edittext_authenticationdefinition_confirmcode)).check(matches(not(isEnabled())));
    }

    @Test
    public void invalidPassword_confirmError() {

        onView(withId(R.id.radiobutton_authenticationdefinition_password)).perform(click());
        onView(withId(R.id.edittext_authenticationdefinition_entercode)).perform(typeText("Mock123@"));
        onView(withId(R.id.edittext_authenticationdefinition_confirmcode)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.edittext_authenticationdefinition_confirmcode)).perform(typeText("MockNotSame"));
        onView(withId(R.id.textinputlayout_authenticationdefinition_confirmpasscode)).check(matches(Util.hasTextInputLayoutErrorText(mActivityTestRule.getActivity().getString(R.string.authenticationdefinition_notmatch))));
    }

    // Method typeText does not work here, so replaceText is used
    @Test
    public void inputPassword_navigateNext() {

        onView(withId(R.id.radiobutton_authenticationdefinition_password)).perform(click());
        ViewEnabledIdlingResource idlingResource = new ViewEnabledIdlingResource(mActivityTestRule.getActivity().findViewById(R.id.edittext_authenticationdefinition_confirmcode));
        onView(withId(R.id.edittext_authenticationdefinition_entercode)).perform(click()).perform(replaceText("Mock123@"));
        IdlingRegistry.getInstance().register(idlingResource);
        onView(withId(R.id.edittext_authenticationdefinition_confirmcode)).check(matches(isEnabled()));
        onView(withId(R.id.edittext_authenticationdefinition_confirmcode)).perform(click()).perform(replaceText("Mock123@"));
        intended(hasComponent(DashboardActivity.class.getName()));
        Espresso.unregisterIdlingResources(idlingResource);
    }
}