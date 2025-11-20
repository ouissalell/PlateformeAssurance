package com.project.controller;

import com.project.service.BeneficiaireService;
import com.project.service.UtilisateurService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/associerUser")
public class AssocierUserServlet extends HttpServlet {

    private BeneficiaireService beneficiaireService = new BeneficiaireService();
    private UtilisateurService utilisateurService = new UtilisateurService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        request.setAttribute("beneficiaires", beneficiaireService.getAllBeneficiaires());
        request.getRequestDispatcher("/associerUser/associerUser.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String login = request.getParameter("login");
        String mdp = request.getParameter("motdepasse");
        Long beneficiaireId = Long.valueOf(request.getParameter("beneficiaire_id"));

        String resultat = utilisateurService.creerCompteUtilisateur(login, mdp, beneficiaireId);

        if ("success".equals(resultat)) {
            request.getSession().setAttribute("success", "Le compte utilisateur a été créé avec succès !");
        } else if ("login_existe".equals(resultat)) {
            request.getSession().setAttribute("error", "Ce login existe déjà. Veuillez en choisir un autre.");
        } else if ("beneficiaire_compte".equals(resultat)) {
            request.getSession().setAttribute("error", "Ce bénéficiaire a déjà un compte utilisateur.");
        } else {
            request.getSession().setAttribute("error", "Une erreur est survenue. Veuillez réessayer.");
        }

        response.sendRedirect(request.getContextPath() + "/associerUser");
    }

}
