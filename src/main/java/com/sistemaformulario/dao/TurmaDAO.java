package com.sistemaformulario.dao;

import com.sistemaformulario.entities.academico.Turma;
import com.sistemaformulario.entities.acesso.Usuario;
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

    public void matricularAluno(Long turmaId, Long alunoId) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();

            Turma turma = em.find(Turma.class, turmaId);
            Usuario aluno = em.find(Usuario.class, alunoId);

            if (turma == null) throw new RuntimeException("Turma não encontrada.");
            if (aluno == null) throw new RuntimeException("Aluno não encontrado.");

            //verificacao pra evitar duplicacao
            if (!turma.getAlunosMatriculados().contains(aluno)) {
                turma.getAlunosMatriculados().add(aluno);
                em.merge(turma);
            } else {
                throw new RuntimeException("Aluno já matriculado nesta turma.");
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}