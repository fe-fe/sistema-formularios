package com.sistemaformulario.dao;

import com.sistemaformulario.entities.avaliacao.Formulario;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.time.LocalDateTime;

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

    public List<Formulario> buscarFormulariosDisponiveis(Long alunoId, Long turmaId) {
        EntityManager em = getEntityManager();
        try {
            LocalDateTime agora = LocalDateTime.now();

            String jpql = """
            SELECT f 
            FROM Formulario f 
            JOIN f.processoAvaliativo p 
            WHERE :agora BETWEEN p.dataInicio AND p.dataFim
            AND f.id NOT IN (
                SELECT part.formulario.id 
                FROM Participacao part 
                WHERE part.aluno.id = :alunoId 
                AND part.turma.id = :turmaId
            )
        """;

            return em.createQuery(jpql, Formulario.class)
                    .setParameter("agora", agora)
                    .setParameter("alunoId", alunoId)
                    .setParameter("turmaId", turmaId)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}