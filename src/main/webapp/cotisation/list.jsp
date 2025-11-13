<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.project.model.Cotisation" %>
<%@ page import="com.project.model.Beneficiaire" %>

<html>
<head>
    <title>Liste des cotisations</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/style.css">
</head>
<body>

<h2>Liste des cotisations</h2>

<%
    String success = (String) request.getAttribute("success");
    String error = (String) request.getAttribute("error");

    List<Cotisation> cotisations = (List<Cotisation>) request.getAttribute("cotisations");
%>

<% if (success != null) { %>
<div class="alert-success">✅ <%= success %></div>
<% } %>

<% if (error != null) { %>
<div class="alert-error">❌ <%= error %></div>
<% } %>

<p>
    <a href="<%= request.getContextPath() %>/cotisation?action=ajouter" class="ajouter-lien">
        <i class="fa-solid fa-plus"></i> Ajouter une cotisation
    </a>

</p>

<table>
    <tr>
        <th>ID</th>
        <th>Bénéficiaire</th>
        <th>Montant</th>
        <th>Date</th>
        <th>Actions</th>
    </tr>

    <%
        if (cotisations != null && !cotisations.isEmpty()) {
            for (Cotisation c : cotisations) {
    %>
    <tr>
        <td><%= c.getId() %></td>
        <td><%= c.getBeneficiaire().getNom() %> <%= c.getBeneficiaire().getPrenom() %></td>
        <td><%= c.getMontant() %></td>
        <td><%= c.getDate() %></td>
        <td class="actions">
            <a href="cotisation?action=modifier&id=<%= c.getId() %>" class="action-btn edit">
                <i class="fa-solid fa-pen"></i> Modifier
            </a>
            <a href="cotisation?action=supprimer&id=<%= c.getId() %>" class="action-btn delete"
               onclick="return confirm('Voulez-vous vraiment supprimer cette cotisation ?');">
                <i class="fa-solid fa-trash"></i> Supprimer
            </a>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="5" class="no-data">Aucune cotisation pour le moment</td>
    </tr>
    <%
        }
    %>
</table>

<p>
    <a href="<%= request.getContextPath() %>/dashboard" style="color: blue; text-decoration: underline;">
        ← Retour au tableau de bord
    </a>
</p>

</body>
</html>
