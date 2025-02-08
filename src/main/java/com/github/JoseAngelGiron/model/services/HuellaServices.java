package com.github.JoseAngelGiron.model.services;

import com.github.JoseAngelGiron.model.dao.HuellaDAO;
import com.github.JoseAngelGiron.model.dao.IDAO;
import com.github.JoseAngelGiron.model.entity.Huella;
import com.github.JoseAngelGiron.model.entity.Usuario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HuellaServices implements IDAO<Huella> {

    private HuellaDAO huellaDAO;

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

    public Map<String, BigDecimal > findUserFootprintByCategory(Usuario user){
       Map<String, BigDecimal> footprintsAndCategories = new HashMap<>();

        if(user.getId()!=null){
            footprintsAndCategories = huellaDAO.findUserFootprintByCategory(user.getId());
        }
        return footprintsAndCategories;
    }

    public Map<String, BigDecimal > findAverageFootprintByCategory(){
        Map<String, BigDecimal> footprintsAndCategories;

        footprintsAndCategories = huellaDAO.findAverageFootprintByCategory();

        return footprintsAndCategories;
    }

    public List<Huella> findPrintsThisMonth() {
        return huellaDAO.findPrintsThisMonth();
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
