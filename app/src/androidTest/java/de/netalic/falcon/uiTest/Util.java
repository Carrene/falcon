package de.netalic.falcon.uiTest;

import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.test.espresso.matcher.BoundedMatcher;

import com.andrognito.patternlockview.PatternLockView;
import com.google.android.material.textfield.TextInputLayout;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class Util {

    private PatternLockView mPatternLockView;

    public static Matcher<View> hasTextInputLayoutErrorText(final String expectedErrorText) {

        return new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {

                if (!(view instanceof TextInputLayout)) {
                    return false;
                }

                CharSequence error = ((TextInputLayout) view).getError();

                if (error == null) {
                    return false;
                }

                String hint = error.toString();
                return expectedErrorText.equals(hint);
            }

            @Override
            public void describeTo(Description description) {

            }
        };
    }

    public static Matcher<View> withTextColor(final int expectedId) {

        return new BoundedMatcher<View, TextView>(TextView.class) {

            @Override
            protected boolean matchesSafely(TextView textView) {

                int colorId = ContextCompat.getColor(textView.getContext(), expectedId);
                return textView.getCurrentTextColor() == colorId;
            }

            @Override
            public void describeTo(Description description) {

                description.appendText("with text color: ");
                description.appendValue(expectedId);
            }
        };
    }


}
