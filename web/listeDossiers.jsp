<%-- 
    Document   : listeUtilisateurs
    Created on : 17 nov. 2015, 16:53:53
    Author     : Pierre
--%>

<%@page import="modele.entite.Dossier"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="Modele/entete_avec_menu.jsp" %>

<table class="table">
    <thead>
        <tr>
            <th>Numéro</th>
            <th>Admissible</th>
            <th>Date</th>
            <th>Etat</th>
            <th>Formation demandé</th>
            <th>Id de l'etudiant</th>
        </tr>
    </thead>
    <tbody>
        <% List<Dossier> Dossiers=(List<Dossier>) request.getAttribute("dossiers");
           for (Dossier dossier : Dossiers){
        %>
        
            <tr>
                <td><%out.print(dossier.getId());%></td>
                <td><%out.print(dossier.isAdmissible());%></td>
                <td><%out.print(dossier.getDate());%></td>
                <td><%out.print(dossier.getEtat());%></td>
                <td><%out.print(dossier.getDemandeFormation().getIntitule());%></td>
                <td><%out.print(dossier.getEtudiant().getId());%></td>
                <td>
                    <a class="btn btn-success" href="Navigation?action=voirModifierUtilisateur&id=<% out.print(dossier.getId()); %>">Consulter</a>
                </td>
            </tr>
        
        <% }%>
    </tbody>
</table>

<% if(request.getAttribute("message") != null){ %>
    <div class="alert alert-success">
        <%out.print(request.getAttribute("message"));%>
    </div>
<%}%>
        
<%@include file="Modele/pied.jsp" %>
