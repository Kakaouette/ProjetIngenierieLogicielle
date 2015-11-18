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
                <td>
                    <form action="Navigation?action=voirModifierUtilisateur" method="POST" class="form-inline">
                        
                        <input type="text" name="login" id="login" value="<%out.print(compte.getLogin());%>" hidden>
                        <input type="text" name="nom" id="nom" value="<%out.print(compte.getNom());%>" hidden>
                        <input type="text" name="prenom" id="prenom" value="<%out.print(compte.getPrenom());%>" hidden>
                        <input type="text" name="type" id="type" value="<%out.print(compte.getType());%>" hidden>
                        <input type="text" name="email" id="email" value="<%out.print(compte.getMail());%>" hidden>
                        <button class="btn btn-success" type="submit" name="action" value="voirModifierUtilisateur">Modifier</button>
                    </form>
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
