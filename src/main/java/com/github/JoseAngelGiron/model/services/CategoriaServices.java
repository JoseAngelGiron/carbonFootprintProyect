package com.github.JoseAngelGiron.model.services;

import com.github.JoseAngelGiron.model.dao.CategoriaDAO;
import com.github.JoseAngelGiron.model.dao.IDAO;
import com.github.JoseAngelGiron.model.entity.Categoria;

import java.util.List;
import java.util.Map;

public class CategoriaServices implements IDAO<Categoria> {

    private CategoriaDAO categoriaDAO;

    public CategoriaServices() {
        this.categoriaDAO = new CategoriaDAO();
    }

    @Override
    public Categoria findById(Integer id) {
        return null;
    }


    public List<Map.Entry<String, Double>> findCategoriesWithTheHigiestImpact() {

        return categoriaDAO.findCategoriasWithHighestImpact();
    }

    @Override
    public boolean save(Categoria entity) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean update(Categoria entity) {
        return false;
    }


}
