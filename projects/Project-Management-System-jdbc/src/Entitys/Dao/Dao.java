package Entitys.Dao;

import java.util.List;

public interface Dao <T>{
     void update(T t);
    T deleteByID(Integer id);
    List<T> findAll();
    T findById(Integer id);
    void save(T t);
}
