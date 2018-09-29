package de.netalic.falcon.uiTest;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.registration.phoneconfirmation.PhoneConfirmationActivity;
import de.netalic.falcon.ui.registration.phoneinput.PhoneInputActivity;

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
    public IntentsTestRule<PhoneInputActivity> mActivityTestRule = new IntentsTestRule<>(PhoneInputActivity.class);

    @Test
    public void noSelectedCountry_showError() {

        onView(withId(R.id.menu_phoneinput_done)).perform(click());
        onView(withId(R.id.textinputlayout_phoneinput_countrypicker)).check(matches(Util.hasTextInputLayoutErrorText(mActivityTestRule.getActivity().getString(R.string.registration_pleasepickcountry))));
    }

    @Test
    public void emptyPhone_showError() {

        onView(withId(R.id.edittext_phoneinput_countrypicker)).perform(click());
        onView(withText("Albania")).perform(click());
        onView(withId(R.id.edittext_phoneinput_countrypicker)).check(matches(withText("Albania")));
        onView(withId(R.id.menu_phoneinput_done)).perform(click());
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText("Please fill your number"))).check(matches(isDisplayed()));
    }

    @Test
    public void fillForm_navigateNext() {

        onView(withId(R.id.edittext_phoneinput_countrypicker)).perform(click());
        onView(withText("Albania")).perform(click());
        onView(withId(R.id.edittext_phoneinput_countrypicker)).check(matches(withText("Albania")));
        onView(withId(R.id.edittext_phoneinput_phonenumber)).perform(typeText("9121234567"));
        onView(withId(R.id.menu_phoneinput_done)).perform(click());
        intended(hasComponent(PhoneConfirmationActivity.class.getName()));
    }

}