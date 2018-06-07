package de.netalic.falcon.repository;

public interface IRepository<T> {

    interface CallRepository<T> {

        void onDone(Deal<T> deal);

    }
}
