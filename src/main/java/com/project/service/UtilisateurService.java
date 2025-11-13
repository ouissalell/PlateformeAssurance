package com.project.service;

import com.project.dao.UtilisateurDAO;
import com.project.model.Utilisateur;

public class UtilisateurService {

    private UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    // Authentification simple
    public Utilisateur authentifier(String login, String motDePasse) {
        Utilisateur user = utilisateurDAO.findByLogin(login);
        if (user != null && user.getMotDePasse().equals(motDePasse)) {
            return user;
        }
        return null;
    }

    // Créer un nouvel utilisateur
    public void creerUtilisateur(Utilisateur u) {
        utilisateurDAO.save(u);
    }

    // Modifier un utilisateur existant
    public void modifierUtilisateur(Utilisateur u) {
        utilisateurDAO.update(u);
    }
}
