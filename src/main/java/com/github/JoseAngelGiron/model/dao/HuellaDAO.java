package com.github.JoseAngelGiron.model.dao;

import com.github.JoseAngelGiron.model.connection.Connection;

import com.github.JoseAngelGiron.model.entity.Huella;
import com.github.JoseAngelGiron.model.entity.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class HuellaDAO implements IDAO<Huella> {

    private final static String FIND_ALL_FOOTPRINTS_WITH_ACTIVITIES_AND_CATEGORIES_BY_USER = "SELECT hu FROM Huella hu " +
            "JOIN FETCH hu.idActividad a " +
            "JOIN FETCH a.idCategoria c " +
            "WHERE hu.idUsuario = :usuario";

    private final static String CALCULATE_DAILY_IMPACT = "SELECT SUM(h.valor * c.factorEmision) " +
            "FROM Huella h " +
            "JOIN h.idActividad a " +
            "JOIN a.idCategoria c " +
            "WHERE h.fecha = :date " +
            "AND h.idUsuario.id = :userId";

    private final static String CALCULATE_IMPACT_IN_A_PERIOD = "SELECT SUM(h.valor * c.factorEmision) " +
            "FROM Huella h " +
            "JOIN h.idActividad a " +
            "JOIN a.idCategoria c " +
            "WHERE h.fecha BETWEEN :startDate AND :endDate " +
            "AND h.idUsuario.id = :userId";

    private Session session;


    @Override
    public Huella findById(Integer id) {
        session = Connection.getSessionFactory();
        Huella print = new Huella();

        if(id==null){
            return print;
        }

        try {
            print = session.get(Huella.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return print;
    }

    public List<Huella> findAllPrintsByUser(Usuario usuario) {
        List<Huella> prints = new ArrayList();
        session = Connection.getSessionFactory();

        try{
            Query<Huella> query = session.createQuery(FIND_ALL_FOOTPRINTS_WITH_ACTIVITIES_AND_CATEGORIES_BY_USER, Huella.class);
            query.setParameter("usuario", usuario);
            prints = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            session.close();
        }



        return prints;
    }

    public double calculateDailyImpact(LocalDate date, Integer userId) {
        double impactoTotal = 0.0;
        session = Connection.getSessionFactory();

        try {
            Query<BigDecimal> query = session.createQuery(CALCULATE_DAILY_IMPACT, BigDecimal.class);

            query.setParameter("date", date);
            query.setParameter("userId", userId);

            BigDecimal result = query.getSingleResult();
            impactoTotal = (result != null) ? result.doubleValue() : 0.0;

        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return impactoTotal;
    }

    public double calculateImpactForPeriod(LocalDate startDate, LocalDate endDate, Integer userId) {
        double impactoTotal = 0.0;
        session = Connection.getSessionFactory();

        try {
            Query<BigDecimal> query = session.createQuery(CALCULATE_IMPACT_IN_A_PERIOD, BigDecimal.class);

            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            query.setParameter("userId", userId);

            BigDecimal result = query.getSingleResult();
            impactoTotal = (result != null) ? result.doubleValue() : 0.0;

        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return impactoTotal;
    }

    @Override
    public boolean save(Huella entity) {
        boolean save = false;
        session = Connection.getSessionFactory();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
            save = true;
        }catch(Exception e){
            if(transaction!=null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }finally{
            session.close();
        }
        return save;
    }

    @Override
    public boolean delete(Integer id) {
        boolean delete = false;
        session = Connection.getSessionFactory();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            Huella print = session.get(Huella.class, id);
            session.remove(print);
            transaction.commit();
            delete = true;

        } catch (Exception e) {
            if(transaction!=null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {

            session.close();
        }
        return delete;
    }

    @Override
    public boolean update(Huella entity) {
        boolean update = false;
        session = Connection.getSessionFactory();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
            update = true;

        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }finally {
            session.close();
        }
        return update;
    }



}
