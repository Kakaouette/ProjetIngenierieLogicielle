<%-- 
    Document   : ajoutBase
    Created on : 19 juin 2014, 16:10:14
    Author     : roulonn
--%>

<%@page import="java.util.Map"%>
<%@page import="java.util.Map"%>
<%@page import="javafx.util.Pair"%>
<%@page import="modele.entite.Menu"%>
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
    </head>
    <body>
        <nav class="navbar navbar-inverse navbar" role="navigation">
            <%
                Compte c = (Compte) request.getSession().getAttribute("compte");
                Integer current = (Integer) request.getAttribute("menuS");
                List<Pair<Menu, List<Menu>>> lesMenus = (List<Pair<Menu, List<Menu>>>) this.getServletContext().getAttribute("menu");
                Map<Menu, TypeCompte> lesMenusType = (Map<Menu, TypeCompte>) this.getServletContext().getAttribute("menuType");
            %>
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed toggle-menu menu-right push-body" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div>
                <div class="container" id="menu">
                    <div id="navbar" class="navbar-collapse collapse cbp-spmenu cbp-spmenu-vertical cbp-spmenu-right">
                        <ul id="ulnav" class="nav navbar-nav">
                            <%
                                for (Pair<Menu, List<Menu>> unEnsembleMenu : lesMenus) {
                                    if (lesMenusType.get(unEnsembleMenu.getKey()).getValue() <= c.getType().getValue()) {
                                        String htmlMenu = "";
                                        htmlMenu += "<li ";
                                        if (unEnsembleMenu.getKey().getId() == current) {
                                            htmlMenu += "class='active' ";
                                        }
                                        if (!unEnsembleMenu.getValue().isEmpty()) {
                                            htmlMenu += "class='dropdown' ";
                                        }
                                        htmlMenu += ">\n";
                                        htmlMenu += "<a ";
                                        if (unEnsembleMenu.getKey().getAction() != null) {
                                            htmlMenu += "href='Navigation?action=" + unEnsembleMenu.getKey().getAction().getId() + "' ";
                                        }

                                        if (!unEnsembleMenu.getValue().isEmpty()) {
                                            htmlMenu += "class='dropdown-toggle' data-toggle='dropdown'";
                                        }
                                        
                                        htmlMenu += ">\n";
                                        htmlMenu += "<span class='fa "+ unEnsembleMenu.getKey().getIcon() +"'></span> " + unEnsembleMenu.getKey().getTexte();
                                        
                                        if (!unEnsembleMenu.getValue().isEmpty()) {
                                            htmlMenu += "<span class='caret'></span>";
                                        }
                                        
                                        htmlMenu += "</a>";
                                        
                                        if (!unEnsembleMenu.getValue().isEmpty()) {
                                            htmlMenu += "<ul class='dropdown-menu'>\n";
                                            for (Menu subMenu : unEnsembleMenu.getValue()) {
                                                if(subMenu.getAction().getPage().getTypeAuthoriser().getValue() <= c.getType().getValue())
                                                    htmlMenu += "<li><a href='Navigation?action=" + subMenu.getAction().getId() + "'><i class='fa "+ subMenu.getIcon() +"'></i> " + subMenu.getTexte() + "</a></li>\n";
                                            }
                                            htmlMenu += "</ul>\n";
                                        }
                                        
                                        htmlMenu += "</li>\n";
                                        out.print(htmlMenu);
                                    }
                                }
                            %>
                            <li class="pull-right">
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

