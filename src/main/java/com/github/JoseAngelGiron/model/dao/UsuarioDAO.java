package com.github.JoseAngelGiron.model.dao;

import com.github.JoseAngelGiron.model.connection.Connection;
import com.github.JoseAngelGiron.model.entity.Usuario;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class UsuarioDAO implements IDAO<Usuario> {

    private final static String FINDBYEMAIL = "FROM Usuario WHERE  email = :email";

    private Session session;


    /**
     * Finds a User by their ID from the database.
     *
     * @param id the ID of the User to search for.
     * @return a Usuario object representing the User with the given ID, or an empty Usuario if not found.
     */
    @Override
    public Usuario findById(Integer id) {
        session = Connection.getSessionFactory();

        Usuario user = new Usuario();

        if(id==null){
            return user;
        }

        try {
            user = session.get(Usuario.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return user;
    }

    /**
     * Finds a User by their name from the database.
     *
     * @param email the name of the User to search for.
     * @return a Usuario object representing the User with the given name, or an empty Usuario if not found.
     */

    public Usuario findByEmail(String email) {
        session = Connection.getSessionFactory();

        Usuario user = new Usuario();

        try {
            Query<Usuario> query = session.createQuery(FINDBYEMAIL, Usuario.class);
            query.setParameter("email", email);

            user = query.getSingleResultOrNull();

        } catch (NoResultException e) {
            e.printStackTrace();

        }finally {

            session.close();
        }

        return user;
    }

    /**
     * Saves a new User to the database.
     *
     * @param user the Usuario object representing the user to be saved.
     */
    @Override
    public void save(Usuario user) {
        session = Connection.getSessionFactory();

        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {

            session.close();
        }

    }
    /**
     * Deletes a User by their ID from the database.
     *
     * @param id the ID of the User to delete.
     */
    @Override
    public void delete(Integer id) {
        session = Connection.getSessionFactory();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            Usuario user = session.get(Usuario.class, id);
            session.remove(user);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }finally {

            session.close();
        }
    }

    /**
     * Updates an existing User in the database.
     *
     * @param user the Usuario object representing the user to be updated.
     */
    @Override
    public void update(Usuario user) {
        session = Connection.getSessionFactory();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }finally {
            session.close();
        }
    }



}
