<%-- 
    Document   : ajoutBase
    Created on : 19 juin 2014, 16:10:14
    Author     : roulonn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% String titre = (String) request.getAttribute("titre");
    if (titre == null) {
        titre = "Accueil";
    }
%>
<html lang="fr">
    <head>
        <title>GIST - <% out.print(titre); %></title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="icon" href="/static/img/favicon.png">
        <!-- Bootstrap core CSS -->
        <link href="bootstrap/css/bootstrap.css" rel="stylesheet">
        <link href="bootstrap/font-awesome/css/font-awesome.css" rel="stylesheet">
        <link href="bootstrap/css/signin.css" rel="stylesheet">
        <script src="jQuery/jquery-1.11.2.js"></script>
        <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
        <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
    </head>
    <body>

        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
            <div class="navbar-header">
                    <a class="navbar-brand" href="Navigation?action=index">Gestion Inscription</a>
                </div>
        </div>
    </nav>
               <div class="container">
