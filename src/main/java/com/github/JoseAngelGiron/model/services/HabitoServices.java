package com.github.JoseAngelGiron.model.services;


import com.github.JoseAngelGiron.model.dao.HabitoDAO;
import com.github.JoseAngelGiron.model.dao.IDAO;
import com.github.JoseAngelGiron.model.entity.Habito;

public class HabitoServices implements IDAO<Habito> {

    private HabitoDAO habitoDAO;

    public HabitoServices() {
        habitoDAO = new HabitoDAO();
    }

    @Override
    public boolean save(Habito entity) {
        boolean saved = false;
        Habito habit = habitoDAO.findByHabitoId(entity.getId());
        if (habit == null) {
            saved = habitoDAO.save(entity);
        }
        return saved;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean update(Habito entity) {
        return false;
    }

    @Override
    public Habito findById(Integer id) {
        return null;
    }
}
