<%-- 
    Document   : index
    Created on : 4 nov. 2015, 18:35:11
    Author     : nicol
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="Modele/entete_sans_menu.jsp" %>
<% String adresseDeconnexion = "https://localhost/cas/logout"; %>
<% if(request.getAttribute("logout") == null){ %>
Votre compte n'existe pas dans notre base, contacter veuillez l’administrateur, <a href='<% out.print(adresseDeconnexion); %>'>cliquez ici pour vous déconnectez du serveur CAS</a>.
<% }else{ %>
Vous êtes déconnecté de l'application mais pas de tous les services : 
<a href='<% out.print(adresseDeconnexion); %>'>cliquez ici pour vous déconnectez du serveur CAS</a>
sinon quittez simplement cette page
<%}%>
<%@include file="Modele/pied.jsp" %>
