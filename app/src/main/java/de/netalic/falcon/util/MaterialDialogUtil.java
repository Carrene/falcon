package de.netalic.falcon.util;

import android.app.Activity;

import com.afollestad.materialdialogs.MaterialDialog;

import de.netalic.falcon.R;

public final class  MaterialDialogUtil {

    private static MaterialDialog sDialog;

    public static void showMaterialDialog(Activity activity){

        MaterialDialog.Builder mMaterialDialogBuilder=new MaterialDialog.Builder(activity)
                .title(activity.getString(R.string.materialdialogutil_pleasewait))
                .content(activity.getString(R.string.materialdialogutil_isloading))
                .progress(true,R.dimen.max_materialutil_materialutil)
                .cancelable(false);

        sDialog=mMaterialDialogBuilder.build();

        sDialog.show();

    }

    public static void disMissMaterialDialog(){

        sDialog.dismiss();


    }
}
