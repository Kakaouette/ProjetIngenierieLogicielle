<%-- 
    Document   : index
    Created on : 4 nov. 2015, 18:35:11
    Author     : nicol
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="Modele/entete_sans_menu.jsp" %>
<% if(request.getAttribute("logout") == null){ %>
Votre compte n'existe pas dans notre base, contacter veuillez l’administrateur.
<% }else{ %>
Vous êtes déconnecté de l'application mais pas de tous les services : 
<a href="https://localhost/cas/logout">cliquez ici pour vous déconnectez de tous les services</a>
sinon quittez simplement cette page
<%}%>
<%@include file="Modele/pied.jsp" %>
