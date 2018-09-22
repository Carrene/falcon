package de.netalic.falcon.uiTest;

import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.DrawerMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.Gravity;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.netalic.falcon.MyApp;
import de.netalic.falcon.R;
import de.netalic.falcon.ui.dashboard.DashboardActivity;
import nuesoft.helpdroid.network.SharedPreferencesJwtPersistor;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class DashboardScreenTest {

    @Rule
    public IntentsTestRule<DashboardActivity> mActivityTestRule = new IntentsTestRule<>(DashboardActivity.class);

    @BeforeClass
    public static void injectJwtToken() {

        SharedPreferencesJwtPersistor sharedPreferencesJwtPersistor = new SharedPreferencesJwtPersistor(MyApp.getInstance().getApplicationContext());
        sharedPreferencesJwtPersistor.save("eyJhbGciOiJIUzI1NiIsImlhdCI6MTUzNjEyOTMzMCwiZXhwIjoxNTM2MjE1NzMwfQ.eyJpZCI6MiwicGhvbmUiOiI5ODExMTEiLCJyb2xlcyI6WyJjbGllbnQiXSwic2Vzc2lvbklkIjoiMDI1ODNkZDgtNzQyZi00NDVmLWE3MDItNjdjODJiMGNlM2UxIiwiZW1haWwiOm51bGwsImlzQWN0aXZlIjpmYWxzZSwiYmFzZUN1cnJlbmN5IjoiVVNEIiwiYmFzZUN1cnJlbmN5U3ltYm9sIjoiJCJ9.NQpU-AsOnkbAmLXUhQtxzW9IN7f3O1GfM5MvGoR-3Mw");

    }

    @Test
    public void navigationDrawer_isClosed() {

        onView(withId(R.id.material_drawer_layout)).check(matches(DrawerMatchers.isClosed(Gravity.LEFT)));

    }

    @Test
    public void toolbarTitle_isCorrect() {

//        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
//                .check(matches(withText(R.string.navigation_dashboard)));
    }

    @Test
    public void navigationDrawer_userProfile() {

        onView(withId(R.id.material_drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.material_drawer_account_header_name)).check(matches(withText("981111")));
        onView(withId(R.id.material_drawer_account_header_email)).check(matches(withText("Email not set")));
    }


    @Test
    public void navigationDrawer_dashboardIsSelected() {

        onView(withId(R.id.material_drawer_layout)).perform(DrawerActions.open());
        onView(allOf(withText("Dashboard"), isDescendantOfA(withId(R.id.material_drawer_recycler_view)))).check(matches(Util.withTextColor(R.color.colorSecondaryDark)));
    }

    public void purchaseButton_isShown(){
//        onView(withId(R.id.ima))
    }

}


