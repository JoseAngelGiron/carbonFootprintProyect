package com.github.JoseAngelGiron.model.dao;

public interface IDAO<T> {

    public T findById(Integer id);
    public void save(T entity);
    public void delete(Integer id);
    public void update(T entity);

}
