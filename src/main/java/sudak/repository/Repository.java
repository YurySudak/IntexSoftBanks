package sudak.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {
    T save(T t);
    void update(T t);
    Optional<T> findById(ID id);
    Optional<T> findByName(String name);
    List<T> findAll();
    void delete(ID id);
}
