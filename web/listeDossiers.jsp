<%-- 
    Document   : listeUtilisateurs
    Created on : 17 nov. 2015, 16:53:53
    Author     : Pierre
--%>

<%@page import="modele.entite.Dossier"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="Modele/entete_avec_menu.jsp" %>

<%@include file="Modele/dataTablesScript.jsp" %>
<table id="myTable" cellspacing="0" class="table table-striped table-bordered table-hover table-condensed dt-responsive text-center" width="100%">
    <thead>
        <tr>
            <th>Etat</th>
            <th>Numéro</th>
            <th>Type</th>
            <th>Date</th>
            <th>Formation demandé</th>
            <th>Nom de l'etudiant</th>
            <th>Prenom de l'etudiant</th>
            <th>Modifier</th>
        </tr>
    </thead>
</table>

<% if(request.getAttribute("message") != null){ %>
    <div class="alert alert-success">
        <%out.print(request.getAttribute("message"));%>
    </div>
<%}%>
        
<%@include file="Modele/pied.jsp" %>
