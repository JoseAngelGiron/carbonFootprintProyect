package com.github.JoseAngelGiron.model.services;

public interface IServices <T> {

    public boolean save(T entity);
    public boolean delete(Integer id);
    public boolean  update(T entity);
    public T findById(Integer id);


}
