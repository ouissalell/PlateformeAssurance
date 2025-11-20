<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List, com.project.model.Beneficiaire" %>

<%
  List<Beneficiaire> liste = (List<Beneficiaire>) request.getAttribute("beneficiaires");
  String error = (String) request.getSession().getAttribute("error");
  String success = (String) request.getSession().getAttribute("success");
  request.getSession().removeAttribute("error");
  request.getSession().removeAttribute("success");
%>

<html>
<head>
  <title>Associer un compte utilisateur</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
  <style>
    body {
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      background: #e9ecef;
      display: flex;
      justify-content: center;
      align-items: flex-start;
      min-height: 100vh;
      padding-top: 50px;
    }
    h2 {
      text-align: center;
      color: #343a40;
    }
    form {
      background-color: #fff;
      padding: 30px 40px;
      border-radius: 12px;
      box-shadow: 0 5px 15px rgba(0,0,0,0.1);
      width: 400px;
    }
    .form-group {
      position: relative;
      margin-top: 20px;
    }
    .form-group input, .form-group select {
      width: 100%;
      padding: 10px 35px 10px 10px;
      border-radius: 6px;
      border: 1px solid #ccc;
      box-sizing: border-box;
      font-size: 14px;
      transition: border 0.3s;
    }
    .form-group input:focus, .form-group select:focus {
      border-color: #28a745;
      outline: none;
    }
    .form-group i {
      position: absolute;
      right: 10px;
      top: 50%;
      transform: translateY(-50%);
      color: #aaa;
    }
    input[type="submit"] {
      margin-top: 25px;
      padding: 12px 20px;
      background-color: #28a745;
      color: #fff;
      border: none;
      border-radius: 8px;
      width: 100%;
      font-size: 16px;
      font-weight: bold;
      cursor: pointer;
      transition: background 0.3s;
    }
    input[type="submit"]:hover {
      background-color: #218838;
    }
    p.message {
      margin-top: 15px;
      font-weight: bold;
      text-align: center;
    }
    p.message.error { color: #dc3545; }
    p.message.success { color: #28a745; }
    p a {
      display: block;
      margin-top: 20px;
      text-align: center;
      text-decoration: none;
      color: #007bff;
      font-weight: bold;
    }
    p a:hover {
      text-decoration: underline;
    }
  </style>
</head>

<body>
<div>
  <h2>Associer un Compte Utilisateur à un Bénéficiaire</h2>

  <% if (error != null) { %>
  <p class="message error"><%= error %></p>
  <% } %>
  <% if (success != null) { %>
  <p class="message success"><%= success %></p>
  <% } %>


  <form action="<%= request.getContextPath() %>/associerUser" method="post">

    <!-- Liste bénéficiaires -->
    <div class="form-group">
      <select name="beneficiaire_id" required>
        <option value="">-- Choisir un bénéficiaire --</option>
        <% for(Beneficiaire b : liste) { %>
        <option value="<%= b.getId() %>">
          <%= b.getNom() %> <%= b.getPrenom() %> — NSS : <%= b.getNumeroSS() %>
        </option>
        <% } %>
      </select>
      <i class="fa fa-users"></i>
    </div>

    <!-- Login -->
    <div class="form-group">
      <input type="text" name="login" placeholder="Login" required/>
      <i class="fa fa-user"></i>
    </div>

    <!-- Mot de passe -->
    <div class="form-group">
      <input type="password" name="motdepasse" placeholder="Mot de passe" required/>
      <i class="fa fa-lock"></i>
    </div>

    <input type="submit" value="Créer le compte"/>
  </form>

  <p><a href="<%= request.getContextPath() %>/dashboard">← Retour au tableau de bord</a></p>
</div>



</body>
</html>
