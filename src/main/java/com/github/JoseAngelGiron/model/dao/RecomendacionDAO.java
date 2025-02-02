package com.github.JoseAngelGiron.model.dao;

import com.github.JoseAngelGiron.model.entity.Recomendacion;

public class RecomendacionDAO implements IDAO<Recomendacion> {

    @Override
    public boolean save(Recomendacion entity) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean update(Recomendacion entity) {
        return false;
    }

    @Override
    public Recomendacion findById(Integer id) {
        return null;
    }
}
