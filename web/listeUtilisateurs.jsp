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
        <form action="Navigation?action=voirModifierUtilisateur" method="POST" class="form-inline">
            <tr>
                    <input type="text" name="login" id="login" hidden>
                <td><input type="text" name="id" id="id" placeholder="<%out.print(compte.getId());%>" readonly></td>
                <td><input type="text" name="nom" id="nom" placeholder="<%out.print(compte.getNom());%>" readonly></td>
                <td><input type="text" name="prenom" id="prenom" placeholder="<%out.print(compte.getPrenom());%>" readonly></td>
                <td><input type="text" name="type" id="type" placeholder="<%out.print(compte.getType());%>" readonly></td>
                <td><input type="text" name="email" id="email" placeholder="<%out.print(compte.getMail());%>" readonly></td>
                <td><button class="btn btn-success" type="submit" name="action" value="voirModifierUtilisateur">Modifier</button></td>
            </tr>
        </form>
        <% }%>
    </tbody>
</table>

<%@include file="Modele/pied.jsp" %>
