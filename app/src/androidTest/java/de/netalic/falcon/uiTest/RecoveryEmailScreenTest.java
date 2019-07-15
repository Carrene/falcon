package de.netalic.falcon.uiTest;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.User;
import de.netalic.falcon.ui.registration.authnticationdefinition.AuthenticationDefinitionActivity;
import de.netalic.falcon.ui.registration.recoveryemail.RecoveryEmailActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class RecoveryEmailScreenTest {

    @Rule
    public IntentsTestRule<RecoveryEmailActivity> mActivityTestRule = new IntentsTestRule<>(RecoveryEmailActivity.class, false, false);


    @Test
    public void noInputEmail_showError() {

        launchRecoveryEmailActivity(new User("981234567"));
        onView(withId(R.id.menu_everywhere_done)).perform(click());
        onView(withId(R.id.textinputlayout_recoveryemail_enterrecoveryemail)).check(matches(Util.hasTextInputLayoutErrorText(mActivityTestRule.getActivity().getString(R.string.recoveryemail_thisemaildoesnotexist))));
    }

    @Test
    public void inputWrongEmail_showError() {

        launchRecoveryEmailActivity(new User("981234567"));
        onView(withId(R.id.edittext_recoveryemail_enterrecoveryemail)).perform(typeText("mockEmail.com"));
        onView(withId(R.id.menu_everywhere_done)).perform(click());
        onView(withId(R.id.textinputlayout_recoveryemail_enterrecoveryemail)).check(matches(Util.hasTextInputLayoutErrorText(mActivityTestRule.getActivity().getString(R.string.recoveryemail_thisemaildoesnotexist))));
    }

    @Test
    public void inputCorrectEmail_navigateNext() {

        launchRecoveryEmailActivity(new User("981234567"));
        onView(withId(R.id.edittext_recoveryemail_enterrecoveryemail)).perform(typeText("mockEmail@mock.com"));
        onView(withId(R.id.menu_everywhere_done)).perform(click());
        intended(hasComponent(AuthenticationDefinitionActivity.class.getName()));
    }

    @Test
    public void skipEmail_navigateNext() {

        launchRecoveryEmailActivity(new User("981234567"));
        onView(withId(R.id.textview_recoveryemail_skip)).perform(click());
        intended(hasComponent(AuthenticationDefinitionActivity.class.getName()));
    }

    private void launchRecoveryEmailActivity(@Nullable User user) {

        Intent intent = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), RecoveryEmailActivity.class);
        intent.putExtra(RecoveryEmailActivity.ARGUMENT_USER, user);
        mActivityTestRule.launchActivity(intent);
    }
}
