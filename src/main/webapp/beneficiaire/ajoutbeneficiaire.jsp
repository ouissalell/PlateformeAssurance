<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.project.model.Beneficiaire" %>
<html>
<head>
    <title>Ajouter un bénéficiaire</title>
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
        .form-group input {
            width: 100%;
            padding: 10px 35px 10px 10px;
            border-radius: 6px;
            border: 1px solid #ccc;
            box-sizing: border-box;
            font-size: 14px;
            transition: border 0.3s;
        }
        .form-group input:focus {
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
    <h2>Ajouter un bénéficiaire</h2>

    <%-- Messages d'erreur ou de succès --%>
    <%
        String error = (String) request.getSession().getAttribute("error");
        String success = (String) request.getSession().getAttribute("success");
        if (error != null) { %>
    <p class="message error"><%= error %></p>
    <%      request.getSession().removeAttribute("error");
    }
        if (success != null) { %>
    <p class="message success"><%= success %></p>
    <%      request.getSession().removeAttribute("success");
    }
    %>

    <form action="<%=request.getContextPath()%>/beneficiaire" method="post">

            <input type="hidden" name="action" value="add"/>  <!-- ✅ AJOUTER CETTE LIGNE -->


        <div class="form-group">
            <input type="text" name="nom" placeholder="Nom" required/>
            <i class="fa fa-user"></i>
        </div>
        <div class="form-group">
            <input type="text" name="prenom" placeholder="Prénom" required/>
            <i class="fa fa-user"></i>
        </div>
        <div class="form-group">
            <!-- ✅ CHANGÉ: type="date" au lieu de text -->
            <input type="date" name="dateNaissance" required/>
            <i class="fa fa-calendar"></i>
        </div>
        <div class="form-group">
            <input type="text" name="numeroSS" placeholder="numeroSS" required/>
            <i class="fa fa-id-card"></i>
        </div>
        <input type="submit" value="Ajouter"/>
    </form>

    <p><a href="<%=request.getContextPath()%>/beneficiaire">← Retour à la liste des bénéficiaires</a></p>

</div>

</body>
</html>