package de.netalic.falcon.uiTest;

import android.support.test.espresso.IdlingResource;
import android.view.View;

public class ViewEnabledIdlingResource implements IdlingResource {

    private final View view;
    private ResourceCallback resourceCallback;

    public ViewEnabledIdlingResource(View view) {

        this.view = view;
    }

    @Override
    public String getName() {

        return ViewEnabledIdlingResource.class.getName() + ":" + view.getId();
    }

    @Override
    public boolean isIdleNow() {

        boolean idle = view.isEnabled();
        if (idle) {
            resourceCallback.onTransitionToIdle();
        }
        return idle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {

        this.resourceCallback = resourceCallback;
    }
}