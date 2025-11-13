<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.project.model.Utilisateur, com.project.model.Beneficiaire, com.project.model.Cotisation, java.util.List" %>
<%@ page import="com.project.service.CotisationService" %>

<%
    if (session == null || !"USER".equals(session.getAttribute("userRole"))) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    Utilisateur user = (Utilisateur) session.getAttribute("user");
    Beneficiaire b = user.getBeneficiaire();

    CotisationService cotisationService = new CotisationService();
    List<Cotisation> cotisations = cotisationService.getCotisationsParBeneficiaire(b.getId());
%>

<html>
<head>
    <title>Mes cotisations</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
</head>
<body class="p-4">

<h3>Mes cotisations</h3>
<table class="table table-striped mt-3">
    <thead>
    <tr>
        <th>ID</th>
        <th>Montant</th>
        <th>Date</th>
    </tr>
    </thead>
    <tbody>
    <% for(Cotisation c : cotisations) { %>
    <tr>
        <td><%= c.getId() %></td>
        <td><%= c.getMontant() %></td>
        <td><%= c.getDate() %></td>
    </tr>
    <% } %>
    </tbody>
</table>


<a href="<%= request.getContextPath() %>/user/home.jsp" class="btn btn-primary">Retour au Dashboard</a>

</body>
</html>
