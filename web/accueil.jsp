<%-- 
    Document   : accueil
    Created on : 4 nov. 2015, 19:05:02
    Author     : nicol
--%>

<%@page import="modele.entite.Formation"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="Modele/entete_avec_menu.jsp" %>
        <p>Bonjour
            <%out.print(c.getPrenom());%> <%out.print(c.getNom());%>, vous êtes de type <%out.print(c.getType().toString());%>.<br>
        <h3>Liste Formation</h3>
        <%if(c.getFormationAssocie().size() == 0){%>
            <p>Vous n'avez pas de formation associé à votre compte.</p>
        <%}%>
        <%for(Formation f : c.getFormationAssocie()){%>
            <%out.print(f.getDescription());%> <br/>
        <%}%>
        </p>
<%@include file="Modele/pied.jsp" %>
