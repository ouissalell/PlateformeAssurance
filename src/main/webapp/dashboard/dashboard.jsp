<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Plateforme Assurance - Tableau de bord</title>
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
    <a href="beneficiaire"><i class="fa fa-users"></i> Bénéficiaires</a>
    <a href="cotisation"><i class="fa fa-money-bill"></i> Cotisations</a>
    <a href="remboursement"><i class="fa fa-file-invoice-dollar"></i> Remboursements</a>
    <a href="associerUser"><i class="fa fa-user-plus"></i> Associer compte User</a>

</div>

<!-- Zone de contenu -->
<div class="main-content">
    <!-- Barre supérieure avec Logout -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1>Bienvenue sur la Plateforme Assurance</h1>
        <!-- Bouton Logout -->
        <a href="login.jsp" class="btn btn-danger">Se déconnecter</a>
    </div>

    <p>Utilisez le menu à gauche pour naviguer vers les différentes fonctionnalités.</p>
</div>







</body>
</html>
