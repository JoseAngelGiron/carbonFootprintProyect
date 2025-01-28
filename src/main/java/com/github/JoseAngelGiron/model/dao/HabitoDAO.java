package com.github.JoseAngelGiron.model.dao;

import com.github.JoseAngelGiron.model.connection.Connection;
import com.github.JoseAngelGiron.model.entity.Habito;


import org.hibernate.Session;
import org.hibernate.Transaction;

public class HabitoDAO implements IDAO<Habito> {

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

    @Override
    public void save(Habito entity) {
        session = Connection.getSessionFactory();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();

        }catch(Exception e){
            if(transaction!=null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }finally{
            session.close();
        }

    }

    @Override
    public void delete(Integer id) {
        session = Connection.getSessionFactory();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            Habito habito = session.get(Habito.class, id);
            session.remove(habito);
            transaction.commit();

        }catch(Exception e){
            if(transaction!=null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }finally{
            session.close();
        }
    }

    @Override
    public void update(Habito entity) {
        session = Connection.getSessionFactory();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }finally {
            session.close();
        }


    }


}
