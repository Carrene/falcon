package de.netalic.falcon.uiTest;

import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.User;
import de.netalic.falcon.ui.authentication.phoneconfirmation.PhoneConfirmationActivity;
import de.netalic.falcon.ui.authentication.phoneconfirmation.PhoneConfirmationFragment;
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
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class PhoneConfirmationScreenTest {

    @Rule
    public IntentsTestRule<PhoneConfirmationActivity> mActivityTestRule = new IntentsTestRule<>(PhoneConfirmationActivity.class, false, false);

    User mUser = new User("989121234567");

    @Test
    public void fillForm_navigateToNext() {

        launchPhoneConfirmationActivity(mUser);
        onView(withId(R.id.edittext_phoneconfirmation_receivecode)).perform(typeText("123456"));
        onView(withText("Please wait")).check(matches(isDisplayed()));
        onView(withId(android.R.id.button1)).perform(click());

    }

    @Test
    public void phoneNumber_showOnPage() {

        launchPhoneConfirmationActivity(mUser);
        onView(withId(R.id.textview_phoneconfirmation_number)).check(matches(withText("989121234567")));
        onView(withId(R.id.textview_phoneconfirmation_timer)).check(matches(not(withText("RESEND"))));
    }

    @Test
    public void noPhoneConfirmation_showError() {

        launchPhoneConfirmationActivity(mUser);
        onView(withId(R.id.menu_phoneconfirmation_done)).perform(click());
        onView(withId(R.id.textinputlayout_phoneconfirmation_receivecode)).check(matches(Util.hasTextInputLayoutHintText(mActivityTestRule.getActivity().getString(R.string.phoneconfirmation_Pleasefillbox))));
        onView(withId(R.id.textview_phoneconfirmation_timer)).check(matches(isDisplayed()));
    }

    private void launchPhoneConfirmationActivity(@Nullable User user) {

        Intent intent = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), PhoneConfirmationActivity.class);
        intent.putExtra(PhoneConfirmationActivity.ARGUMENT_USER, user);
        mActivityTestRule.launchActivity(intent);
    }

    @Test
    public void resendActivation_showTimer() {

        PhoneConfirmationFragment.waitingTime = 2000;
        launchPhoneConfirmationActivity(mUser);
        onView(withId(R.id.textview_phoneconfirmation_timer)).check(matches(not(withText("RESEND"))));
        SystemClock.sleep(PhoneConfirmationFragment.waitingTime * 2);
        onView(withId(R.id.textview_phoneconfirmation_timer)).check(matches(withText("RESEND")));
    }

    @Test
    public void changeNumber_navigatePrevious() {

        launchPhoneConfirmationActivity(mUser);
        onView(withId(R.id.textview_phoneconfirmation_changenumber)).perform(click());
        intended(hasComponent(RegistrationActivity.class.getName()));
    }
}