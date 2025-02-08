package com.github.JoseAngelGiron.model.dao;

import com.github.JoseAngelGiron.model.connection.Connection;
import com.github.JoseAngelGiron.model.entity.Categoria;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CategoriaDAO implements IDAO<Categoria> {

    private final static String CATEGORIES_WITH_HIGHEST_IMPACT = "SELECT c.nombre, SUM(h.valor * c.factorEmision) as total_impacto " +
            "FROM Huella h " +
            "JOIN h.idActividad a " +
            "JOIN a.idCategoria c " +
            "GROUP BY c.nombre " +
            "ORDER BY total_impacto DESC";

    private Session session;

    @Override
    public Categoria findById(Integer id) {
        return null;
    }

    public List<Map.Entry<String, Double>> findCategoriasWithHighestImpact() {
        session = Connection.getSessionFactory();
        List<Map.Entry<String, Double>> categorias = new ArrayList<>();

        try {

            List<Object[]> results = session.createQuery(CATEGORIES_WITH_HIGHEST_IMPACT).getResultList();


            for (Object[] result : results) {
                String categoriaNombre = (String) result[0];
                BigDecimal totalImpactoBigDecimal = (BigDecimal) result[1];


                Double totalImpacto = totalImpactoBigDecimal.doubleValue();


                categorias.add(new AbstractMap.SimpleEntry<>(categoriaNombre, totalImpacto));
            }

        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return categorias;
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
