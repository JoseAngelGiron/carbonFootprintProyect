package com.github.JoseAngelGiron.model.dao;

import com.github.JoseAngelGiron.model.connection.Connection;
import com.github.JoseAngelGiron.model.entity.Habito;


import com.github.JoseAngelGiron.model.entity.HabitoId;
import com.github.JoseAngelGiron.model.entity.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class HabitoDAO implements IDAO<Habito> {

    private final static String FIND_ALL_HABITS_AND_CATEGORIES_BY_USER = "SELECT h FROM Habito h " +
                    "JOIN FETCH h.idActividad a " +
                    "JOIN FETCH a.idCategoria c " +
                    "WHERE h.idUsuario = :usuario";


    private Session session;

    @Override
    public Habito findById(Integer id) {
        session = Connection.getSessionFactory();
        Habito habito = new Habito();

        if(id==null){
            return habito;
        }

        try {
            habito = session.get(Habito.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return habito;
    }

    public Habito findByHabitoId(HabitoId id) {
        session = Connection.getSessionFactory();
        Habito habito = new Habito();

        if(id==null){
            return habito;
        }

        try {
            habito = session.get(Habito.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return habito;
    }

    public List<Habito> findAllHabitsByUser(Usuario user) {
        List<Habito> habits = new ArrayList();
        session = Connection.getSessionFactory();

        try{
            Query<Habito> query = session.createQuery(FIND_ALL_HABITS_AND_CATEGORIES_BY_USER, Habito.class);

            query.setParameter("usuario", user);

            habits = query.list();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            session.close();
        }

        return habits;
    }

    @Override
    public boolean save(Habito entity) {
        boolean saved = false;
        session = Connection.getSessionFactory();
        Transaction transaction = null;

        try{
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
            saved = true;

        }catch(Exception e){
            if(transaction!=null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }finally{
            session.close();
        }
        return saved;
    }

    @Override
    public boolean delete(Integer id) {
        boolean deleted = false;
        session = Connection.getSessionFactory();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            Habito habito = session.get(Habito.class, id);
            session.remove(habito);
            transaction.commit();
            deleted = true;

        }catch(Exception e){
            if(transaction!=null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }finally{
            session.close();
        }
        return deleted;
    }


    public boolean deleteByHabitoID(Habito habit) {
        boolean deleted = false;
        session = Connection.getSessionFactory();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();

            session.remove(habit);
            transaction.commit();
            deleted = true;

        }catch(Exception e){
            if(transaction!=null) {
                transaction.rollback();
            }
            e.printStackTrace();

        }finally{
            session.close();
        }
        return deleted;
    }

    @Override
    public boolean update(Habito entity) {
        boolean updated = false;
        session = Connection.getSessionFactory();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
            updated = true;

        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }finally {
            session.close();
        }

        return updated;
    }



}
