package com.github.JoseAngelGiron.model.services;

import com.github.JoseAngelGiron.model.dao.IDAO;
import com.github.JoseAngelGiron.model.dao.RecomendacionDAO;
import com.github.JoseAngelGiron.model.entity.Huella;
import com.github.JoseAngelGiron.model.entity.Recomendacion;
import com.github.JoseAngelGiron.model.entity.Usuario;

import java.util.ArrayList;
import java.util.List;

public class RecomendacionServices implements IDAO<Recomendacion> {

    RecomendacionDAO recomnendationDAO;

    public RecomendacionServices() {
        recomnendationDAO = new RecomendacionDAO();
    }

    @Override
    public Recomendacion findById(Integer id) {
        return null;
    }

    public List<Recomendacion> findRecommendationByPrint(Huella print) {
        List<Recomendacion> recommendations = new ArrayList<>();
        if (print != null) {
            recommendations = recomnendationDAO.findRecomendationByPrint(print.getId());
        }
        return recommendations;
    }

    public List<Recomendacion> findRecommendationsDailyByUser(Usuario user, String tipo) {
        List<Recomendacion> recommendations = new ArrayList<>();
        if (user != null) {
            recommendations = recomnendationDAO.findRecomendationsByFrequency(user, tipo);
        }
        return recommendations;
    }


    @Override
    public boolean save(Recomendacion entity) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean update(Recomendacion entity) {
        return false;
    }


}
