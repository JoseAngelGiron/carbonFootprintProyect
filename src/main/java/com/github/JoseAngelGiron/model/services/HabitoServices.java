package com.github.JoseAngelGiron.model.services;


import com.github.JoseAngelGiron.model.dao.HabitoDAO;
import com.github.JoseAngelGiron.model.dao.IDAO;
import com.github.JoseAngelGiron.model.dao.UsuarioDAO;
import com.github.JoseAngelGiron.model.entity.Habito;
import com.github.JoseAngelGiron.model.entity.Usuario;

import java.util.ArrayList;
import java.util.List;

public class HabitoServices implements IDAO<Habito> {

    private HabitoDAO habitoDAO;

    public HabitoServices() {
        habitoDAO = new HabitoDAO();
    }

    @Override
    public Habito findById(Integer id) {
        return null;
    }

    public List<Habito> findAllHabitsByUser(Usuario user) {
        List<Habito> habits = new ArrayList<>();

        UsuarioDAO userDAO = new UsuarioDAO();
        Usuario userRetrieved = userDAO.findById(user.getId());

        if(userRetrieved!=null){
            habits = habitoDAO.findAllHabitsByUser(user);
        }

        return habits;
    }

    @Override
    public boolean save(Habito entity) {
        boolean saved = false;
        Habito retrievedHabit = habitoDAO.findByUserAndActivity(entity.getIdUsuario(),entity.getIdActividad());
        if (retrievedHabit == null) {
            saved = habitoDAO.save(entity);
        }
        return saved;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }
    public boolean delete(Habito habit) {
        boolean deleted = false;
        Habito habitRetrieved = habitoDAO.findByHabitoId(habit.getId());
        if(habitRetrieved!=null){
            habitoDAO.deleteByHabitoID(habit);
            deleted = true;
        }

        return deleted;
    }

    @Override
    public boolean update(Habito entity) {
        boolean updated = false;
        Habito habit = habitoDAO.findByHabitoId(entity.getId());
        if (habit != null) {
            updated = habitoDAO.save(entity);
        }
        return updated;
    }


}
