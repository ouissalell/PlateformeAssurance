package com.project.controller;

import com.project.model.Beneficiaire;
import com.project.service.BeneficiaireService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/beneficiaire")
public class BeneficiaireServlet extends HttpServlet {

    private BeneficiaireService service = new BeneficiaireService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        // --- SUPPRESSION ---
        if ("supprimer".equals(action)) {
            try {
                Long id = Long.parseLong(request.getParameter("id"));
                if (service.deleteBeneficiaire(id)) {
                    request.getSession().setAttribute("success", "Bénéficiaire supprimé avec succès !");
                } else {
                    request.getSession().setAttribute("error", "Erreur lors de la suppression !");
                }
            } catch (Exception e) {
                request.getSession().setAttribute("error", "ID invalide ou erreur interne !");
            }
            response.sendRedirect("beneficiaire");
            return;
        }

        // --- MODIFICATION (affichage formulaire) ---
        else if ("modifier".equals(action)) {
            try {
                Long id = Long.parseLong(request.getParameter("id"));
                Beneficiaire b = service.getBeneficiaireById(id);

                if (b != null) {
                    request.setAttribute("beneficiaire", b);
                    request.getRequestDispatcher("/beneficiaire/modifierbeneficiaire.jsp").forward(request, response);
                    return;
                } else {
                    request.getSession().setAttribute("error", "Bénéficiaire introuvable !");
                    response.sendRedirect("beneficiaire");
                    return;
                }
            } catch (Exception e) {
                request.getSession().setAttribute("error", "Erreur interne ou ID invalide !");
                response.sendRedirect("beneficiaire");
                return;
            }
        }

        // --- AJOUT (affichage formulaire) --- ✅ AJOUTÉ
        else if ("ajouter".equals(action)) {
            request.getRequestDispatcher("/beneficiaire/ajoutbeneficiaire.jsp").forward(request, response);
            return;
        }

        // --- AFFICHAGE PAR DÉFAUT ---
        String error = (String) request.getSession().getAttribute("error");
        String success = (String) request.getSession().getAttribute("success");
        request.setAttribute("error", error);
        request.setAttribute("success", success);
        request.getSession().removeAttribute("error");
        request.getSession().removeAttribute("success");

        List<Beneficiaire> list = service.getAllBeneficiaires();
        request.setAttribute("beneficiaires", list);
        request.getRequestDispatcher("/beneficiaire/list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        // --- MODIFICATION (update) ---
        if ("update".equals(action)) {
            try {
                Long id = Long.parseLong(request.getParameter("id"));
                Beneficiaire b = new Beneficiaire();
                b.setId(id);
                b.setNom(request.getParameter("nom"));
                b.setPrenom(request.getParameter("prenom"));
                b.setDateNaissance(request.getParameter("dateNaissance"));
                b.setNumeroSS(request.getParameter("numeroSS"));

                if (service.updateBeneficiaire(b)) {
                    request.getSession().setAttribute("success", "Bénéficiaire modifié avec succès !");
                } else {
                    request.getSession().setAttribute("error", "Erreur lors de la modification !");
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.getSession().setAttribute("error", "Erreur interne !");
            }
            response.sendRedirect("beneficiaire");
            return;
        }

        // --- AJOUT ---
        if ("add".equals(action)) {
            Beneficiaire b = new Beneficiaire();
            b.setNom(request.getParameter("nom"));
            b.setPrenom(request.getParameter("prenom"));
            b.setDateNaissance(request.getParameter("dateNaissance"));
            b.setNumeroSS(request.getParameter("numeroSS"));

            String result = service.addBeneficiaire(b);

            if (result == null) {
                request.getSession().setAttribute("success", "Bénéficiaire ajouté avec succès !");
            } else {
                request.getSession().setAttribute("error", result);
            }

            response.sendRedirect("beneficiaire");
            return;
        }
    }
}