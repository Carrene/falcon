package de.netalic.falcon.util;

import android.content.Context;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import de.netalic.falcon.R;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.security.AccessController.getContext;

public final class SnackbarUtil {

    public static void showSnackbar(View view, String string, Context context) {

        checkNotNull(getContext());
        Snackbar snackbar = Snackbar.make(view, string, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        snackbar.show();
    }
}
