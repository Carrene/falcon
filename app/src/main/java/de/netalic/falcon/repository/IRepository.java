package de.netalic.falcon.repository;

public interface IRepository<T, K> {

    void update(T t, CallRepository<T> callRepository);

    void get(K identifier, CallRepository<T> callRepository);

    interface CallRepository<T> {

        void onDone(Deal<T> deal);

    }
}
