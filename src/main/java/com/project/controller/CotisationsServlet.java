package com.project.controller;

import com.project.model.Utilisateur;
import com.project.model.Beneficiaire;
import com.project.model.Cotisation;
import com.project.service.CotisationService;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/cotisations")
public class CotisationsServlet extends HttpServlet {

    private CotisationService cotisationService = new CotisationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"USER".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        Utilisateur user = (Utilisateur) session.getAttribute("user");
        Beneficiaire b = user.getBeneficiaire();

        // Récupérer ses cotisations
        CotisationService cotisationService = new CotisationService();
        List<Cotisation> cotisations = cotisationService.getCotisationsParBeneficiaire(b.getId());
        request.setAttribute("cotisations", cotisations);
        request.getRequestDispatcher("/user/cotisations.jsp").forward(request, response);
    }



}
