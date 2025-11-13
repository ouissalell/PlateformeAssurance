package com.project.controller;

import com.project.model.Utilisateur;
import com.project.model.Beneficiaire;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !"USER".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        Utilisateur user = (Utilisateur) session.getAttribute("user");
        Beneficiaire b = user.getBeneficiaire();

        request.setAttribute("beneficiaire", b);
        request.getRequestDispatcher("/user/home.jsp").forward(request, response);
    }





}
