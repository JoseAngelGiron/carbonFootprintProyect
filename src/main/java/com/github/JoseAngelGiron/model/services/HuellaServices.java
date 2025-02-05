package com.github.JoseAngelGiron.model.services;

import com.github.JoseAngelGiron.model.dao.HuellaDAO;
import com.github.JoseAngelGiron.model.dao.IDAO;
import com.github.JoseAngelGiron.model.entity.Huella;
import com.github.JoseAngelGiron.model.entity.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class HuellaServices implements IDAO<Huella> {

    HuellaDAO huellaDAO;

    public HuellaServices() {
        huellaDAO = new HuellaDAO();
    }

    @Override
    public Huella findById(Integer id) {
        return null;
    }

    public List<Huella> findAllprintsByUser(Usuario usuario) {
        List<Huella> prints = new ArrayList<>();

        if(usuario!=null){
            prints = huellaDAO.findAllPrintsByUser(usuario);
        }

        return prints;
    }

    public double findDailyImpact(Usuario user, LocalDate date) {
        double impact = 0;

        if(user!=null && date!=null) {
            impact = huellaDAO.calculateDailyImpact(date, user.getId());
        }

        return impact;
    }

    public double findImpactForAPeriod(Usuario user, LocalDate startdate, LocalDate endDate) {
        double impact = 0;

        if(user!=null && startdate!=null && endDate!=null) {
            impact = huellaDAO.calculateImpactForPeriod(startdate, endDate, user.getId());
        }

        return impact;
    }

    @Override
    public boolean save(Huella entity) {
        boolean saved = false;
        Huella printRetrieved = null;

        if (entity.getId() != null) {
            printRetrieved = huellaDAO.findById(entity.getId());
        }

        if(printRetrieved == null) {
            saved = huellaDAO.save(entity);
        }


        return saved;
    }

    @Override
    public boolean delete(Integer id) {
        boolean deleted = false;
        if(id != null) {
            deleted = huellaDAO.delete(id);
        }
        return deleted;
    }

    @Override
    public boolean update(Huella entity) {
        boolean updated = false;
        if(entity != null) {
            updated = huellaDAO.update(entity);
        }
        return updated;
    }


}
