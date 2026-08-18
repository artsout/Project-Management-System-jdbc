package Services.model;

import java.util.List;

public interface Service <T>{

        void update(T t);
        T deleteById(Integer id);
        List<T> findAll();
        T findById(Integer id);
        void save(T t);

}
