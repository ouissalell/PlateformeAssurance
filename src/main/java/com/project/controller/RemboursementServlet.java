package com.project.controller;

import com.project.model.Remboursement;
import com.project.model.Beneficiaire;
import com.project.service.RemboursementService;
import com.project.service.BeneficiaireService;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@WebServlet("/remboursement")
public class RemboursementServlet extends HttpServlet {

    private RemboursementService service = new RemboursementService();
    private BeneficiaireService beneficiaireService = new BeneficiaireService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        // --- SUPPRESSION ---
        if ("supprimer".equals(action)) {
            try {
                Long id = Long.parseLong(request.getParameter("id"));
                if (service.deleteRemboursement(id)) {
                    request.getSession().setAttribute("success", "Remboursement supprimé avec succès !");
                } else {
                    request.getSession().setAttribute("error", "Erreur lors de la suppression !");
                }
            } catch (Exception e) {
                request.getSession().setAttribute("error", "ID invalide ou erreur interne !");
            }
            response.sendRedirect("remboursement");
            return;
        }

        // --- MODIFICATION (affichage formulaire) ---
        if ("modifier".equals(action)) {
            try {
                Long id = Long.parseLong(request.getParameter("id"));
                Remboursement r = service.getRemboursementById(id);

                if (r != null) {
                    List<Beneficiaire> listB = beneficiaireService.getAllBeneficiaires();
                    request.setAttribute("beneficiaires", listB);
                    request.setAttribute("remboursement", r);
                    request.getRequestDispatcher("/remboursement/modifierremboursement.jsp").forward(request, response);
                    return;
                } else {
                    request.getSession().setAttribute("error", "Remboursement introuvable !");
                    response.sendRedirect("remboursement");
                    return;
                }
            } catch (Exception e) {
                request.getSession().setAttribute("error", "Erreur interne ou ID invalide !");
                response.sendRedirect("remboursement");
                return;
            }
        }

        // --- AJOUT (affichage formulaire) ---
        if ("ajouter".equals(action)) {
            List<Beneficiaire> listB = beneficiaireService.getAllBeneficiaires();

            // ✅ On ajoute la liste comme attribut de la requête
            request.setAttribute("beneficiaires", listB);

            request.getRequestDispatcher("/remboursement/ajoutremboursement.jsp").forward(request, response);
            return;
        }


        // --- AFFICHAGE PAR DÉFAUT (liste des remboursements) ---
        String error = (String) request.getSession().getAttribute("error");
        String success = (String) request.getSession().getAttribute("success");
        request.setAttribute("error", error);
        request.setAttribute("success", success);
        request.getSession().removeAttribute("error");
        request.getSession().removeAttribute("success");

        List<Remboursement> list = service.getAllRemboursements();
        request.setAttribute("remboursements", list);
        request.getRequestDispatcher("/remboursement/list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            // common parsing (but don't assume id is always present)
            String idParam = request.getParameter("id");
            Long id = (idParam != null && !idParam.isEmpty()) ? Long.parseLong(idParam) : null;

            Long beneficiaireId = Long.parseLong(request.getParameter("beneficiaireId"));
            Beneficiaire b = beneficiaireService.getBeneficiaireById(beneficiaireId);
            if (b == null) {
                request.getSession().setAttribute("error", "Bénéficiaire introuvable !");
                response.sendRedirect("remboursement");
                return;
            }

            double montant = Double.parseDouble(request.getParameter("montant"));
            LocalDate date = LocalDate.parse(request.getParameter("date")); // yyyy-MM-dd
            String description = request.getParameter("description");

            // --- UPDATE ---
            if ("update".equals(action)) {
                if (id == null) {
                    request.getSession().setAttribute("error", "ID manquant pour la mise à jour !");
                    response.sendRedirect(request.getContextPath() + "/remboursement");
                    return;
                }

                // Vérifier doublon (même bénéficiaire et même date sauf lui-même)
                boolean existeDeja = service.getRemboursementsParBeneficiaire(beneficiaireId).stream()
                        .anyMatch(re -> re.getDate().equals(date) && !re.getId().equals(id));

                if (existeDeja) {
                    request.getSession().setAttribute("error", "⚠️ Un remboursement à cette date existe déjà !");
                    response.sendRedirect(request.getContextPath() + "/remboursement");
                    return;
                }

                Remboursement rToUpdate = new Remboursement();
                rToUpdate.setId(id);
                rToUpdate.setBeneficiaire(b); // ✅ on utilise l’objet récupéré via le service
                rToUpdate.setMontant(montant);
                rToUpdate.setDate(date);
                rToUpdate.setDescription(description);


                boolean ok = service.updateRemboursement(rToUpdate);
                if (ok) {
                    request.getSession().setAttribute("success", " Remboursement modifié avec succès !");
                } else {
                    request.getSession().setAttribute("error", " Erreur lors de la mise à jour !");
                }

                response.sendRedirect(request.getContextPath() + "/remboursement");
                return;
            }

            // --- AJOUT ---
            Remboursement r = new Remboursement();
            r.setId(id); // généralement null pour un ajout
            r.setBeneficiaire(b);
            r.setMontant(montant);
            r.setDate(date);
            r.setDescription(description);

            String result = service.addRemboursement(r);

            switch (result) {
                case "SUCCESS":
                    request.getSession().setAttribute("success", "Remboursement enregistré avec succès !");
                    break;
                case "DOUBLON":
                    request.getSession().setAttribute("error",
                            "Ce bénéficiaire a déjà un remboursement enregistré à cette date !");
                    response.sendRedirect("remboursement?action=ajouter");
                    return;
                case "MONTANT_INVALIDE":
                    request.getSession().setAttribute("error", "Le montant doit être supérieur à 0 !");
                    response.sendRedirect("remboursement?action=ajouter");
                    return;
                case "DATE_MANQUANTE":
                    request.getSession().setAttribute("error", "La date est obligatoire !");
                    response.sendRedirect("remboursement?action=ajouter");
                    return;
                default:
                    request.getSession().setAttribute("error", "Erreur lors de l'enregistrement !");
                    response.sendRedirect("remboursement?action=ajouter");
                    return;
            }

        } catch (NumberFormatException | DateTimeParseException e) {
            request.getSession().setAttribute("error", "Montant ou date invalide !");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Erreur interne : " + e.getMessage());
        }

        response.sendRedirect("remboursement");
    }
}
