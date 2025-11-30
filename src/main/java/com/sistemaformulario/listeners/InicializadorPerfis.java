package com.sistemaformulario.listeners;

import com.sistemaformulario.entities.acesso.Perfil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class InicializadorPerfis implements ServletContextListener {

    private EntityManagerFactory emf;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("🔵 Inicializando sistema... verificando perfis padrão.");

        emf = Persistence.createEntityManagerFactory("projeto-avaliacao-pu");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            long count = em.createQuery("SELECT COUNT(p) FROM Perfil p", Long.class).getSingleResult();

            if (count == 0) {
                System.out.println("⚠ Nenhum perfil encontrado. Criando perfis padrão...");

                em.persist(new Perfil("ALUNO"));
                em.persist(new Perfil("ADMINISTRADOR"));
                em.persist(new Perfil("PROFESSOR"));

                System.out.println("✔ Perfis padrão criados com sucesso!");
            } else {
                System.out.println("✔ Perfis já existem. Nenhuma ação necessária.");
            }

            em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (emf != null) {
            emf.close();
        }
    }
}
