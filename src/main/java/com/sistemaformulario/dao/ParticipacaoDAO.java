package com.sistemaformulario.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ParticipacaoDAO extends GenericDAO<com.sistemaformulario.entities.resposta.Participacao> {

    public ParticipacaoDAO() {
        super(com.sistemaformulario.entities.resposta.Participacao.class);
    }


    public boolean hasStudentReplied(Long alunoId, Long turmaId, Long formularioId) {
        EntityManager em = getEntityManager();
        try {
            String jpql = """
                SELECT COUNT(c) FROM ControleParticipacao c 
                WHERE c.aluno.id = :alunoId 
                AND c.turma.id = :turmaId 
                AND c.formulario.id = :formId
            """;

            Long count = em.createQuery(jpql, Long.class)
                    .setParameter("alunoId", alunoId)
                    .setParameter("turmaId", turmaId)
                    .setParameter("formId", formularioId)
                    .getSingleResult();

            return count > 0;
        } finally {
            em.close();
        }
    }


    public List<com.sistemaformulario.entities.resposta.Participacao> findByTurmaAndFormulario(Long turmaId, Long formularioId) {
        EntityManager em = getEntityManager();
        try {
            String jpql = """
                SELECT c FROM ControleParticipacao c 
                JOIN FETCH c.aluno 
                WHERE c.turma.id = :turmaId 
                AND c.formulario.id = :formId
                ORDER BY c.aluno.nome ASC
            """;

            // O "JOIN FETCH c.aluno" é uma otimização para carregar os nomes dos alunos
            // na mesma consulta, evitando o problema de N+1 queries.

            TypedQuery<com.sistemaformulario.entities.resposta.Participacao> query = em.createQuery(jpql, com.sistemaformulario.entities.resposta.Participacao.class);
            query.setParameter("turmaId", turmaId);
            query.setParameter("formId", formularioId);

            return query.getResultList();
        } finally {
            em.close();
        }
    }


    public Long countParticipations(Long turmaId, Long formularioId) {
        EntityManager em = getEntityManager();
        try {
            String jpql = """
                SELECT COUNT(c) FROM ControleParticipacao c 
                WHERE c.turma.id = :turmaId 
                AND c.formulario.id = :formId
            """;

            return em.createQuery(jpql, Long.class)
                    .setParameter("turmaId", turmaId)
                    .setParameter("formId", formularioId)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    public com.sistemaformulario.entities.resposta.Participacao findByStudentAndContext(Long alunoId, Long turmaId, Long formularioId) {
        EntityManager em = getEntityManager();
        try {
            String jpql = """
                SELECT c FROM ControleParticipacao c 
                WHERE c.aluno.id = :alunoId 
                AND c.turma.id = :turmaId 
                AND c.formulario.id = :formId
            """;

            return em.createQuery(jpql, com.sistemaformulario.entities.resposta.Participacao.class)
                    .setParameter("alunoId", alunoId)
                    .setParameter("turmaId", turmaId)
                    .setParameter("formId", formularioId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}