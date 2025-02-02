package com.github.JoseAngelGiron.model.services;

import com.github.JoseAngelGiron.model.dao.IDAO;
import com.github.JoseAngelGiron.model.entity.Categoria;

public class CategoriaServices implements IDAO<Categoria> {
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
