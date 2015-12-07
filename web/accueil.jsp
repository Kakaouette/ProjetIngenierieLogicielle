<%-- 
    Document   : accueil
    Created on : 4 nov. 2015, 19:05:02
    Author     : nicol
--%>

<%@page import="modele.entite.Formation"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="Modele/entete_avec_menu.jsp" %>
        <p>Bonjour
            <%out.print(c.getPrenom());%> <%out.print(c.getNom());%>, vous êtes de type <%out.print(c.getType().toString());%> de valeur <%out.print(c.getType().getValue());%><br>
        <h2>Liste Formation</h2>
        <%for(Formation f : c.getFormationAssocie()){%>
            <%out.print(f.getDescription());%> <br/>
        <%}%>
        </p>
<%@include file="Modele/pied.jsp" %>
