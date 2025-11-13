package com.project.dao;

import com.project.model.Beneficiaire;
import com.project.utils.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static java.sql.DriverManager.getConnection;

public class BeneficiaireDAO {



    public List<Beneficiaire> lister() {
        EntityManager em = JPAUtil.getEntityManager();
        Query query = em.createQuery("SELECT b FROM Beneficiaire b");
        List<Beneficiaire> list = query.getResultList();
        em.close();
        return list;
    }


    public boolean supprimer(Long id) {
        boolean success = false;
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Beneficiaire b = em.find(Beneficiaire.class, id);
            if (b != null) {
                em.remove(b);
                success = true;
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return success;
    }




    public boolean ajouter(Beneficiaire b) {
        EntityManager em = JPAUtil.getEntityManager();
        boolean success = false;
        try {
            em.getTransaction().begin();
            // Vérifier si le numéro SS existe déjà
            Query query = em.createQuery("SELECT COUNT(b) FROM Beneficiaire b WHERE b.numeroSS = :numero");
            query.setParameter("numero", b.getNumeroSS());
            long count = (Long) query.getSingleResult();

            if(count == 0) {
                em.persist(b);
                success = true;
            }
            em.getTransaction().commit();
        } catch(Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return success;
    }


    public boolean existeNumeroSS(String numeroSS) {
        EntityManager em = JPAUtil.getEntityManager();
        Query query = em.createQuery("SELECT COUNT(b) FROM Beneficiaire b WHERE b.numeroSS = :numero");
        query.setParameter("numero", numeroSS);
        long count = (Long) query.getSingleResult();
        em.close();
        return count > 0;
    }

    // --- Méthode pour trouver un bénéficiaire par id ---


    // --- Méthode pour modifier un bénéficiaire ---



    public Beneficiaire trouver(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        Beneficiaire b = em.find(Beneficiaire.class, id);
        em.close();
        return b;
    }

    public boolean modifier(Beneficiaire b) {
        boolean success = false;
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(b); // met à jour automatiquement
            em.getTransaction().commit();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return success;
    }





}