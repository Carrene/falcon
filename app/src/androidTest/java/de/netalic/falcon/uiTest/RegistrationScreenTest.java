package de.netalic.falcon.uiTest;

import android.support.design.widget.TextInputLayout;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.authentication.phoneconfirmation.PhoneConfirmationActivity;
import de.netalic.falcon.ui.authentication.registration.RegistrationActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RegistrationScreenTest {

    @Rule
    public IntentsTestRule<RegistrationActivity> mActivityTestRule = new IntentsTestRule<>(RegistrationActivity.class);

    @Test
    public void noSelectedCountry_showError() {

        onView(withId(R.id.menu_registration_done)).perform(click());
        onView(withId(R.id.textinputlayout_registration_countrypicker)).check(matches(Util.hasTextInputLayoutHintText(mActivityTestRule.getActivity().getString(R.string.registration_pleasepickcountry))));
    }

    @Test
    public void emptyPhone_showError() {

        onView(withId(R.id.edittext_registration_countrypicker)).perform(click());
        onView(withText("Albania")).perform(click());
        onView(withId(R.id.edittext_registration_countrypicker)).check(matches(withText("Albania")));
        onView(withId(R.id.menu_registration_done)).perform(click());
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText("Please fill your number"))).check(matches(isDisplayed()));
    }

    @Test
    public void fillForm_navigateNext() {

        onView(withId(R.id.edittext_registration_countrypicker)).perform(click());
        onView(withText("Albania")).perform(click());
        onView(withId(R.id.edittext_registration_countrypicker)).check(matches(withText("Albania")));
        onView(withId(R.id.edittext_registration_phonenumber)).perform(typeText("9121234567"));
        onView(withId(R.id.menu_registration_done)).perform(click());
        intended(hasComponent(PhoneConfirmationActivity.class.getName()));
    }

}