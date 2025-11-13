package com.project.service;

import com.project.dao.RemboursementDAO;
import com.project.model.Remboursement;

import java.time.LocalDate;
import java.util.List;

public class RemboursementService {

    private RemboursementDAO dao;

    public RemboursementService() {
        dao = new RemboursementDAO();
    }

    // Lister tous les remboursements
    public List<Remboursement> getAllRemboursements() {
        return dao.lister();
    }

    // Lister les remboursements d’un bénéficiaire
    public List<Remboursement> getRemboursementsParBeneficiaire(Long beneficiaireId) {
        return dao.listerParBeneficiaire(beneficiaireId);
    }

    // Trouver un remboursement par ID
    public Remboursement getRemboursementById(Long id) {
        return dao.trouver(id);
    }

    // Ajouter un remboursement
    public String addRemboursement(Remboursement r) {
        System.out.println("=== DEBUG addRemboursement ===");
        System.out.println("Montant: " + r.getMontant());
        System.out.println("Date: " + r.getDate());
        System.out.println("Beneficiaire: " + (r.getBeneficiaire() != null ? r.getBeneficiaire().getId() : "null"));

        // Validation des champs
        if (r.getMontant() == null || r.getMontant() <= 0) {
            System.out.println("ERREUR: MONTANT_INVALIDE");
            return "MONTANT_INVALIDE";
        }
        if (r.getDate() == null) {
            System.out.println("ERREUR: DATE_MANQUANTE");
            return "DATE_MANQUANTE";
        }
        if (r.getBeneficiaire() == null) {
            System.out.println("ERREUR: BENEFICIAIRE_MANQUANT");
            return "BENEFICIAIRE_MANQUANT";
        }

        // Vérifier doublon
        boolean doublon = dao.existePourBeneficiaireEtDate(r.getBeneficiaire().getId(), r.getDate());
        System.out.println("Doublon détecté: " + doublon);
        if (doublon) {
            System.out.println("ERREUR: DOUBLON");
            return "DOUBLON";
        }

        // Tentative d'ajout
        System.out.println("Appel dao.ajouter()...");
        boolean success = dao.ajouter(r);
        System.out.println("Résultat dao.ajouter(): " + success);

        return success ? "SUCCESS" : "ERREUR_BD";
    }

    // Modifier un remboursement
    public boolean updateRemboursement(Remboursement r) {
        if (r.getId() == null) return false;
        return dao.modifier(r);
    }

    // Supprimer un remboursement
    public boolean deleteRemboursement(Long id) {
        if (id == null) return false;
        return dao.supprimer(id);
    }
}
