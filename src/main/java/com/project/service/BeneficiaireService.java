package com.project.service;

import com.project.dao.BeneficiaireDAO;
import com.project.model.Beneficiaire;

import java.util.List;

public class BeneficiaireService {

    private BeneficiaireDAO dao;

    public BeneficiaireService() {
        dao = new BeneficiaireDAO();
    }

    // Lister tous les bénéficiaires
    public List<Beneficiaire> getAllBeneficiaires() {
        return dao.lister();
    }

    // Trouver un bénéficiaire par ID
    public Beneficiaire getBeneficiaireById(Long id) {
        return dao.trouver(id);
    }

    // Ajouter un nouveau bénéficiaire
    public String addBeneficiaire(Beneficiaire b) {
        if (b.getNom() == null || b.getNom().isEmpty() ||
                b.getPrenom() == null || b.getPrenom().isEmpty() ||
                b.getDateNaissance() == null || b.getDateNaissance().isEmpty() ||
                b.getNumeroSS() == null || b.getNumeroSS().isEmpty()) {
            return "Champs manquants : Tous les champs sont obligatoires !";
        }

        if (dao.existeNumeroSS(b.getNumeroSS())) {
            return "Ce Numéro de SS est déjà utilisé !";
        }

        boolean ok = dao.ajouter(b);
        return ok ? null : "Erreur interne lors de l'ajout !";
    }



    // Modifier un bénéficiaire
    public boolean updateBeneficiaire(Beneficiaire b) {
        if (b.getId() == null) return false;
        return dao.modifier(b);
    }

    // Supprimer un bénéficiaire
    public boolean deleteBeneficiaire(Long id) {
        if (id == null) return false;
        return dao.supprimer(id);
    }}