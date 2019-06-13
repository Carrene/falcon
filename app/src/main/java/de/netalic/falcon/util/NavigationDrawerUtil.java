package de.netalic.falcon.util;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;

import java.util.Map;

import de.netalic.falcon.MyApp;
import de.netalic.falcon.R;
import de.netalic.falcon.ui.addresses.AddressesActivity;
import de.netalic.falcon.ui.contacts.ContactsActivity;
import de.netalic.falcon.ui.dashboard.DashboardActivity;
import de.netalic.falcon.ui.setting.basic.SettingActivity;
import nuesoft.helpdroid.network.SharedPreferencesJwtPersistor;
import nuesoft.helpdroid.util.Parser;

public class NavigationDrawerUtil {

    private static final String EMAIL_NOT_SET = "Email not set";

    public static Drawer getDrawer(final Activity activity, Toolbar toolbar, int identifier) {

        PrimaryDrawerItem item1 = new CustomPrimaryDrawerItem().withIdentifier(1).withName(R.string.navigation_dashboard).withIcon(R.drawable.navigation_dashboard);
        PrimaryDrawerItem item2 = new CustomPrimaryDrawerItem().withIdentifier(2).withName(R.string.navigation_wallets).withIcon(R.drawable.navigation_wallet);
        PrimaryDrawerItem item3 = new CustomPrimaryDrawerItem().withIdentifier(3).withName(R.string.navigation_contacts).withIcon(R.drawable.everywhere_contacticon);
        PrimaryDrawerItem item4 = new CustomPrimaryDrawerItem().withIdentifier(4).withName(R.string.navigation_setting).withIcon(R.drawable.navigation_setting);
        PrimaryDrawerItem item5 = new CustomPrimaryDrawerItem().withIdentifier(4).withName(R.string.navigation_support).withIcon(R.drawable.navigation_support);
        PrimaryDrawerItem item6 = new CustomPrimaryDrawerItem().withIdentifier(5).withName(R.string.navigation_aboutus).withIcon(R.drawable.navigation_aboutus);

        //TODO(Ehsan): Inject this object
        SharedPreferencesJwtPersistor sharedPreferencesJwtPersistor = new SharedPreferencesJwtPersistor(MyApp.getInstance().getApplicationContext());
//        Map<String, Object> tokenBody = Parser.getTokenBody(sharedPreferencesJwtPersistor.get());
//        String phone = (String) tokenBody.get("phone");
//        String email = (String) tokenBody.get("email");

//        if (email == null) {
//            email = EMAIL_NOT_SET;
//        }

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.color.primary).withSelectionListEnabledForSingleProfile(false)
//                .addProfiles(new ProfileDrawerItem().withName(phone).withEmail(email).withIcon(R.drawable.navigationheader_profile))
                .build();

        Drawer result = new DrawerBuilder()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withCloseOnClick(true)
                .withSelectedItem(identifier)
                .addDrawerItems(item1, item2, item3, item4, item5,item6)
                .withAccountHeader(headerResult)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {

                    Intent intent = null;
                    switch (position) {

                        case 1: {

                            if (activity.getLocalClassName().equals("de.netalic.falcon.ui.dashboard.DashboardActivity")) {


                            } else {
                                intent = new Intent(activity, DashboardActivity.class);
                                activity.startActivity(intent);
                                break;
                            }
                        }
                        case 2: {
                            if (position != identifier) {

                                intent = new Intent(activity, AddressesActivity.class);
                                activity.startActivity(intent);
                                break;
                            }
                        }
                        case 3: {

                            intent=new Intent(activity, ContactsActivity.class);
                            activity.startActivity(intent);
                            break;
                        }
                        case 4: {
                            if (position != identifier) {
                                intent = new Intent(activity, SettingActivity.class);
                                activity.startActivity(intent);
                                break;
                            }
                        }
                        case 5: {

                        }
                        case 6: {

                        }
                    }

                    return false;
                }).withOnDrawerNavigationListener(view -> {

                    activity.onBackPressed();
                    return true;
                })
                .build();
        return result;
    }
}