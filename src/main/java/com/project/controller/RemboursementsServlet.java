package com.project.controller;

import com.project.model.Utilisateur;
import com.project.model.Beneficiaire;
import com.project.model.Remboursement;
import com.project.service.RemboursementService;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/remboursements")
public class RemboursementsServlet extends HttpServlet {

    private RemboursementService remboursementService = new RemboursementService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1️⃣ Vérifier que l'utilisateur est connecté et a le rôle USER
        HttpSession session = request.getSession(false);
        if (session == null || !"USER".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // 2️⃣ Récupérer l'utilisateur et son bénéficiaire
        Utilisateur user = (Utilisateur) session.getAttribute("user");
        Beneficiaire b = user.getBeneficiaire();

        // 3️⃣ Récupérer uniquement les remboursements de ce bénéficiaire
        List<Remboursement> remboursements = remboursementService.getRemboursementsParBeneficiaire(b.getId());

        // 4️⃣ Passer la liste à la JSP
        request.setAttribute("remboursements", remboursements);

        // 5️⃣ Forward vers la JSP
        request.getRequestDispatcher("/user/remboursements.jsp").forward(request, response);
    }
}
