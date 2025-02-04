package com.github.JoseAngelGiron.model.services;

import com.github.JoseAngelGiron.model.dao.HuellaDAO;
import com.github.JoseAngelGiron.model.dao.IDAO;
import com.github.JoseAngelGiron.model.entity.Huella;



public class HuellaServices implements IDAO<Huella> {

    HuellaDAO huellaDAO;

    public HuellaServices() {
        huellaDAO = new HuellaDAO();
    }

    @Override
    public boolean save(Huella entity) {
        boolean saved = false;
        Huella printRetrieved = null;

        if (entity.getId() != null) {
            printRetrieved = huellaDAO.findById(entity.getId());
        }

        if(printRetrieved == null) {
            saved = huellaDAO.save(entity);
        }


        return saved;
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
