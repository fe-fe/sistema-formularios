package com.sistemaformulario.dao;

import com.sistemaformulario.entities.avaliacao.ProcessoAvaliativo;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class ProcessoAvaliativoDAO extends GenericDAO<ProcessoAvaliativo> {

    public ProcessoAvaliativoDAO() {
        super(ProcessoAvaliativo.class);
    }

    public List<ProcessoAvaliativo> findActiveProcesses() {
        EntityManager em = getEntityManager();
        try {
            LocalDateTime agora = LocalDateTime.now();
            String jpql = "SELECT p FROM ProcessoAvaliativo p WHERE :agora BETWEEN p.dataInicio AND p.dataFim";
            return em.createQuery(jpql, ProcessoAvaliativo.class)
                    .setParameter("agora", agora)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}