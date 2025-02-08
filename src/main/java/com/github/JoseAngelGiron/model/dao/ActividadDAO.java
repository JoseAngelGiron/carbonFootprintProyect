package com.github.JoseAngelGiron.model.dao;

import com.github.JoseAngelGiron.model.connection.Connection;
import com.github.JoseAngelGiron.model.entity.Actividad;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ActividadDAO implements IDAO<Actividad> {

    private final static String FIND_ALL_ACTIVITIES_AND_CATEGORIES = "SELECT a FROM Actividad a " +
            "JOIN FETCH a.idCategoria";

    private Session session;


    @Override
    public Actividad findById(Integer id) {
        return null;
    }

    public List<Actividad> findAll() {
        session = Connection.getSessionFactory();
        List<Actividad> actividades = new ArrayList<>();

        try {
            Query<Actividad> query = session.createQuery(FIND_ALL_ACTIVITIES_AND_CATEGORIES, Actividad.class);
            actividades = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return actividades;
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
