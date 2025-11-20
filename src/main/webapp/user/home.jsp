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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">

    <style>
        body {
            display: flex;
            margin: 0;
            min-height: 100vh;
        }
        .sidebar {
            width: 220px;
            background-color: #2c3e50;
            padding-top: 20px;
            display: flex;
            flex-direction: column;
            height: 100vh;
        }
        .sidebar a {
            color: white;
            text-decoration: none;
            padding: 15px 20px;
            font-weight: bold;
            display: block;
        }
        .sidebar a:hover {
            background-color: #34495e;
        }
        .main-content {
            flex-grow: 1;
            padding: 20px;
        }
    </style>
</head>

<body>

<!-- Menu vertical -->
<div class="sidebar">
    <a href="<%= request.getContextPath() %>/profil"><i class="fa fa-user"></i> Mon Profil</a>
    <a href="<%= request.getContextPath() %>/cotisations"><i class="fa fa-money-bill"></i> Mes Cotisations</a>
    <a href="<%= request.getContextPath() %>/remboursements"><i class="fa fa-file-invoice-dollar"></i> Mes Remboursements</a>
</div>

<!-- Contenu principal -->
<div class="main-content">

    <!-- Header avec logout -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1>Bienvenue <%= b.getNom() + " " + b.getPrenom() %></h1>

        <a class="btn btn-danger" href="<%= request.getContextPath() %>/logout">Se déconnecter</a>
    </div>

    <p>Utilisez le menu à gauche pour accéder à vos informations, cotisations et remboursements.</p>
</div>

</body>
</html>
