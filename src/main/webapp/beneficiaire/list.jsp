<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.project.model.Beneficiaire" %>
<html>
<head>
    <title>Liste des bénéficiaires</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/style.css">
</head>
<body>

<h2>Liste des bénéficiaires</h2>

<%
    String success = (String) request.getAttribute("success");
    String error = (String) request.getAttribute("error");
%>

<% if (success != null) { %>
<div class="alert-success">✅ <%= success %></div>
<% } %>

<% if (error != null) { %>
<div class="alert-error">❌ <%= error %></div>
<% } %>

<p>


    <a href="<%= request.getContextPath() %>/beneficiaire?action=ajouter" class="ajouter-lien">
        <i class="fa-solid fa-plus"></i> Ajouter un bénéficiaire
    </a>


</p>

<table>
    <tr>
        <th>ID</th>
        <th>Nom</th>
        <th>Prénom</th>
        <th>Date naissance</th>
        <th>Numéro SS</th>
        <th>Actions</th>
    </tr>

    <%
        java.util.List<com.project.model.Beneficiaire> list =
                (java.util.List<com.project.model.Beneficiaire>) request.getAttribute("beneficiaires");

        if (list != null && !list.isEmpty()) {
            for (com.project.model.Beneficiaire b : list) {
    %>
    <tr>
        <td><%= b.getId() %></td>
        <td><%= b.getNom() %></td>
        <td><%= b.getPrenom() %></td>
        <!-- ✅ LocalDate s'affiche automatiquement au format yyyy-MM-dd -->
        <td><%= b.getDateNaissance() %></td>
        <td><%= b.getNumeroSS() %></td>
        <td class="actions">
            <a href="beneficiaire?action=modifier&id=<%= b.getId() %>" class="action-btn edit">
                <i class="fa-solid fa-pen"></i> Modifier
            </a>
            <a href="beneficiaire?action=supprimer&id=<%= b.getId() %>"
               class="action-btn delete"
               onclick="return confirm('Voulez-vous vraiment supprimer ce bénéficiaire ?');">
                <i class="fa-solid fa-trash"></i> Supprimer
            </a>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr><td colspan="6" class="no-data">Aucun bénéficiaire pour le moment</td></tr>
    <%
        }
    %>
</table>

<!-- Lien pour revenir au dashboard -->
<p>
    <a href="<%= request.getContextPath() %>/dashboard" style="color: blue; text-decoration: underline;">
        ← Retour au tableau de bord
    </a>
</p>

</body>
</html>