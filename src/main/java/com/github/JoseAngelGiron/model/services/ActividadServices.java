package com.github.JoseAngelGiron.model.services;

import com.github.JoseAngelGiron.model.dao.ActividadDAO;
import com.github.JoseAngelGiron.model.entity.Actividad;

import java.util.List;

public class ActividadServices implements IServices<Actividad> {


    private ActividadDAO actividadDAO;

    public ActividadServices() {
        actividadDAO = new ActividadDAO();
    }

    @Override
    public Actividad findById(Integer id) {
        return null;
    }

    public List<Actividad> findAllActivities() {
        return actividadDAO.findAll();
    }

    @Override
    public boolean save(Actividad entity) {
        return false;

    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean update(Actividad entity) {
        return false;
    }


}
