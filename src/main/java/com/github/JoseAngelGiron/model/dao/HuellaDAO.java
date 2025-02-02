package com.github.JoseAngelGiron.model.dao;

import com.github.JoseAngelGiron.model.connection.Connection;
import com.github.JoseAngelGiron.model.entity.Huella;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class HuellaDAO implements IDAO<Huella> {

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

    @Override
    public boolean save(Huella entity) {
        boolean save = false;
        session = Connection.getSessionFactory();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.persist(entity);
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
            e.printStackTrace();
            transaction.rollback();
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
