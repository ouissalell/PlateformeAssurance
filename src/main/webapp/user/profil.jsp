<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.project.model.Utilisateur, com.project.model.Beneficiaire" %>
<%

    if (session == null || !"USER".equals(session.getAttribute("userRole"))) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }


    Utilisateur user = (Utilisateur) session.getAttribute("user");
    Beneficiaire b = user.getBeneficiaire();
%>
<html>
<head>
    <title>Mes informations</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
</head>
<body class="p-4">

<h3>Mes informations personnelles</h3>
<table class="table table-bordered mt-3">
    <tr><th>Nom</th><td><%= b.getNom() %></td></tr>
    <tr><th>Prénom</th><td><%= b.getPrenom() %></td></tr>
    <tr><th>Date de naissance</th><td><%= b.getDateNaissance() %></td></tr>
    <tr><th>Numéro SS</th><td><%= b.getNumeroSS() %></td></tr>
</table>


<a href="<%= request.getContextPath() %>/user/home.jsp" class="btn btn-primary">Retour au Dashboard</a>

</body>
</html>
