package com.project.dao;

import com.project.model.Utilisateur;
import com.project.model.Beneficiaire;
import com.project.utils.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class UtilisateurDAO {

    public Utilisateur findByLogin(String login) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Utilisateur> q = em.createQuery(
                    "SELECT u FROM Utilisateur u WHERE u.login = :login",
                    Utilisateur.class
            );
            q.setParameter("login", login);

            return q.getResultList().isEmpty() ? null : q.getResultList().get(0);
        } finally {
            em.close();
        }
    }

    public void insert(Utilisateur user) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            // Assurer que le Beneficiaire est attaché à l'EM
            user.setBeneficiaire(em.find(Beneficiaire.class, user.getBeneficiaire().getId()));
            em.persist(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }



    public void update(Utilisateur u) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(u);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void save(Utilisateur u) {
        if (u.getId() == null) {
            // Nouveau utilisateur : insert
            insert(u); // insert ne prend plus que l'Utilisateur
        } else {
            // Utilisateur existant : update
            update(u);
        }
    }



    public boolean loginExiste(String login) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Long> q = em.createQuery(
                    "SELECT COUNT(u) FROM Utilisateur u WHERE u.login = :login",
                    Long.class
            );
            q.setParameter("login", login);
            return q.getSingleResult() > 0;
        } finally {
            em.close();
        }
    }

    public boolean beneficiaireADejaCompte(Long beneficiaireId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Long> q = em.createQuery(
                    "SELECT COUNT(u) FROM Utilisateur u WHERE u.beneficiaire.id = :id",
                    Long.class
            );
            q.setParameter("id", beneficiaireId);

            return q.getSingleResult() > 0;
        } finally {
            em.close();
        }
    }
}
