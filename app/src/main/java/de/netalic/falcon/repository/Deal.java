package de.netalic.falcon.repository;


import java.util.List;

import de.netalic.falcon.model.Wallet;
import retrofit2.Response;


/**
 * @param <T> This describes model type
 */

public class Deal<T> {

    private T mModel;
    private Response<T> mResponse;
    private Throwable mThrowable;

    public Deal(T model, Response<T> response, Throwable throwable) {

        this.mModel = model;
        this.mResponse = response;
        this.mThrowable = throwable;
    }

    public T getModel() {

        return mModel;
    }

    public Response<T> getResponse() {

        return mResponse;
    }

    public Throwable getThrowable() {

        return mThrowable;
    }
}
