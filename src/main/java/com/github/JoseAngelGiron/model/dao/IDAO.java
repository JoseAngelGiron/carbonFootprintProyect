package com.github.JoseAngelGiron.model.dao;

public interface IDAO<T> {

    public T findById(Integer id);
    public boolean save(T entity);
    public boolean delete(Integer id);
    public boolean update(T entity);

}
