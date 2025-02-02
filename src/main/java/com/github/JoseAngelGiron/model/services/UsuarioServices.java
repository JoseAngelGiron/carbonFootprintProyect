package com.github.JoseAngelGiron.model.services;

import com.github.JoseAngelGiron.model.dao.UsuarioDAO;
import com.github.JoseAngelGiron.model.entity.Usuario;


public class UsuarioServices implements IServices<Usuario> {

    private UsuarioDAO userDAO;


    public UsuarioServices() {
        this.userDAO = new UsuarioDAO();
    }

    @Override
    public boolean save(Usuario user) {
        boolean saved = false;

        if (user.getId() == null && !user.getNombre().isEmpty() && !user.getEmail().isEmpty()) {

            Usuario retrievedUser = userDAO.findByEmail(user.getEmail());

            if(retrievedUser == null) {
                saved = userDAO.save(user);
            }
        }

        return saved;
    }

    @Override
    public Usuario findById(Integer id) {
        if (id != null) {
            return userDAO.findById(id);
        }
        return new Usuario();

    }

    public Usuario findUsuarioByEmail(String email) {
        Usuario user = new Usuario();
        if (email != null) {
            user = userDAO.findByEmail(email);
        }
        return user;
    }


    @Override
    public boolean update(Usuario user) {
        boolean updated = false;
        if (user.getId() != null) {
            userDAO.update(user);
            updated = true;
        }
        return updated;
    }

    @Override
    public boolean delete(Integer id) {
        boolean deleted = false;
        //Revisar si da problemas
        if (id != null) {
            Usuario user = userDAO.findById(id);

            if (user != null && user.getId() != null) {
                System.out.println(user.getId());
                userDAO.delete(user.getId());
                deleted = true;
            }

        }
        return deleted;
    }

}
