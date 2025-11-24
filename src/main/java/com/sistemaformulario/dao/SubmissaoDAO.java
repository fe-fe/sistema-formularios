package com.sistemaformulario.dao;

import com.sistemaformulario.entities.avaliacao.Submissao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class SubmissaoDAO extends GenericDAO<Submissao> {

    public SubmissaoDAO() {
        super(Submissao.class);
    }

    public List<Submissao> findByTurma(Long turmaId) {
        EntityManager em = getEntityManager();
        try {
            // O JOIN FETCH é opcional, mas ajuda a performance se você for ler
            // as respostas logo em seguida. Aqui fiz a consulta simples.
            String jpql = "SELECT s FROM Submissao s WHERE s.turma.id = :turmaId";

            TypedQuery<Submissao> query = em.createQuery(jpql, Submissao.class);
            query.setParameter("turmaId", turmaId);

            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Submissao> findByTurmaAndFormulario(Long turmaId, Long formularioId) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT s FROM Submissao s WHERE s.turma.id = :tid AND s.formulario.id = :fid";

            return em.createQuery(jpql, Submissao.class)
                    .setParameter("tid", turmaId)
                    .setParameter("fid", formularioId)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}