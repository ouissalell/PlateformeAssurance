package com.project.controller;


import com.project.model.Beneficiaire;
import com.project.model.Utilisateur;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.ServletException;
import java.io.IOException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/profil")
public class ProfilServlet extends HttpServlet {





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
    // Forward vers la JSP, PAS vers la servlet
    request.getRequestDispatcher("/user/profil.jsp").forward(request, response);
}}
