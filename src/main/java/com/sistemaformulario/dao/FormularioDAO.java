package com.sistemaformulario.dao;

import com.sistemaformulario.entities.avaliacao.Formulario;
import jakarta.persistence.EntityManager;
import java.util.List;

public class FormularioDAO extends GenericDAO<Formulario> {

    public FormularioDAO() {
        super(Formulario.class);
    }

    public List<Formulario> findByProcessoId(Long processoId) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT f FROM Formulario f WHERE f.processoAvaliativo.id = :pid";
            return em.createQuery(jpql, Formulario.class)
                    .setParameter("pid", processoId)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}