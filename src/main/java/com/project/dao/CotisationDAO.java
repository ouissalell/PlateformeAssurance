package com.project.dao;

import com.project.model.Cotisation;
import com.project.model.Beneficiaire;
import com.project.utils.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.Query;

import java.time.LocalDate;
import java.util.List;

public class CotisationDAO {

    // --- Lister toutes les cotisations ---
    public List<Cotisation> lister() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Query query = em.createQuery("SELECT c FROM Cotisation c");
            List<Cotisation> list = query.getResultList();
            return list;
        } finally {
            em.close();
        }
    }

    // --- Lister les cotisations d'un bénéficiaire ---
    public List<Cotisation> listerParBeneficiaire(Long beneficiaireId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Query query = em.createQuery(
                    "SELECT c FROM Cotisation c WHERE c.beneficiaire.id = :id"
            );
            query.setParameter("id", beneficiaireId);
            List<Cotisation> list = query.getResultList();
            return list;
        } finally {
            em.close();
        }
    }

    // --- Trouver une cotisation par ID ---
    public Cotisation trouver(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Cotisation c = em.find(Cotisation.class, id);
            return c;
        } finally {
            em.close();
        }
    }

    // --- Ajouter une cotisation ---
    public boolean ajouter(Cotisation c) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            // Vérifier que le bénéficiaire existe
            Beneficiaire b = em.find(Beneficiaire.class, c.getBeneficiaire().getId());
            if (b != null) {
                c.setBeneficiaire(b); // liaison JPA
                em.persist(c);
                em.getTransaction().commit();
                return true;
            } else {
                System.out.println("Erreur : le bénéficiaire n'existe pas !");
                em.getTransaction().rollback();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    // --- Modifier une cotisation ---
    public boolean modifier(Cotisation c) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            // Vérifier le bénéficiaire associé
            Beneficiaire b = em.find(Beneficiaire.class, c.getBeneficiaire().getId());
            if (b != null) {
                c.setBeneficiaire(b);
                em.merge(c);
                em.getTransaction().commit();
                return true;
            } else {
                System.out.println("Erreur : le bénéficiaire associé n'existe pas !");
                em.getTransaction().rollback();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    // --- Supprimer une cotisation ---
    public boolean supprimer(Long id) {
        if (id == null) {
            return false;
        }

        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Cotisation c = em.find(Cotisation.class, id);
            if (c != null) {
                em.remove(c);
                em.getTransaction().commit();
                return true;
            } else {
                em.getTransaction().rollback();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    // ✅ CORRIGÉ : Prend LocalDate en paramètre au lieu de String
    public boolean existePourBeneficiaireEtDate(Long beneficiaireId, LocalDate date) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Query query = em.createQuery(
                    "SELECT COUNT(c) FROM Cotisation c WHERE c.beneficiaire.id = :id AND c.date = :date"
            );
            query.setParameter("id", beneficiaireId);
            query.setParameter("date", date);  // ✅ LocalDate directement
            long count = (Long) query.getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }

    // --- Vérifier si une cotisation existe déjà pour un bénéficiaire à une date (sauf une cotisation spécifique) ---
    public boolean existePourBeneficiaireEtDateSauf(Long beneficiaireId, LocalDate date, Long cotisationId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Query query = em.createQuery(
                    "SELECT COUNT(c) FROM Cotisation c WHERE c.beneficiaire.id = :id AND c.date = :date AND c.id != :cotisationId"
            );
            query.setParameter("id", beneficiaireId);
            query.setParameter("date", date);
            query.setParameter("cotisationId", cotisationId);
            long count = (Long) query.getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }

    // --- Calculer le total des cotisations d'un bénéficiaire ---
    public Double calculerTotalParBeneficiaire(Long beneficiaireId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Query query = em.createQuery(
                    "SELECT COALESCE(SUM(c.montant), 0.0) FROM Cotisation c WHERE c.beneficiaire.id = :id"
            );
            query.setParameter("id", beneficiaireId);
            return (Double) query.getSingleResult();
        } finally {
            em.close();
        }
    }

    // --- Lister les cotisations entre deux dates ---
    public List<Cotisation> listerEntreDates(LocalDate dateDebut, LocalDate dateFin) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Query query = em.createQuery(
                    "SELECT c FROM Cotisation c WHERE c.date BETWEEN :debut AND :fin ORDER BY c.date DESC"
            );
            query.setParameter("debut", dateDebut);
            query.setParameter("fin", dateFin);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // --- Calculer le total des cotisations sur une période ---
    public Double calculerTotalPeriode(LocalDate dateDebut, LocalDate dateFin) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Query query = em.createQuery(
                    "SELECT COALESCE(SUM(c.montant), 0.0) FROM Cotisation c WHERE c.date BETWEEN :debut AND :fin"
            );
            query.setParameter("debut", dateDebut);
            query.setParameter("fin", dateFin);
            return (Double) query.getSingleResult();
        } finally {
            em.close();
        }
    }
}