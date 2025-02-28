package sudak.service;

import java.util.List;

public interface Service<T, ID> {
    T create(T t);
    void update(T t);
    T getById(ID id);
    List<T> getAll();
    void delete(ID id);
}
