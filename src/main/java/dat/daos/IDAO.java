package dat.daos;

import java.util.Set;

public interface IDAO<T> {
    T getByID(Integer id);
    Set<T> getAll();
    void create(T t);
    void delete(T t);
}
