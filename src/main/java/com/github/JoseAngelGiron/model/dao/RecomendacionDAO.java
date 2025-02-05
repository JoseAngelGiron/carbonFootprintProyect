package com.github.JoseAngelGiron.model.dao;

import com.github.JoseAngelGiron.model.connection.Connection;
import com.github.JoseAngelGiron.model.entity.Recomendacion;
import com.github.JoseAngelGiron.model.entity.Usuario;
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

    private final static String FIND_RECOMMENDATIONS_BY_FREQUENCY = "SELECT r " +
                    "FROM Recomendacion r " +
                    "JOIN r.idCategoria c " +
                    "JOIN Habito h ON h.idActividad.idCategoria = c " +
                    "WHERE h.idUsuario.id = :userId " +
                    "AND h.tipo = :tipo " +
                    "ORDER BY h.frecuencia DESC";



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


    public List<Recomendacion> findRecomendationsByFrequency(Usuario user, String tipo) {
        session = Connection.getSessionFactory();
        List<Recomendacion> recomendaciones = new ArrayList<>();

        try {
            Query<Recomendacion> query = session.createQuery(FIND_RECOMMENDATIONS_BY_FREQUENCY, Recomendacion.class);
            query.setParameter("userId", user.getId());
            query.setParameter("tipo", tipo);
            query.setMaxResults(3);

            recomendaciones = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();

        }

        return recomendaciones;
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
