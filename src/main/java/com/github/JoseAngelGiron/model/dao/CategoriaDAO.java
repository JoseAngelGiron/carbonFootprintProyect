package com.github.JoseAngelGiron.model.dao;

import com.github.JoseAngelGiron.model.entity.Categoria;

public class CategoriaDAO implements IDAO<Categoria> {

    @Override
    public boolean save(Categoria entity) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean update(Categoria entity) {
        return false;
    }

    @Override
    public Categoria findById(Integer id) {
        return null;
    }
}
