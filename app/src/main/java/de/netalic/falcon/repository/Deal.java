package de.netalic.falcon.repository;


import retrofit2.Response;

//T is model type
//K is source type

public class Deal<T, K> {

    private T mModel;
    private Response<T> mResponse;
    private K mSource;

    public Deal(T model, Response<T> response, K source) {

        this.mModel = model;
        this.mResponse = response;
        this.mSource = source;
    }

    public T getModel() {

        return mModel;
    }

    public Response<T> getResponse() {

        return mResponse;
    }

    public K getSource() {

        return mSource;
    }
}
