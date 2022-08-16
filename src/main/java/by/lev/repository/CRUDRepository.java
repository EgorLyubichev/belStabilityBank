package by.lev.repository;

import java.util.List;
import java.util.Optional;

    public interface CRUDRepository<K, T> {

        int DEFAULT_FIND_ALL_LIMIT = 10;

        int DEFAULT_FIND_ALL_OFFSET = 0;

        T findById(K id);

        Optional<T> findOne(K id);

        List<T> findAll();

        List<T> findAll(int limit, int offset);

        T create(T t);

        T update(T t);

        K delete(K id);
    }
