package com.test.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {

    Optional<T> findById(Long id);

    List<T> findAll();

    T save(T entity);

    void deleteById(Long id);

    default void close() {}
}