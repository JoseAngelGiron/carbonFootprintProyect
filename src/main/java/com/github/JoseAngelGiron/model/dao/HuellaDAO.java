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
    public void save(Huella entity) {
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
            Huella print = session.get(Huella.class, id);
            session.remove(print);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }finally {

            session.close();
        }
    }

    @Override
    public void update(Huella entity) {
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
