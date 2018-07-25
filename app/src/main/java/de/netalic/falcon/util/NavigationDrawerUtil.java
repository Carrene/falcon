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
import de.netalic.falcon.view.ChargeActivity;
import de.netalic.falcon.view.DashboardActivity;
import nuesoft.helpdroid.network.SharedPreferencesJwtPersistor;
import nuesoft.helpdroid.util.Parser;

public class NavigationDrawerUtil {

    public static Drawer getDrawer(final Activity activity, Toolbar toolbar, int identifier) {

        PrimaryDrawerItem item1 = new CustomPrimaryDrawerItem().withIdentifier(1).withName(R.string.navigation_dashboard).withIcon(R.drawable.navigation_dashboard);
        PrimaryDrawerItem item2 = new CustomPrimaryDrawerItem().withIdentifier(2).withName(R.string.navigation_charge).withIcon(R.drawable.navigation_charge);
        PrimaryDrawerItem item3 = new CustomPrimaryDrawerItem().withIdentifier(3).withName(R.string.navigation_requesttoreceive).withIcon(R.drawable.navigation_requesttoreceive);
        PrimaryDrawerItem item4 = new CustomPrimaryDrawerItem().withIdentifier(4).withName(R.string.navigation_walletaddress).withIcon(R.drawable.navigation_wallet);
        PrimaryDrawerItem item5 = new CustomPrimaryDrawerItem().withIdentifier(5).withName(R.string.navigation_transactionhistory).withIcon(R.drawable.navigation_transaction);
        PrimaryDrawerItem item6 = new CustomPrimaryDrawerItem().withIdentifier(7).withName(R.string.navigation_chart).withIcon(R.drawable.navigation_chart);
        PrimaryDrawerItem item7 = new CustomPrimaryDrawerItem().withIdentifier(8).withName(R.string.navigation_setting).withIcon(R.drawable.navigation_setting);
        PrimaryDrawerItem item8 = new CustomPrimaryDrawerItem().withIdentifier(9).withName(R.string.navigation_help).withIcon(R.drawable.navigation_help);

        //TODO: Inject this object
        SharedPreferencesJwtPersistor sharedPreferencesJwtPersistor = new SharedPreferencesJwtPersistor(MyApp.getInstance().getApplicationContext());
        Map<String, Object> tokenBody = Parser.getTokenBody(sharedPreferencesJwtPersistor.get());
        String phone = (String) tokenBody.get("phone");
        String email = (String) tokenBody.get("email");

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.color.primary).withSelectionListEnabledForSingleProfile(false)
                .addProfiles(new ProfileDrawerItem().withName(phone).withEmail(email).withIcon(R.drawable.navigation_charge))
                .build();

        Drawer result = new DrawerBuilder()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withCloseOnClick(true)
                .withSelectedItem(identifier)
                .addDrawerItems(item1, item2, item3, item4, item5, item6, item7, item8)
                .withAccountHeader(headerResult)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    if (position != identifier) {
                        Intent intent = null;
                        switch (position) {

                            case 1: {
                                intent = new Intent(activity, DashboardActivity.class);

                                activity.startActivity(intent);
                                break;
                            }

                            case 2: {
                                intent = new Intent(activity, ChargeActivity.class);
                                activity.startActivity(intent);
                                break;
                            }
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