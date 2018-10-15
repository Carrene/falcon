package de.netalic.falcon.ui.base;

import java.util.List;

import de.netalic.falcon.data.model.Currency;

public interface BaseView<T> {

    void setPresenter(T presenter);

    void showProgressBar();

    void dismissProgressBar();

}
