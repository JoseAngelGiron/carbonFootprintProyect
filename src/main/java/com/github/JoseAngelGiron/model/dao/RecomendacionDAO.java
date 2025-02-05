package com.github.JoseAngelGiron.model.dao;

import com.github.JoseAngelGiron.model.connection.Connection;
import com.github.JoseAngelGiron.model.entity.Recomendacion;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class RecomendacionDAO implements IDAO<Recomendacion> {

    private final static String FIND_RECOMENDATION_BY_PRINT = "SELECT r " +
            "FROM Recomendacion r " +
            "JOIN r.idCategoria c " +
            "JOIN c.actividads a " +
            "JOIN a.huellas h " +
            "WHERE h.id = :id ";

    private Session session;

    @Override
    public Recomendacion findById(Integer id) {
        return null;
    }

    public List<Recomendacion> findRecomendationByPrint(Integer id) {
        List<Recomendacion> recommendations = new ArrayList<>();
        session = Connection.getSessionFactory();

        try{
            Query<Recomendacion> query = session.createQuery(FIND_RECOMENDATION_BY_PRINT, Recomendacion.class);
            query.setParameter("id", id);
            recommendations = query.getResultList();


        } catch (RuntimeException e) {
            e.printStackTrace();
        }finally {
            session.close();
        }

        return recommendations;

    }

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


}
