package com.project.controller;

import com.project.model.Cotisation;
import com.project.model.Beneficiaire;
import com.project.service.CotisationService;
import com.project.service.BeneficiaireService;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet("/cotisation")
public class CotisationServlet extends HttpServlet {

    private CotisationService service = new CotisationService();
    private BeneficiaireService beneficiaireService = new BeneficiaireService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        // --- SUPPRESSION ---
        if ("supprimer".equals(action)) {
            try {
                Long id = Long.parseLong(request.getParameter("id"));
                if (service.deleteCotisation(id)) {
                    request.getSession().setAttribute("success", "Cotisation supprimée avec succès !");
                } else {
                    request.getSession().setAttribute("error", "Erreur lors de la suppression !");
                }
            } catch (Exception e) {
                request.getSession().setAttribute("error", "ID invalide ou erreur interne !");
            }
            response.sendRedirect("cotisation");
            return;
        }

        // --- MODIFICATION (affichage formulaire) ---
        if ("modifier".equals(action)) {
            try {
                Long id = Long.parseLong(request.getParameter("id"));
                Cotisation c = service.getCotisationById(id);

                if (c != null) {
                    List<Beneficiaire> listB = beneficiaireService.getAllBeneficiaires();
                    request.setAttribute("beneficiaires", listB);
                    request.setAttribute("cotisation", c);
                    request.getRequestDispatcher("/cotisation/modifiercotisation.jsp").forward(request, response);
                    return;
                } else {
                    request.getSession().setAttribute("error", "Cotisation introuvable !");
                    response.sendRedirect("cotisation");
                    return;
                }
            } catch (Exception e) {
                request.getSession().setAttribute("error", "Erreur interne ou ID invalide !");
                response.sendRedirect("cotisation");
                return;
            }
        }

        // --- AJOUT COTISATION (affichage formulaire) ---
        if ("ajouter".equals(action)) {
            List<Beneficiaire> listB = beneficiaireService.getAllBeneficiaires();
            request.setAttribute("beneficiaires", listB);
            request.getRequestDispatcher("/cotisation/ajoutcotisation.jsp").forward(request, response);
            return;
        }

        // --- AFFICHAGE PAR DÉFAUT (liste des cotisations) ---
        String error = (String) request.getSession().getAttribute("error");
        String success = (String) request.getSession().getAttribute("success");
        request.setAttribute("error", error);
        request.setAttribute("success", success);
        request.getSession().removeAttribute("error");
        request.getSession().removeAttribute("success");

        List<Cotisation> list = service.getAllCotisations();
        request.setAttribute("cotisations", list);
        request.getRequestDispatcher("/cotisation/list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            Long id = request.getParameter("id") != null && !request.getParameter("id").isEmpty()
                    ? Long.parseLong(request.getParameter("id"))
                    : null;
            Long beneficiaireId = Long.parseLong(request.getParameter("beneficiaireId"));

            Beneficiaire b = beneficiaireService.getBeneficiaireById(beneficiaireId);
            if (b == null) {
                request.getSession().setAttribute("error", "Bénéficiaire introuvable !");
                response.sendRedirect("cotisation");
                return;
            }

            Double montant = Double.parseDouble(request.getParameter("montant"));
            LocalDate date = LocalDate.parse(request.getParameter("date")); // format yyyy-MM-dd

            Cotisation c = new Cotisation();
            c.setId(id);
            c.setMontant(montant);
            c.setDate(date);
            c.setBeneficiaire(b);

            boolean ok;

            if ("update".equals(action)) {
                // Vérifier doublon sauf cette cotisation
                if (service.getCotisationsParBeneficiaire(beneficiaireId).stream()
                        .anyMatch(co -> co.getDate().equals(date) && !co.getId().equals(id))) {
                    request.getSession().setAttribute("error", "Une cotisation pour cette date existe déjà !");
                    response.sendRedirect("cotisation");
                    return;
                }

                ok = service.updateCotisation(c);

                if (ok) {
                    request.getSession().setAttribute("success", "Cotisation modifiée avec succès !");
                } else {
                    request.getSession().setAttribute("error", "Erreur lors de la modification !");
                }

            } else { // AJOUT
                ok = service.addCotisation(c);
            if (ok) {
                request.getSession().setAttribute("success", "Cotisation enregistrée avec succès !");
            } else {
                request.getSession().setAttribute("error",
                        "Ce bénéficiaire a déjà une cotisation enregistrée à cette date !");
                response.sendRedirect("cotisation?action=ajouter");
                return;
            }}


        } catch (NumberFormatException | DateTimeParseException e) {
            request.getSession().setAttribute("error", "Montant ou date invalide !");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Erreur interne : " + e.getMessage());
        }

        response.sendRedirect("cotisation");
    }
}
