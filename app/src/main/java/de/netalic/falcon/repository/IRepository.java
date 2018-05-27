package de.netalic.falcon.repository;

public interface IRepository<T> {

    void update(T t);

    T get(int id);
}