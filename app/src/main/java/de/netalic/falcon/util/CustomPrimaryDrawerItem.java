package de.netalic.falcon.util;


import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

import de.netalic.falcon.R;

class CustomPrimaryDrawerItem extends PrimaryDrawerItem {

    CustomPrimaryDrawerItem() {

        withIconTintingEnabled(true).withSelectedIconColorRes(R.color.colorSecondary).withSelectedTextColorRes(R.color.colorSecondary).withSelectedColorRes(R.color.colorSecondaryDark25);
    }
}