<%-- 
    Document   : index
    Created on : 4 nov. 2015, 18:35:11
    Author     : nicol
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="Modele/entete_sans_menu.jsp" %>
<% if(request.getAttribute("logout") == null){ %>
Votre compte n'existe pas dans notre base, contacter veuillez l’administrateur, <a href="https://www.example.com/cas/logout">cliquez ici pour vous déconnectez du serveur CAS</a>.
<% }else{ %>
Vous êtes déconnecté de l'application mais pas de tous les services : 
<a href="https://www.example.com/cas/logout">cliquez ici pour vous déconnectez du serveur CAS</a>
sinon quittez simplement cette page
<%}%>
<%@include file="Modele/pied.jsp" %>
