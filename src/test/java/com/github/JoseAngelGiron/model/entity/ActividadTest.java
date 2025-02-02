package com.github.JoseAngelGiron.model.entity;

import com.github.JoseAngelGiron.model.dao.ActividadDAO;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ActividadTest {


    @Test
    public void testAll() {

        ActividadDAO actividadDAO = new ActividadDAO();
        List<Actividad> actividades = actividadDAO.findAll();

        for(Actividad actividad : actividades) {
            System.out.println(actividad.getNombre() + " "+ actividad.getIdCategoria().getNombre()
            + " " + actividad.getIdCategoria().getUnidad() + " " + actividad.getIdCategoria().getFactorEmision() );
        }
    }
}
