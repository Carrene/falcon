package de.netalic.falcon.ui.base;

public interface BaseView<T> {

    void setPresenter(T presenter);

    void showProgressBar();

    void dismissProgressBar();

}
