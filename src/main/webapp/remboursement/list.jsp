<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.project.model.Remboursement" %>
<%@ page import="java.util.List" %>
<%@ page import="com.project.model.Beneficiaire" %>
<html>
<head>
    <title>Liste des remboursements</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/style.css">
</head>
<body>

<h2>Liste des remboursements</h2>

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


    <a href="<%= request.getContextPath() %>/remboursement?action=ajouter" class="ajouter-lien">
        <i class="fa-solid fa-plus"></i> Ajouter un remboursement
    </a>

</p>




<table>
    <tr>
        <th>ID</th>
        <th>Bénéficiaire</th>
        <th>Montant</th>
        <th>Date</th>
        <th>Description</th>
        <th>Actions</th>
    </tr>

    <%
        java.util.List<com.project.model.Remboursement> list =
                (java.util.List<com.project.model.Remboursement>) request.getAttribute("remboursements");

        if (list != null && !list.isEmpty()) {
            for (com.project.model.Remboursement r : list) {
    %>
    <tr>
        <td><%= r.getId() %></td>
        <td>
            <%= r.getBeneficiaire() != null
                    ? (r.getBeneficiaire().getNom() + " " + r.getBeneficiaire().getPrenom())
                    : "—" %>
        </td>
        <td><%= r.getMontant() %> MAD</td>
        <td><%= r.getDate() %></td>
        <td><%= (r.getDescription() != null && !r.getDescription().isEmpty()) ? r.getDescription() : "-" %></td>
        <td class="actions">
            <a href="remboursement?action=modifier&id=<%= r.getId() %>" class="action-btn edit">
                <i class="fa-solid fa-pen"></i> Modifier
            </a>
            <a href="remboursement?action=supprimer&id=<%= r.getId() %>"
               class="action-btn delete"
               onclick="return confirm('Voulez-vous vraiment supprimer ce remboursement ?');">
                <i class="fa-solid fa-trash"></i> Supprimer
            </a>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr><td colspan="6" class="no-data">Aucun remboursement enregistré pour le moment</td></tr>
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
