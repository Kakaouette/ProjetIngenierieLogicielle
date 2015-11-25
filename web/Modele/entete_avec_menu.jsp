<%-- 
    Document   : ajoutBase
    Created on : 19 juin 2014, 16:10:14
    Author     : roulonn
--%>

<%@page import="modele.entite.TypeCompte"%>
<%@page import="modele.entite.Compte"%>
<%@page import="java.util.List"%>
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
        <link rel="icon" href="images/favicon.ico">
        <!-- Bootstrap core CSS -->
        <link href="bootstrap/css/bootstrap.css" rel="stylesheet">
        <link href="bootstrap/font-awesome/css/font-awesome.css" rel="stylesheet">
        <link href="bootstrap/css/ULR.css" rel="stylesheet">
        <script src="jQuery/jquery-1.11.2.js"></script>
        <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
        <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
          <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
        <%
            Compte c = (Compte) request.getSession().getAttribute("compte");
        %>
    </head>
    <body>
        <nav class="navbar navbar-inverse navbar" role="navigation">
            <%
                Integer current = (Integer) request.getAttribute("menu");
                List<String> pageAction = (List<String>) request.getSession().getAttribute("page");
                if (current == null) {
                    current = 0;
                }
            %>
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed toggle-menu menu-right push-body" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand collapsed" href="Navigation?action=index">ULR</a>
                </div>
                <div class="container" id="menu">
                    <div id="navbar" class="navbar-collapse collapse cbp-spmenu cbp-spmenu-vertical cbp-spmenu-right" style="height: 895px;">
                        <ul class="nav navbar-nav">
                            <li <%if (current.equals(0)) {%>class="active"<%}%>>
                                <a href="Navigation?action=index">
                                    <span class="fa fa-home"></span> Accueil
                                </a>
                            </li>
                            <%if (c.getType() == TypeCompte.admin) {%>
                                <li <%if (current.equals(1)) {%>class="active"<%}%> class="dropdown" >
                                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                        Gestion comptes <span class="caret"></span>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li><a href="Navigation?action=voirAjoutCompte"><i class="fa fa-plus"></i> Ajouter</a></li>
                                        <li><a href="Navigation?action=voirGestionComptes"><i class="fa fa-edit"></i> Modifier</a></li>
                                    </ul>
                                </li>
                            <%}%>
                            <%if (c.getType() == TypeCompte.admin || c.getType() == TypeCompte.secretaire_formation) {%>
                                <li <%if (current.equals(2)) {%>class="active"<%}%> class="dropdown">
                                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                                        Gestion formations <span class="caret"></span>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li><a href="Navigation?action=voirAjoutFormation"><i class="fa fa-plus"></i> Ajouter</a></li>
                                        <li><a href="Navigation?action=voirGestionFormation"><i class="fa fa-edit"></i> Modifier</a></li>
                                        <li><a href="Navigation?action=voirDatesInscription"><i class="fa fa-calendar"></i> Dates d'inscription</a></li>
                                    </ul>
                                </li>
                            <%}%>
                            <li>
                                <a href=Navigation?action=gererAuthentification&session=deco>
                                    <i class="fa fa-power-off"></i> Déconnexion
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </nav>
        <!-- Main -->
        <div class="container" id="main">
            <h1 class="page-header"><%=titre%></h1>