package de.netalic.falcon.uiTest;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.registration.phoneconfirmation.PhoneConfirmationActivity;
import de.netalic.falcon.ui.registration.phoneinput.PhoneInputActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

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