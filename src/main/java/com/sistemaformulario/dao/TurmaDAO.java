package com.sistemaformulario.dao;

import com.sistemaformulario.entities.academico.Turma;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class TurmaDAO extends GenericDAO<Turma> {

    public TurmaDAO() {
        super(Turma.class);
    }

    public List<Turma> findByAlunoId(Long alunoId) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT t FROM Turma t JOIN t.alunosMatriculados a WHERE a.id = :alunoId";
            TypedQuery<Turma> query = em.createQuery(jpql, Turma.class);
            query.setParameter("alunoId", alunoId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Professor vê apenas suas turmas
    public List<Turma> findByProfessorId(Long professorId) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT t FROM Turma t WHERE t.professor.id = :profId";
            TypedQuery<Turma> query = em.createQuery(jpql, Turma.class);
            query.setParameter("profId", professorId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}