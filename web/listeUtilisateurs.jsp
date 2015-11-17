<%-- 
    Document   : listeUtilisateurs
    Created on : 17 nov. 2015, 16:53:53
    Author     : Pierre
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="Modele/entete_avec_menu.jsp" %>

<table class="table">
    <thead>
        <tr>
            <th>Numéro</th>
            <th>Nom</th>
            <th>Prénom</th>
            <th>Type</th>
            <th>Email</th>
        </tr>
    </thead>
    <tbody>
        <% List<Compte> comptes=(List<Compte>) request.getSession().getAttribute("comptes");
           for (Compte compte : comptes){
        %>
        <tr>
            <td><%out.print(compte.getId());%></td>
            <td><%out.print(compte.getNom());%></td>
            <td><%out.print(compte.getPrenom());%></td>
            <td><%out.print(compte.getType());%></td>
            <td><%out.print(compte.getMail());%></td>
        </tr>
        <% }%>
    </tbody>
</table>

<%@include file="Modele/pied.jsp" %>
