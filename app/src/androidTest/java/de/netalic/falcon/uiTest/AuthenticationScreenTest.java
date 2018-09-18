package de.netalic.falcon.uiTest;


import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Authentication;
import de.netalic.falcon.data.repository.authentication.AuthenticationRepository;
import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.ui.authentication.authentication.AuthenticationActivity;
import de.netalic.falcon.ui.dashboard.DashboardActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AuthenticationScreenTest {


    @Rule
    public IntentsTestRule<AuthenticationActivity> mActivityTestRule = new IntentsTestRule<>(AuthenticationActivity.class, false, false);

    @Test
    public void inputPassword_showError() {


        RepositoryLocator.getInstance().getRepository(AuthenticationRepository.class).update(new Authentication("mock123", Authentication.PASSWORD_TYPE), deal -> {


            if (deal.getThrowable() == null) {

                launchAuthenticationActivity();
                onView(withId(R.id.edittext_authentication_entercode)).perform(typeText("mock123456"));
                onView(withId(R.id.menu_everywhere_done)).perform(click());
                onView(withId(R.id.textinputlayout_authentication_enterpasscode))
                        .check(matches(Util.hasTextInputLayoutErrorText(mActivityTestRule.getActivity()
                                .getString(R.string.authenticationpassword_passworddoesnotmatch))));
            }
        });

    }

    @Test
    public void inputPassword_navigateNext() {

        RepositoryLocator.getInstance().getRepository(AuthenticationRepository.class).update(new Authentication("mock123", Authentication.PASSWORD_TYPE), deal -> {

            launchAuthenticationActivity();
            onView(withId(R.id.edittext_authentication_entercode)).perform(typeText("mock123"));
            onView(withId(R.id.menu_everywhere_done)).perform(click());
            intended(hasComponent(DashboardActivity.class.getName()));

        });
    }

    //TODO:(Milad) find a way for UI test of pattern view

    @Test
    public void inputPattern_showError() {

    }

    @Test
    public void inputPattern_navigateNext() {


    }


    private void launchAuthenticationActivity() {

        Intent intent = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(), AuthenticationActivity.class);
        mActivityTestRule.launchActivity(intent);
    }
}
