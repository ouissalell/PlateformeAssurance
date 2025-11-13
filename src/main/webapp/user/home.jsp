<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.project.model.Utilisateur, com.project.model.Beneficiaire" %>

<%
    // Vérification du rôle
    if (session == null || !"USER".equals(session.getAttribute("userRole"))) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    // Récupération de l'utilisateur et du bénéficiaire
    Utilisateur user = (Utilisateur) session.getAttribute("user");
    Beneficiaire b = user.getBeneficiaire();
%>

<html>
<head>
    <title>Accueil Utilisateur</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
</head>
<body class="p-4">
<nav class="navbar navbar-light bg-light">
    <div class="container-fluid d-flex justify-content-between">
        <!-- Texte à gauche -->
        <span class="navbar-text">
            <h2>Bienvenue <%= b.getNom() + " " + b.getPrenom() %></h2>
        </span>
        <!-- Bouton à droite -->
        <a class="btn btn-danger" href="<%= request.getContextPath() %>/logout">Se déconnecter</a>
    </div>
</nav>


<ul class="list-group mt-4">
    <li class="list-group-item"><a href="<%= request.getContextPath() %>/profil">Mes informations personnelles</a></li>
    <li class="list-group-item"><a href="<%= request.getContextPath() %>/cotisations">Mes cotisations</a></li>
    <li class="list-group-item"><a href="<%= request.getContextPath() %>/remboursements">Mes remboursements</a></li>

</ul>

</body>
</html>
