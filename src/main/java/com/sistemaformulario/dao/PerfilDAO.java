package com.sistemaformulario.dao;

import com.sistemaformulario.entities.acesso.Perfil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class PerfilDAO extends GenericDAO<Perfil> {

    public PerfilDAO() {
        super(Perfil.class);
    }

    public Perfil findByName(String nome) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT p FROM Perfil p WHERE p.nome = :nome";
            TypedQuery<Perfil> query = em.createQuery(jpql, Perfil.class);
            query.setParameter("nome", nome);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Perfil> findAll() {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT p FROM Perfil p ORDER BY p.nome";
            return em.createQuery(jpql, Perfil.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar perfis no banco", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}