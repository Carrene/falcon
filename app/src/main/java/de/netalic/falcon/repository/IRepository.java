package de.netalic.falcon.repository;

import java.util.List;

import de.netalic.falcon.model.Wallet;

public interface IRepository<T, K> {

    void update(T t, CallRepository<T> callRepository);

    void get(K identifier, CallRepository<T> callRepository);

    interface CallRepository<T> {

        void onDone(Deal<T> deal);




    }
}
