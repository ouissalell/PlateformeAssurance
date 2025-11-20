package com.project.service;

import com.project.dao.UtilisateurDAO;
import com.project.model.Utilisateur;
import com.project.model.Beneficiaire;
import com.project.utils.JPAUtil;
import jakarta.persistence.EntityManager;

public class UtilisateurService {

    private UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    public Utilisateur authentifier(String login, String motDePasse) {
        Utilisateur user = utilisateurDAO.findByLogin(login);
        if (user != null && user.getMotDePasse().equals(motDePasse)) {
            return user;
        }
        return null;
    }

    public String creerCompteUtilisateur(String login, String mdp, Long beneficiaireId) {

        if (utilisateurDAO.loginExiste(login)) {
            return "login_existe";
        }

        if (utilisateurDAO.beneficiaireADejaCompte(beneficiaireId)) {
            return "beneficiaire_compte";
        }

        EntityManager em = JPAUtil.getEntityManager();
        Beneficiaire b = em.find(Beneficiaire.class, beneficiaireId);

        Utilisateur user = new Utilisateur();
        user.setLogin(login);
        user.setMotDePasse(mdp);
        user.setRole("USER");
        user.setBeneficiaire(b);

        utilisateurDAO.insert(user);

        return "success";
    }

    public void creerUtilisateur(Utilisateur u) {
        utilisateurDAO.save(u);
    }

    public void modifierUtilisateur(Utilisateur u) {
        utilisateurDAO.update(u);
    }
}
