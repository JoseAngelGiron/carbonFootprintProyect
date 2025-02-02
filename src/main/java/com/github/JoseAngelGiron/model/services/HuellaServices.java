package com.github.JoseAngelGiron.model.services;

import com.github.JoseAngelGiron.model.dao.IDAO;
import com.github.JoseAngelGiron.model.entity.Huella;



public class HuellaServices implements IDAO<Huella> {
    @Override
    public boolean save(Huella entity) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean update(Huella entity) {
        return false;
    }

    @Override
    public Huella findById(Integer id) {
        return null;
    }
}
