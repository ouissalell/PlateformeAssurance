package com.project.service;

import com.project.dao.CotisationDAO;
import com.project.model.Cotisation;
import com.project.model.Beneficiaire;

import java.time.LocalDate;
import java.util.List;

public class CotisationService {

    private CotisationDAO dao;

    public CotisationService() {
        dao = new CotisationDAO();
    }

    // Lister toutes les cotisations
    public List<Cotisation> getAllCotisations() {
        return dao.lister();
    }

    // Lister cotisations d'un bénéficiaire
    public List<Cotisation> getCotisationsParBeneficiaire(Long beneficiaireId) {
        return dao.listerParBeneficiaire(beneficiaireId);
    }

    // Trouver une cotisation par ID
    public Cotisation getCotisationById(Long id) {
        return dao.trouver(id);
    }

    // Ajouter une cotisation
    public boolean addCotisation(Cotisation c) {
        if (c.getMontant() == null || c.getMontant() <= 0 ||
                c.getDate() == null || c.getBeneficiaire() == null) {
            return false; // champs obligatoires manquants
        }

        // Vérifier doublon pour le bénéficiaire et la date
        if (dao.existePourBeneficiaireEtDate(c.getBeneficiaire().getId(), c.getDate())) {
            return false; // cotisation déjà existante
        }

        dao.ajouter(c);
        return true;
    }

    // Modifier une cotisation
    public boolean updateCotisation(Cotisation c) {
        if (c.getId() == null) return false;
        return dao.modifier(c);
    }

    // Supprimer une cotisation
    public boolean deleteCotisation(Long id) {
        if (id == null) return false;
        return dao.supprimer(id);
    }
}
