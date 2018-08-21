package de.netalic.falcon.data.repository.base;

import java.util.List;

public interface IRepository<T, K> {

    void update(T t, CallRepository<T> callRepository);

    void get(K identifier, CallRepository<T> callRepository);

    void getAll(CallRepository<List<T>> callRepository);

    interface CallRepository<T> {

        void onDone(Deal<T> deal);
    }
}
