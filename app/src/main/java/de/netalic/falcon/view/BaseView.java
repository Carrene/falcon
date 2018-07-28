package de.netalic.falcon.view;

public interface BaseView<T> {

    void setPresenter(T presenter);

    void showProgressBar();

    void dismissProgressBar();
}
