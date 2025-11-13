package com.project.dao;

import com.project.model.Remboursement;
import com.project.model.Beneficiaire;
import com.project.utils.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.time.LocalDate;
import java.util.List;

public class RemboursementDAO {

    // --- Lister tous les remboursements ---
    public List<Remboursement> lister() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Query query = em.createQuery("SELECT r FROM Remboursement r ORDER BY r.date DESC");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // --- Lister les remboursements d’un bénéficiaire ---
    public List<Remboursement> listerParBeneficiaire(Long beneficiaireId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Query query = em.createQuery(
                    "SELECT r FROM Remboursement r WHERE r.beneficiaire.id = :id ORDER BY r.date DESC"
            );
            query.setParameter("id", beneficiaireId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // --- Trouver un remboursement par ID ---
    public Remboursement trouver(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Remboursement.class, id);
        } finally {
            em.close();
        }
    }

    // --- Ajouter un remboursement ---
    public boolean ajouter(Remboursement r) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            System.out.println("=== DEBUG DAO.ajouter ===");
            System.out.println("Remboursement: " + r);
            System.out.println("Beneficiaire ID: " + r.getBeneficiaire().getId());

            em.getTransaction().begin();

            Beneficiaire b = em.find(Beneficiaire.class, r.getBeneficiaire().getId());
            System.out.println("Beneficiaire trouvé: " + (b != null ? b.getId() : "null"));

            if (b != null) {
                r.setBeneficiaire(b);
                em.persist(r);
                em.getTransaction().commit();
                System.out.println("SUCCESS: Remboursement ajouté avec ID: " + r.getId());
                return true;
            } else {
                System.out.println("ERREUR: le bénéficiaire n'existe pas !");
                em.getTransaction().rollback();
                return false;
            }

        } catch (Exception e) {
            System.out.println("EXCEPTION dans dao.ajouter(): " + e.getMessage());
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    // --- Modifier un remboursement ---
    public boolean modifier(Remboursement r) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            Beneficiaire b = em.find(Beneficiaire.class, r.getBeneficiaire().getId());
            if (b != null) {
                r.setBeneficiaire(b);
                em.merge(r);
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

    // --- Supprimer un remboursement ---
    public boolean supprimer(Long id) {
        if (id == null) return false;

        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Remboursement r = em.find(Remboursement.class, id);
            if (r != null) {
                em.remove(r);
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

    // --- Vérifier si un remboursement existe déjà pour un bénéficiaire à une date ---
    public boolean existePourBeneficiaireEtDate(Long beneficiaireId, LocalDate date) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Query query = em.createQuery(
                    "SELECT COUNT(r) FROM Remboursement r WHERE r.beneficiaire.id = :id AND r.date = :date"
            );
            query.setParameter("id", beneficiaireId);
            query.setParameter("date", date);
            long count = (Long) query.getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }

    // --- Vérifier si un remboursement existe (autre que celui en cours) ---
    public boolean existePourBeneficiaireEtDateSauf(Long beneficiaireId, LocalDate date, Long remboursementId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Query query = em.createQuery(
                    "SELECT COUNT(r) FROM Remboursement r WHERE r.beneficiaire.id = :id AND r.date = :date AND r.id != :remboursementId"
            );
            query.setParameter("id", beneficiaireId);
            query.setParameter("date", date);
            query.setParameter("remboursementId", remboursementId);
            long count = (Long) query.getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }

    // --- Calculer le total des remboursements d’un bénéficiaire ---
    public Double calculerTotalParBeneficiaire(Long beneficiaireId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Query query = em.createQuery(
                    "SELECT COALESCE(SUM(r.montant), 0.0) FROM Remboursement r WHERE r.beneficiaire.id = :id"
            );
            query.setParameter("id", beneficiaireId);
            return (Double) query.getSingleResult();
        } finally {
            em.close();
        }
    }

    // --- Lister les remboursements entre deux dates ---
    public List<Remboursement> listerEntreDates(LocalDate dateDebut, LocalDate dateFin) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Query query = em.createQuery(
                    "SELECT r FROM Remboursement r WHERE r.date BETWEEN :debut AND :fin ORDER BY r.date DESC"
            );
            query.setParameter("debut", dateDebut);
            query.setParameter("fin", dateFin);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // --- Calculer le total des remboursements sur une période ---
    public Double calculerTotalPeriode(LocalDate dateDebut, LocalDate dateFin) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Query query = em.createQuery(
                    "SELECT COALESCE(SUM(r.montant), 0.0) FROM Remboursement r WHERE r.date BETWEEN :debut AND :fin"
            );
            query.setParameter("debut", dateDebut);
            query.setParameter("fin", dateFin);
            return (Double) query.getSingleResult();
        } finally {
            em.close();
        }
    }
}
