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
import de.netalic.falcon.ui.charge.ChargeActivity;
import de.netalic.falcon.ui.dashboard.DashboardActivity;
import de.netalic.falcon.ui.setting.basic.SettingActivity;
import de.netalic.falcon.ui.transaction.transactionhistory.TransactionHistoryActivity;
import de.netalic.falcon.ui.transfer.TransferActivity;
import nuesoft.helpdroid.network.SharedPreferencesJwtPersistor;
import nuesoft.helpdroid.util.Parser;

public class NavigationDrawerUtil {

    private static final String EMAIL_NOT_SET = "Email not set";

    public static Drawer getDrawer(final Activity activity, Toolbar toolbar, int identifier) {

        PrimaryDrawerItem item1 = new CustomPrimaryDrawerItem().withIdentifier(1).withName(R.string.navigation_dashboard).withIcon(R.drawable.navigation_dashboard);
        PrimaryDrawerItem item2 = new CustomPrimaryDrawerItem().withIdentifier(2).withName(R.string.navigation_charge).withIcon(R.drawable.navigation_charge);
        PrimaryDrawerItem item3 = new CustomPrimaryDrawerItem().withIdentifier(3).withName(R.string.navigation_addresses).withIcon(R.drawable.navigation_wallet);
        PrimaryDrawerItem item4 = new CustomPrimaryDrawerItem().withIdentifier(4).withName(R.string.navigation_transfer).withIcon(R.drawable.navigation_transfer);
        PrimaryDrawerItem item5 = new CustomPrimaryDrawerItem().withIdentifier(5).withName(R.string.navigation_transactionhistory).withIcon(R.drawable.navigation_transaction);
        PrimaryDrawerItem item6 = new CustomPrimaryDrawerItem().withIdentifier(6).withName(R.string.navigation_setting).withIcon(R.drawable.navigation_setting);
        PrimaryDrawerItem item7 = new CustomPrimaryDrawerItem().withIdentifier(7).withName(R.string.navigation_help).withIcon(R.drawable.navigation_help);

        //TODO(Ehsan): Inject this object
        SharedPreferencesJwtPersistor sharedPreferencesJwtPersistor = new SharedPreferencesJwtPersistor(MyApp.getInstance().getApplicationContext());
        Map<String, Object> tokenBody = Parser.getTokenBody(sharedPreferencesJwtPersistor.get());
        String phone = (String) tokenBody.get("phone");
        String email = (String) tokenBody.get("email");

        if (email == null) {
            email = EMAIL_NOT_SET;
        }

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.color.primary).withSelectionListEnabledForSingleProfile(false)
                .addProfiles(new ProfileDrawerItem().withName(phone).withEmail(email).withIcon(R.drawable.navigationheader_profile))
                .build();

        Drawer result = new DrawerBuilder()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withCloseOnClick(true)
                .withSelectedItem(identifier)
                .addDrawerItems(item1, item2, item3, item4, item5, item6, item7)
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

                            case 3:{
                                intent=new Intent(activity, AddressesActivity.class);
                                activity.startActivity(intent);
                                break;
                            }

                            case 4:{
                                intent=new Intent(activity, TransferActivity.class);
                                activity.startActivity(intent);
                                break;
                            }

                            case 5: {
                                intent = new Intent(activity, TransactionHistoryActivity.class);
                                activity.startActivity(intent);
                                break;
                            }

                            case 6:{
                                intent=new Intent(activity, SettingActivity.class);
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