package de.netalic.falcon.util;

import android.content.Context;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.MaterialDialog;

import de.netalic.falcon.R;

import static com.google.common.base.Preconditions.checkNotNull;

public class MaterialDialogUtil {

    private MaterialDialog sMaterialDialog;
    private static MaterialDialogUtil sMaterialDialogUtil;

    public static MaterialDialogUtil getInstance() {

        if (sMaterialDialogUtil == null) {
            sMaterialDialogUtil = new MaterialDialogUtil();
        }
        return sMaterialDialogUtil;
    }

    private MaterialDialogUtil() {

    }

    public void showMaterialDialog(@NonNull Context context) {

        checkNotNull(context);
        MaterialDialog.Builder mMaterialDialogBuilder = new MaterialDialog.Builder(context)
                .title(context.getString(R.string.materialdialogutil_pleasewait))
                .content(context.getString(R.string.materialdialogutil_isloading))
                .progress(true, R.dimen.max_materialutil_materialutil)
                .negativeText("Cancel")
                .cancelable(false);


        sMaterialDialog = mMaterialDialogBuilder.build();
        sMaterialDialog.show();

        sMaterialDialog.getBuilder().onNegative((dialog, which) -> sMaterialDialog.dismiss());
    }

    public void dismissMaterialDialog() {

        if (sMaterialDialog != null)
            sMaterialDialog.dismiss();
    }
}
