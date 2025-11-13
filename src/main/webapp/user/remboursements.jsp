<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List, com.project.model.Remboursement" %>

<%

    if (session == null || !"USER".equals(session.getAttribute("userRole"))) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }


    List<Remboursement> remboursements = (List<Remboursement>) request.getAttribute("remboursements");
%>

<html>
<head>
    <title>Mes Remboursements</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
</head>
<body class="p-4">

<h2>Mes Remboursements</h2>

<table class="table table-striped">
    <thead>
    <tr>
        <th>ID</th>
        <th>Montant</th>
        <th>Date</th>
    </tr>
    </thead>
    <tbody>
    <% if (remboursements != null && !remboursements.isEmpty()) {
        for (Remboursement r : remboursements) { %>
    <tr>
        <td><%= r.getId() %></td>
        <td><%= r.getMontant() %></td>
        <td><%= r.getDate() %></td>
    </tr>
    <%  }
    } else { %>
    <tr>
        <td colspan="3">Aucun remboursement trouvé.</td>
    </tr>
    <% } %>
    </tbody>
</table>

<a href="<%= request.getContextPath() %>/user/home.jsp" class="btn btn-primary">Retour au Dashboard</a>


</body>
</html>
