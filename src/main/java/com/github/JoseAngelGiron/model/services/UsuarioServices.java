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

        if (user.getId() == null && !user.getNombre().isEmpty()) {

            Usuario retrievedUser = userDAO.findByName(user.getNombre());

            if(retrievedUser == null) {
                userDAO.save(user);
                saved = true;
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

    public Usuario findUsuarioByName(String name) {
        Usuario user = new Usuario();
        if (name != null) {
            userDAO.findByName(name);
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
