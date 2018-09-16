package de.netalic.falcon.uiTest;


import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Authentication;
import de.netalic.falcon.data.repository.authentication.AuthenticationRepository;
import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.ui.authentication.authentication.AuthenticationActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AuthenticationScreenTest {


    @Rule
    public IntentsTestRule<AuthenticationActivity>mActivityTestRule=new IntentsTestRule<>(AuthenticationActivity.class,false,false);

    @Test
    public  void passwordType(){


        RepositoryLocator.getInstance().getRepository(AuthenticationRepository.class).update(new Authentication("mock123",Authentication.sPasswordType),deal -> {


            if (deal.getThrowable()==null){

                launchAuthenticationActivity();
                onView(withId(R.id.edittext_authentication_entercode)).perform(typeText("mock123456"));


            }


        });

    }

    private void launchAuthenticationActivity(){

        Intent intent=new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(),AuthenticationActivity.class);
        mActivityTestRule.launchActivity(intent);
    }
}
